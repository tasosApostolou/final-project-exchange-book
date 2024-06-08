package com.example.changebook.dto.StoreDTO;

import com.example.changebook.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StoreReadOnlyDTO extends BaseDTO {
    private String name;
    private String address;
    //    private UserReadOnlyDTO user;
    private Long userId;

    public StoreReadOnlyDTO(Long id, String firstname, String lastname) {
        this.setId(id);
//        this.username = username;
//        this.password = password;
        this.name = firstname;
        this.address = lastname;
    }
}
