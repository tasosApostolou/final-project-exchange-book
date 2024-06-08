package com.example.changebook.service;

import com.example.changebook.dto.personDTO.PersonUpdateDTO;
import com.example.changebook.dto.personDTO.RegisterPersonDTO;
import com.example.changebook.dto.usersDTO.UserInsertDTO;
import com.example.changebook.dto.usersDTO.UserUpdateDTO;
import com.example.changebook.model.Book;
import com.example.changebook.model.Notification;
import com.example.changebook.model.Person;
import com.example.changebook.model.User;
import com.example.changebook.service.exceptions.EntityAlreadyExistsException;
import com.example.changebook.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IPersonService {
    Person registerPerson(RegisterPersonDTO dto) throws EntityAlreadyExistsException;
    Person updatePerson(PersonUpdateDTO personDTO) throws EntityNotFoundException;
    Person deletePerson(Long id) throws EntityNotFoundException;
    List<Person> getPersonsByLastname(String lastname) throws EntityNotFoundException;
    //    public User addBooksToPerson(Long userId, Set<Long> bookIds) throws Exception;
    Person getPersonById(Long id) throws EntityNotFoundException;
//    User getUserByUsername(String username) throws EntityNotFoundException;
    Person getPersonByUserId(Long id) throws EntityNotFoundException;
    List<Person> getPersonsByBookTitle(String bookTitle) throws EntityNotFoundException;

    List<Book> getAllBooksByPersonId(Long id) throws EntityNotFoundException;
    Book removeBookFromPerson(Long personId, Long bookId) throws EntityNotFoundException;
}
