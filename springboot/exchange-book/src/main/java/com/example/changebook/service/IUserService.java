package com.example.changebook.service;




import com.example.changebook.dto.usersDTO.UserInsertDTO;
import com.example.changebook.dto.usersDTO.UserUpdateDTO;
import com.example.changebook.model.Book;
import com.example.changebook.model.Notification;
import com.example.changebook.model.User;
import com.example.changebook.service.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Set;

public interface IUserService {
    User insertUser(UserInsertDTO userDTO) throws Exception;
    User updateUser(UserUpdateDTO userDTO) throws EntityNotFoundException;
    User deleteUser(Long id) throws EntityNotFoundException;
    List<User> getUsersByUsername(String username) throws EntityNotFoundException;
    User getUserById(Long id) throws EntityNotFoundException;
    User getUserByUsername(String username) throws EntityNotFoundException;
    List<Notification> getNotificationsByUserId(Long userID) throws EntityNotFoundException;


}
