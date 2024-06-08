package com.example.changebook.dto.usersDTO;

import com.example.changebook.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInsertDTO {
//    @NotBlank
    private String username;
//    @NotBlank
    private String password;
//    @NotBlank
    private String role;
}
