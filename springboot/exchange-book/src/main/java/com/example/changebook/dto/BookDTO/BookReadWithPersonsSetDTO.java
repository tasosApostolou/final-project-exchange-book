package com.example.changebook.dto.BookDTO;

import com.example.changebook.dto.BaseDTO;
import com.example.changebook.dto.authorDTO.AuthorReadOnlyDTO;
import com.example.changebook.dto.personDTO.PersonReadonlyDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookReadWithPersonsSetDTO extends BaseDTO {
    public String title;
    public AuthorReadOnlyDTO author;
    public List<PersonReadonlyDTO> persons;
    public BookReadWithPersonsSetDTO() {
    }

    public BookReadWithPersonsSetDTO(Long id, String title, AuthorReadOnlyDTO authorReadOnlyDTO) {
        this.setId(id);
        title = title;
        this.author = authorReadOnlyDTO;
    }


    public BookReadWithPersonsSetDTO(Long id, String title) {
        this.setId(id);
        title = title;
    }
}
