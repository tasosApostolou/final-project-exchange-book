package com.example.changebook.dto.BookDTO;

import com.example.changebook.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookUpdateDTO extends BaseDTO {
    private String title;
    public BookUpdateDTO(Long bookId, String title) {
        this.setId(bookId);
        this.title = title;
    }



}
