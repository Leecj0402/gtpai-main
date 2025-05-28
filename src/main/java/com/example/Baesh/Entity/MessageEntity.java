package com.example.Baesh.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="message")
@Data
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chatRoomId")
    private ChatRoomEntity chatRoom;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private UserEntity sender;

    private String content;
    private LocalDateTime sentTime;
}
