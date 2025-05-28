package com.example.Baesh.DTO.ChatRoom;

import com.example.Baesh.DTO.MessageDTO;
import com.example.Baesh.DTO.User.ChatRoomuserDTO;
import com.example.Baesh.DTO.User.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    private Long id;
    private ChatRoomuserDTO userId1;
    private ChatRoomuserDTO userId2;
    private LocalDateTime createdTime;
    private List<MessageDTO> message;
}
