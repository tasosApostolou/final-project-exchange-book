package com.example.changebook.dto.NotificationDTO;

import com.example.changebook.dto.BaseDTO;
import com.example.changebook.dto.BookDTO.BookReadOnlyDTO;
import com.example.changebook.dto.BookDTO.BookUpdateDTO;
import com.example.changebook.dto.usersDTO.UserReadOnlyDTO;
import com.example.changebook.dto.usersDTO.UserUpdateDTO;
import com.example.changebook.model.Book;
import com.example.changebook.model.NotificationType;
import com.example.changebook.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationUpdateDTO extends BaseDTO {
    User interested;
    User user;
    Book book;
    NotificationType notificationType;
    Boolean isSeen;
    public NotificationUpdateDTO() {
    }

    public NotificationUpdateDTO(Long id,User interested, User user, Book book, NotificationType notificationType, Boolean isSeen) {
        this.setId(id);
        this.interested = interested;
        this.user = user;
        this.book = book;
        this.notificationType = notificationType;
        this.isSeen = isSeen;
    }
}
