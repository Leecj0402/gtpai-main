package com.example.Baesh.DTO.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUserDTO {
    private Long id;
    private String name;
    private String pictureUrl;
}
