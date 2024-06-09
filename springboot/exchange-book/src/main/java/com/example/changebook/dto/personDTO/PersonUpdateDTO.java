package com.example.changebook.dto.personDTO;

import com.example.changebook.dto.BaseDTO;
import com.example.changebook.dto.usersDTO.UserUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonUpdateDTO extends BaseDTO {
    private String firstname;
    private String lastname;
    private Long userId;

    public PersonUpdateDTO(Long id,String username, String password, String firstname, String lastname,Long userId) {
        this.setId(id);
//        this.username = username;
//        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.userId = userId;
    }
}
