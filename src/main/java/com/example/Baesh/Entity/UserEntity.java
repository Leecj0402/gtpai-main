package com.example.Baesh.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String pictureUrl;
    private String mbti;
    private String roles;
    private String GatherAPI;

    @OneToMany(mappedBy = "follower")
    private List<FollowEntity> follower;

    @OneToMany(mappedBy = "following")
    private List<FollowEntity> following;

    @OneToMany(mappedBy = "user1")
    private List<ChatRoomEntity> chatRoomsAsUser1;

    @OneToMany(mappedBy = "user2")
    private List<ChatRoomEntity> chatRoomsAsUser2;

    public UserEntity(String firstName, String lastName, String email, String pictureUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pictureUrl = pictureUrl;
        this.roles = "ROLE_USER";
    }

}
