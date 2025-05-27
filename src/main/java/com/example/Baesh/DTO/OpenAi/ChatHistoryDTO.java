package com.example.Baesh.DTO.OpenAi;

import lombok.Data;

@Data
public class ChatHistoryDTO {
    private Long id;
    private Long userId;
    private String chatData;
}
