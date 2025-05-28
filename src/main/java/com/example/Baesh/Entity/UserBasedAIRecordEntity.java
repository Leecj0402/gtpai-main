package com.example.Baesh.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="UserBasedAIRecord")
@Data
public class UserBasedAIRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aiModelName;
    private String chatData;
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;
}
