package com.example.changebook.dto.loginDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
@Data
public class LoginCredentialsDTO {
    @NotBlank
    public String username;

    @NotBlank
    public String password;

}
