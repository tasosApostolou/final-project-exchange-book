package com.example.changebook.dto.authorDTO;

import com.example.changebook.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorReadOnlyDTO extends BaseDTO {

public String name;

    public AuthorReadOnlyDTO(Long id, String name) {
        this.setId(id);
        this.name = name;
    }

    public AuthorReadOnlyDTO() {

    }
}
