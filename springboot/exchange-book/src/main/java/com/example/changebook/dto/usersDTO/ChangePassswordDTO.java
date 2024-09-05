package com.example.changebook.dto.usersDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangePassswordDTO {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
