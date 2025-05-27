package com.example.Baesh.Service;

import com.example.Baesh.DTO.FineTune.FineTuneRequest;
import com.example.Baesh.DTO.FineTune.FineTuneResponse;
import com.example.Baesh.DTO.OpenAi.OpenAiFileResponse;
import com.example.Baesh.Entity.ChatHistoryEntity;
import com.example.Baesh.Entity.UserEntity;
import com.example.Baesh.Interface.ChatHistoryRepository;
import com.example.Baesh.Interface.OpenAiApi;
import com.example.Baesh.Interface.UserRepository;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OpenAiClientService {
    private final OpenAiApi openAiApi;
    private final String apiKey;
    private final ChatHistoryRepository chatHistoryRepository;
    private final UserRepository userRepository;
    @Value("${jsonl.file.path}")
    private String jsonlFilePath;

    public OpenAiClientService(@Value("${spring.ai.openai.api-key}") String apiKey
            ,ChatHistoryRepository chatHistoryRepository, UserRepository userRepository)
    {
        this.apiKey = "Bearer " + apiKey;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openai.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        openAiApi = retrofit.create(OpenAiApi.class);
        this.chatHistoryRepository = chatHistoryRepository;
        this.userRepository=userRepository;

    }

    //챗봇 과 대화 내용 JSON타입으로 변경후 디비에 저장
    public void saveChatMessage(JSONArray messages, Long userId) {
        UserEntity user= userRepository.findById(userId).orElse(null);

        JSONObject wrappedMessages = new JSONObject();
        wrappedMessages.put("messages", messages);

        ChatHistoryEntity chatHistory = new ChatHistoryEntity();
        chatHistory.setChatData(wrappedMessages.toString());
        chatHistory.setCreatedTime(LocalDateTime.now());
        chatHistory.setUserId(user);
        chatHistoryRepository.save(chatHistory);
    }


    public String gptFineTunUpdate(Long userId){
        UserEntity user = userRepository.findById(userId).orElse(null);
        List<ChatHistoryEntity> chatHistories =chatHistoryRepository.findByUserId(userId);
        System.out.println(chatHistories.size());
        String fineTuneId;
        File file = new File(jsonlFilePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); // 필요한 디렉터리 생성
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (ChatHistoryEntity chatHistory : chatHistories) {
                writer.write(chatHistory.getChatData());
                writer.newLine(); // JSONL 형식에 맞게 줄 구분
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error writing JSONL file: " + e.getMessage();
        }

        try {
            fineTuneId= uploadFile(file.getPath());
            System.out.println(getFineTuneStatus(fineTuneId));
            file.delete();
            return getFineTuneStatus(fineTuneId).getId();
        }catch (IOException e){
            e.printStackTrace();
            return "Error writing JSONL file: " + e.getMessage();
        }



    }

    public String uploadFile(String filePath) throws IOException {
        File file = new File(filePath);

        // 파일과 목적을 설정하는 MultipartBody
        RequestBody fileBody = RequestBody.create(file, MediaType.parse("application/jsonl"));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
        RequestBody purpose = RequestBody.create("fine-tune", MediaType.parse("text/plain"));

        // API 호출
        Call<OpenAiFileResponse> call = openAiApi.uploadFile(apiKey, filePart, purpose);
        Response<OpenAiFileResponse> response = call.execute();

        if (response.isSuccessful() && response.body() != null) {
            System.out.println(response.body());
            FineTuneResponse fineTuneResponse = startFineTuning(response.body().getId(),"gpt-3.5-turbo");
            return fineTuneResponse.getId();
        } else {
            System.err.println("파일 업로드 실패: " + (response.errorBody() != null ? response.errorBody().string() : "알 수 없는 오류"));
            return "파일 업로드 실패: " + (response.errorBody() != null ? response.errorBody().string() : "알 수 없는 오류");
        }
    }

    public FineTuneResponse startFineTuning(String fileId, String modelName) throws IOException {
        FineTuneRequest request = new FineTuneRequest(fileId, modelName);

        // API 요청 및 응답 확인
        Call<FineTuneResponse> call = openAiApi.createFineTune(apiKey, request);
        Response<FineTuneResponse> response = call.execute();

        if (response.isSuccessful() && response.body() != null) {
            System.out.println("파인튜닝 시작: "+ response.body());
            return response.body();
        } else {
            // 오류 응답이 있을 경우 에러 메시지 출력
            if (response.errorBody() != null) {
                System.err.println("파인튜닝 오류: " + response.errorBody().string());
            } else {
                System.err.println("파인튜닝 실패: 알 수 없는 오류");
            }
            return null;
        }
    }

    public FineTuneResponse getFineTuneStatus(String fineTuneId) throws IOException {
        return openAiApi.getFineTuneStatus(apiKey, fineTuneId).execute().body();
    }




}
