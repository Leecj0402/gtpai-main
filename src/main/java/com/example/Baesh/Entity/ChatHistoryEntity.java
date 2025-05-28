package com.example.Baesh.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "userBasedAIRecord")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userId;

    private String chatData;
    private LocalDateTime createdTime;
}
