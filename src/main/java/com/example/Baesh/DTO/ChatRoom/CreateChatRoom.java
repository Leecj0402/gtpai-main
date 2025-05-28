package com.example.Baesh.DTO.ChatRoom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatRoom {
    private Long user1Id;
    private Long user2Id;
}
