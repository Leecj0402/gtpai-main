package com.example.Baesh.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="follow")
@Data
public class FollowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "followerId")
    private UserEntity follower;

    @ManyToOne
    @JoinColumn(name = "followingId")
    private UserEntity following;

    private LocalDateTime followTime;
}
