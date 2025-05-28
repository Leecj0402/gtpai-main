package com.example.Baesh.DTO.Oauth;

import com.example.Baesh.DTO.User.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private UserDTO user;
    private String firstName;
    private String lastName;
    private String email;
    private String pictureUrl;

}
