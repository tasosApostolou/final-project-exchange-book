package com.example.changebook.dto.personDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RegisterPersonDTO {
    @NotBlank
    private String username;
    @NotNull
    private String password;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
}
