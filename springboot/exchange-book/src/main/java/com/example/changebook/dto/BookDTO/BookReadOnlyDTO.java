package com.example.changebook.dto.BookDTO;

import com.example.changebook.dto.BaseDTO;
import com.example.changebook.dto.authorDTO.AuthorReadOnlyDTO;
import com.example.changebook.dto.usersDTO.UserReadOnlyDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookReadOnlyDTO extends BaseDTO {
    public String title;
    public AuthorReadOnlyDTO author;
    public BookReadOnlyDTO() {
    }

    public BookReadOnlyDTO(Long id, String title, AuthorReadOnlyDTO authorReadOnlyDTO) {
        this.setId(id);
        this.title = title;
        this.author = authorReadOnlyDTO;
    }
    public BookReadOnlyDTO(Long id, String title, AuthorReadOnlyDTO authorReadOnlyDTO, List<UserReadOnlyDTO> userReadOnlyDTOS) {
        this.setId(id);
        this.title = title;
        this.author = authorReadOnlyDTO;
    }


    public BookReadOnlyDTO(Long id, String title) {
        this.setId(id);
        title = title;
    }



}
