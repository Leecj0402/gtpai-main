package com.example.Baesh.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="post")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String imageUrl;
    private String authorName;

    @ManyToOne
    @JoinColumn(name = "authorId",nullable = false)
    private UserEntity authorId;

    private LocalDateTime createdTime;

}
