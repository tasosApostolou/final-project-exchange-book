package com.example.changebook.repository;

import com.example.changebook.model.Notification;
import com.example.changebook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findNotificationById(Long id);
    void deleteAllByInterestedUser(User user);

    List<Notification> findAllByHolderUSer(User holderUser);

}
