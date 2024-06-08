package com.example.changebook.dto.NotificationDTO;

import com.example.changebook.dto.BaseDTO;
import com.example.changebook.dto.BookDTO.BookReadOnlyDTO;
import com.example.changebook.dto.usersDTO.UserReadOnlyDTO;
import com.example.changebook.model.Notification;
import com.example.changebook.model.NotificationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationReadOnlyDTO extends BaseDTO {
    UserReadOnlyDTO interested;
    UserReadOnlyDTO user;
    BookReadOnlyDTO book;
    NotificationType notificationType;
    LocalDateTime createdAt;
    Boolean isSeen;

    public NotificationReadOnlyDTO() {
    }

    public NotificationReadOnlyDTO(UserReadOnlyDTO interested,UserReadOnlyDTO user, BookReadOnlyDTO book, LocalDateTime createdAt, Boolean isSeen) {
        this.interested = interested;
        this.user = user;
        this.book = book;
        this.createdAt = createdAt;
        this.isSeen = isSeen;
    }
}