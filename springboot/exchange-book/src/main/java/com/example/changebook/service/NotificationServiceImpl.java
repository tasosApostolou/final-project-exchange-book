package com.example.changebook.service;

import com.example.changebook.dto.NotificationDTO.NotificationInsertDTO;
import com.example.changebook.dto.NotificationDTO.NotificationReadOnlyDTO;
import com.example.changebook.dto.NotificationDTO.NotificationUpdateDTO;
import com.example.changebook.mapper.Mapper;
import com.example.changebook.model.*;
import com.example.changebook.repository.BookRepository;
import com.example.changebook.repository.NotificationRepository;
import com.example.changebook.repository.UserRepository;
import com.example.changebook.service.exceptions.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService{
// NOT COMPLETED
private final NotificationRepository notificationRepository;
private final UserRepository userRepository;
private final BookRepository bookRepository;;
    @Override
    @Transactional
    //Not expected yet
    public Notification insert(NotificationInsertDTO dto, Long interestedID) throws Exception {
        Notification notification = new Notification();
        Notification inserted = new Notification();
        User holderUser;
        User interestedUser;
        Book book;

        try{
            interestedUser = userRepository.findUserById(interestedID); //No check because is the client requester
            holderUser = userRepository.findUserById(dto.getUser().getId());  // is event click request on existance user
            book = bookRepository.findBookById(dto.getBook().getId());
            notification.setBook(book);
            notification.setHolderUSer(holderUser);
            notification.setInterestedUser(interestedUser);
            Notification finalNotification = notification;
            finalNotification.setType(NotificationType.INTEREST);
//            if (interestedUser.getAllNotifications().stream().anyMatch(notification1 -> notification1.getHolderUSer().getId()== finalNotification.getInterestedUser().getId())){
//                finalNotification.setNotificationType(NotificationType.MATCH);
//            }
            for (Notification notif : interestedUser.getAllNotifications()){ // If both of 2 users are interested for a book of each other, make a new MATCH notification
                if (notif.getInterestedUser().getId()==holderUser.getId()){
                    finalNotification.setType(NotificationType.MATCH);
                    Notification n = new Notification();
                    n = notif;
                    n.setType(NotificationType.MATCH);
                    n = notificationRepository.save(n);
                }
            }
            inserted = notificationRepository.save(finalNotification);
            log.info(inserted.getBook().getTitle()+"book");
            if(notification.getId()==null){
                throw new Exception("Insert error");
            }
            log.info("insert succes for notification with id"+ notification.getId());
            return inserted;
        }catch (Exception e){
            log.error("insert error "+ e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Notification delete(Long id) throws EntityNotFoundException {
        Notification notification = null;

        try {
             notification =  notificationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Notification.class,id));
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return notification;
    }

    @Override
    public List<Notification> getNotificationsByHolder(User holder) throws EntityNotFoundException {
        List<Notification> notifications = new ArrayList<>();
        try {
            notifications = notificationRepository.findAllByHolderUSer(holder);
            if(notifications.isEmpty())throw new EntityNotFoundException(Notification.class,0L);
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return notifications;
    }

    @Override
    public Notification getNotificationById(Long id) throws EntityNotFoundException {
        Notification notification;
        try {
            notification = notificationRepository.findNotificationById(id);
            if(notification==null)throw new EntityNotFoundException(Notification.class,id);
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return notification;
    }

    @Override
    @Transactional
    public Notification update(NotificationUpdateDTO dto) throws EntityNotFoundException {
        Notification notification;
        Notification updated;
        try {
            notification = notificationRepository.findNotificationById(dto.getId());
            System.out.println(notification.getType()+" notification type");
//            notification.setType(notification.getType());
            if (notification == null) throw new EntityNotFoundException(Notification.class, dto.getId());
            updated = notificationRepository.save(Mapper.mapToNotification(dto));
            log.info("Notification with id: "+ updated.getId()+ " was updated");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return updated;
    }
}

