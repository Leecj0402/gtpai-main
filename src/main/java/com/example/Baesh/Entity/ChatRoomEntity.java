package com.example.Baesh.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="chatRoom")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1Id")
    private UserEntity user1;

    @ManyToOne
    @JoinColumn(name = "user2Id")
    private UserEntity user2;

    private LocalDateTime createdTime;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<MessageEntity> messages;
}
