package com.example.changebook.dto.usersDTO;

import com.example.changebook.dto.BaseDTO;
import com.example.changebook.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserUpdateDTO extends BaseDTO {

//    @NotBlank
    private String username;
//    @NotBlank
    private String password;
//    @NotBlank
    @Enumerated(EnumType.STRING)
    private Role role;

    public UserUpdateDTO(Long id,String username, String password, Role role) {
        this.setId(id);
        this.username = username;
        this.password = password;
        this.role = role;
    }
}