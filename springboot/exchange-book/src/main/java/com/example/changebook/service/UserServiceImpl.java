package com.example.changebook.service;

import com.example.changebook.dto.usersDTO.UserInsertDTO;
import com.example.changebook.dto.usersDTO.UserUpdateDTO;
import com.example.changebook.mapper.Mapper;

import com.example.changebook.model.Book;
import com.example.changebook.model.Notification;
import com.example.changebook.model.User;
import com.example.changebook.repository.BookRepository;
import com.example.changebook.repository.UserRepository;
import com.example.changebook.service.exceptions.EntityAlreadyExistsException;
import com.example.changebook.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    @Override
    public User insertUser(UserInsertDTO dto) throws Exception {
        User user;

        try{
            user = Mapper.mapToUser(dto);
            Optional<User> userToCreate = userRepository.findByUsername(user.getUsername());
            if (userToCreate.isPresent()) throw new EntityAlreadyExistsException(User.class,dto.getUsername());
            user = userRepository.save(user);
            if(user.getId()==null){
                throw new Exception("Insert error");
            }
            log.info("insert succes for user with id"+ user.getId());
            return user;
        }catch (Exception e){
            log.error("insert error "+ e.getMessage());
            throw e;
        }
    }

    @Transactional
    @Override
    public User updateUser(UserUpdateDTO dto) throws EntityNotFoundException {
        User user;
        User userToUpdate;
        try {
            user = userRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException(User.class,dto.getId()));
            userToUpdate = Mapper.mapToUser(dto);// This user without notifications
//            userToUpdate.setPassword(user.getPassword()); // userToUpdate it was found user with notifications
//            user = userRepository.save(userToUpdate); // save changing password user
            userToUpdate.setNotifications(user.getNotifications());
            user = userRepository.save(userToUpdate);
            log.info("User with id: "+ userToUpdate.getId()+ " was updated");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return user;
    }

    @Transactional
    @Override
    public User deleteUser(Long id) throws EntityNotFoundException {
        User user;

        try {
            user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class,id));
            userRepository.deleteById(id);
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return user;
    }

    @Override
    public List<User> getUsersByUsername(String username) throws EntityNotFoundException {
        List<User> users = new ArrayList<>();
        try {
            users = userRepository.findByUsernameStartingWith(username);
            if (users.isEmpty()) throw new EntityNotFoundException(User.class,0L);
            log.info("Users with lastname starting with "+ username +" were found");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return users;
    }

    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
        User user;
        try {
            user = userRepository.findUserById(id);
            if(user==null)throw new EntityNotFoundException(User.class,id);
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) throws EntityNotFoundException {
//        User user;
//        try {
//            user = userRepository.findUserByUsername(username);
//            if(user==null)throw new EntityNotFoundException(User.class,0L);
//
//        }catch (EntityNotFoundException e){
//            log.error(e.getMessage());
//            throw e;
//        }

        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new EntityNotFoundException(User.class,0L));
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userID) throws EntityNotFoundException {
        List<Notification> notifications = new ArrayList<>();
        User user;
        try{
            user = userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException(User.class,userID));
//            notifications = user.getAllNotifications().stream().filter(notification -> (notification.getIsSeen()==false)).collect(Collectors.toList());
            //notifications = user.getAllNotifications().stream().collect(Collectors.toList());
            notifications = user.getAllNotifications().stream().toList();
            System.out.println(notifications);
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return notifications;
    }

    @Override
    public List<User> getUsersByBookTitle(String bookTitle) throws EntityNotFoundException {
        List<User> users = new ArrayList<>();
        Book b = new Book();
        try {
            users = userRepository.findUsersByBookTitle(bookTitle);
            if (users.isEmpty()) throw new EntityNotFoundException(User.class,0L);
            log.info("Users with book title "+ bookTitle +" were found");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return users;
    }

    @Override
    public List<Book> getAllBooksByUserId(Long id) throws EntityNotFoundException {
        List<Book> books = new ArrayList<>();
        User user;
        try {
           user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Book.class,0L));
           books = user.getAllBooks().stream().toList();
            if (books.isEmpty()) throw new EntityNotFoundException(Book.class,0L);
            log.info("books of user with id "+ id  + " were found");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return books;
    }

    @Transactional
    @Override
    public void removeBookFromUser(Long userId, Long bookId) throws EntityNotFoundException {
            Optional<User> userOptional = userRepository.findById(userId);
            User user = userOptional.orElseThrow(() -> new EntityNotFoundException(User.class,userId));
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            Book book = bookOptional.orElseThrow(() -> new EntityNotFoundException(Book.class,bookId));
            boolean isRemoved = user.getBooks().remove(book);
            if (!isRemoved) {
                throw new RuntimeException("Book not found in the user's collection");
            }
            userRepository.save(user);
    }

}

