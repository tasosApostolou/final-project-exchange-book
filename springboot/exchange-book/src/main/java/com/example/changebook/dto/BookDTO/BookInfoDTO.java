package com.example.changebook.dto.BookDTO;

import com.example.changebook.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookInfoDTO extends BaseDTO {

    private String title;
//    private String state;
//    private String authorName; // Assuming Author has a 'name' field
    private List<Long> userIds;

    public BookInfoDTO(Long bookId, String title,  List<Long> userIds) {
        this.setId(bookId);
        this.title = title;
//        this.state = state;
//        this.authorName = authorName;
        this.userIds = userIds;
    }

    // Getters and setters
}