package com.example.changebook.dto.usersDTO;

import com.example.changebook.dto.BaseDTO;
import com.example.changebook.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReadOnlyDTO extends BaseDTO {
    private String username;
//    private String password;
//    @Enumerated(EnumType.STRING)
    private String role;
//
//    public UserReadOnlyDTO(Long id,String username, String password, String role) {
//        this.setId(id);
//        this.username = username;
//        this.password = password;
//        this.role = role;
//    }

    public UserReadOnlyDTO(Long id,String username, String role) {
        this.setId(id);
        this.username = username;
        this.role = role;
    }
}
