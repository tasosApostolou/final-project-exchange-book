package com.example.changebook.dto.personDTO;

import com.example.changebook.dto.BaseDTO;
import com.example.changebook.dto.usersDTO.UserReadOnlyDTO;
import com.example.changebook.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PersonReadonlyDTO extends BaseDTO {
    private String firstname;
    private String lastname;
//    private UserReadOnlyDTO user;
    private Long userId;
    public PersonReadonlyDTO(Long id, String firstname, String lastname) {
        this.setId(id);
        this.firstname = firstname;
        this.lastname = lastname;
    }



}
