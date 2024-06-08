package com.example.changebook.dto.BookDTO;

import com.example.changebook.dto.authorDTO.AuthorInsertDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookInsertDTO {
//    @NotBlank
    public String Title;
//    @NotBlank
    public AuthorInsertDTO author;
}
