package com.example.changebook.dto.NotificationDTO;

import com.example.changebook.dto.BookDTO.BookInsertDTO;
import com.example.changebook.dto.BookDTO.BookReadOnlyDTO;
import com.example.changebook.dto.usersDTO.UserInsertDTO;
import com.example.changebook.dto.usersDTO.UserReadOnlyDTO;
import com.example.changebook.model.Book;
import com.example.changebook.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationInsertDTO {
    User interested;
    User user;
    Book book;
    Boolean isSeen;
    public NotificationInsertDTO() {
    }


}