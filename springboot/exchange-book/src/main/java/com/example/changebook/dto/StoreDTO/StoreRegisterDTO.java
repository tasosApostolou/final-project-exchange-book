package com.example.changebook.dto.StoreDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StoreRegisterDTO {
    @NotBlank
    private String username;
    @NotNull
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
}
