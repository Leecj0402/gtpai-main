package com.example.Baesh.Controller;

import com.example.Baesh.DTO.FineTune.FineTuneRequest;
import com.example.Baesh.DTO.FineTune.FineTuneResponse;
import com.example.Baesh.Entity.ChatHistoryEntity;
import com.example.Baesh.Interface.ChatHistoryRepository;
import com.example.Baesh.Service.OpenAiClientCloneService;
import com.example.Baesh.Service.OpenAiClientService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/gpt")
public class GptController {
    private final ChatHistoryRepository chatHistoryRepository;
    private final OpenAiClientCloneService openAiClientCloneService;
    private final OpenAiClientService openAiClientService;

    public GptController( ChatHistoryRepository chatHistoryRepository, OpenAiClientService openAiClientService,OpenAiClientCloneService openAiClientCloneService) {

        this.chatHistoryRepository =chatHistoryRepository;
        this.openAiClientService = openAiClientService;
        this.openAiClientCloneService = openAiClientCloneService;
    }

    @PostMapping("/generate")
    public Map<String, String> generate(@RequestBody Map<String, Object> request) {
        System.out.println(request);
        String userMessage = (String) request.get("message");
        Long userId = ((Number) request.get("userId")).longValue();
        // JSON 배열로 대화 관리
        JSONArray messages = new JSONArray();
        messages.put(new JSONObject(Map.of("role", "user", "content", userMessage)));

        // 사용자 입력을 AI 모델에 전달하여 봇의 응답 생성
        String botReply = openAiClientCloneService.callModel("ft:gpt-3.5-turbo-0125:personal::AWZ8UxLH:ckpt-step-46", String.valueOf(messages));

        // AI 봇의 응답을 JSON 객체로 추가
        messages.put(new JSONObject(Map.of("role", "assistant", "content", botReply)));

        openAiClientService.saveChatMessage(messages,userId);
        // AI 응답 반환
        return Map.of("genera", botReply);
    }

    @PostMapping("/update")
    public FineTuneResponse gptFineTun(@RequestBody Long userId) throws IOException {
        String fineTuneId;
        fineTuneId = openAiClientService.gptFineTunUpdate(userId);
        return openAiClientService.getFineTuneStatus(fineTuneId);
    }

    @GetMapping("/finetune/status/{fineTuneId}")
    public FineTuneResponse getFineTuneStatus(@PathVariable("fineTuneId") String fineTuneId) throws IOException {
        return openAiClientService.getFineTuneStatus(fineTuneId);
    }
}
