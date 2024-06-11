package com.example.changebook.dto.StoreDTO;

import com.example.changebook.dto.BaseDTO;
import com.example.changebook.model.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StoreUpdateDTO extends BaseDTO {
    private String name;
    private String address;
    private Long userId;

    public StoreUpdateDTO(Long id, String name, String address, Long userId) {
        this.setId(id);
//        this.username = username;
//        this.password = password;
        this.name = name;
        this.address = address;
        this.userId = userId;
    }
}
