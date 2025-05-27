package com.example.Baesh.Service;

import com.example.Baesh.Interface.OpenAiApi;
import com.example.Baesh.Interface.UserRepository;
import okhttp3.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Map;

@Service
public class OpenAiClientCloneService {
    private final String apiKey;
    private final String endpoint = "https://api.openai.com/v1/chat/completions";

    public OpenAiClientCloneService(@Value("${spring.ai.openai.api-key}") String apiKey) {
        this.apiKey = apiKey;
    }

    public String callModel(String model, String prompt) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // messages 배열 생성
            JSONArray messages = new JSONArray();
            messages.put(new JSONObject(Map.of("role", "system", "content", "You are a helpful assistant.")));
            messages.put(new JSONObject(Map.of("role", "user", "content", prompt)));

            // 요청 본문 생성
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", model);
            requestBody.put("messages", messages);
            requestBody.put("max_tokens", 100);
            requestBody.put("temperature", 0.7);

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

            // API 호출
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.openai.com/v1/chat/completions",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // 응답 처리
            JSONObject responseBody = new JSONObject(response.getBody());
            return responseBody.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
        } catch (Exception e) {
            System.err.println("OpenAI API 호출 실패: " + e.getMessage());
            throw new RuntimeException("OpenAI API 호출 실패", e);
        }
    }
}