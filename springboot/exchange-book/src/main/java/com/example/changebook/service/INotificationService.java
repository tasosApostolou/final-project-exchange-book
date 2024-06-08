package com.example.changebook.service;


import com.example.changebook.dto.NotificationDTO.NotificationInsertDTO;
import com.example.changebook.dto.NotificationDTO.NotificationReadOnlyDTO;
import com.example.changebook.dto.NotificationDTO.NotificationUpdateDTO;
import com.example.changebook.model.Notification;
import com.example.changebook.model.User;
import com.example.changebook.service.exceptions.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.List;

public interface INotificationService {
    Notification insert(NotificationInsertDTO dto, Long interestedID) throws Exception;

    Notification delete(Long id) throws EntityNotFoundException;
    List<Notification> getNotificationsByHolder(User holder) throws EntityNotFoundException;
    Notification getNotificationById(Long id) throws EntityNotFoundException;

    Notification update(NotificationUpdateDTO dto) throws EntityNotFoundException;
}
