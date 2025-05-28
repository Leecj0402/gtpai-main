package com.example.Baesh.DTO.User;

import com.example.Baesh.Entity.FollowEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String mbti;
    private String pictureUrl;
    private int following;
    private int followers;
}
