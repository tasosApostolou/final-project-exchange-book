package com.example.changebook.service;

import com.example.changebook.dto.personDTO.PersonUpdateDTO;
import com.example.changebook.dto.personDTO.RegisterPersonDTO;
import com.example.changebook.dto.usersDTO.UserUpdateDTO;
import com.example.changebook.mapper.Mapper;
import com.example.changebook.model.Book;
import com.example.changebook.model.Notification;
import com.example.changebook.model.Person;
import com.example.changebook.model.User;
import com.example.changebook.repository.BookRepository;
import com.example.changebook.repository.NotificationRepository;
import com.example.changebook.repository.PersonRepository;
import com.example.changebook.repository.UserRepository;
import com.example.changebook.service.exceptions.EntityAlreadyExistsException;
import com.example.changebook.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
//@AllArgsConstructor
public class PersonServiceImpl implements IPersonService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final BookRepository bookRepository;
    private final NotificationRepository notificationRepository;


    @Override
    @Transactional
    public Person registerPerson(RegisterPersonDTO dto) throws EntityAlreadyExistsException {
        Person person;
        User user;

        try {
//            person = new Person(dto.getFirstname(), dto.getLastname());
//            user = User.NEW_PERSON(dto.getUsername(), dto.getPassword());
            person = Mapper.extractPersonFromRegisterPersonDTO(dto);
            user = Mapper.extractUserFromRegisterPersonDTO(dto);
            Optional<User> returnedUser = userRepository.findByUsername(dto.getUsername());
            if (returnedUser.isPresent()) throw new EntityAlreadyExistsException(Person.class, dto.getUsername());
            person.addUser(user);
            personRepository.save(person);
            log.info("Person added");
        } catch (EntityAlreadyExistsException e) {
            log.error("Problem in create user person" + e.getMessage());
            throw e;
        }
        return person;
    }

    @Transactional
    @Override
    public Person updatePerson(PersonUpdateDTO dto) throws EntityNotFoundException {
        Person person;
        Person personToUpdate;
        try {
            person = personRepository.findById(dto.getId()).orElseThrow(()-> new EntityNotFoundException(User.class, dto.getId()));
            User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException(User.class,dto.getUserId()));
            personToUpdate = Mapper.mapToPerson(dto);
            personToUpdate.setUser(user);
            person = personRepository.save(personToUpdate);
            log.info("User with id: "+ person.getId()+ " was updated");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return person;
    }

    @Transactional
    @Override
    public Person deletePerson(Long id) throws EntityNotFoundException {
        Person person;
        try {
            person =  personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Person.class,id));
//            notificationRepository.deleteAllByInterestedUser(person.getUser());
            personRepository.deleteById(id);
            log.info("Person user deleted");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return person;
    }

    @Override
    public List<Person> getPersonsByLastname(String lastname) throws EntityNotFoundException {
        List<Person> persons = new ArrayList<>();
        try {
            persons = personRepository.findByLastnameStartingWith(lastname);
            if (persons.isEmpty()) throw new EntityNotFoundException(Person.class,0L);
            log.info("Users with lastname starting with "+ lastname +" were found");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return persons;
    }

    @Override
    public Person getPersonById(Long id) throws EntityNotFoundException {
        Person person;
        try {
            person = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class,id));
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return person;
    }

    @Override
    public Person getPersonByUserId(Long id) throws EntityNotFoundException {
        Person person;
        User user;
        try{
            user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class,id));
            person = user.getPerson();
        }catch (EntityNotFoundException e){
            log.error("User with id: " + id + " not found");
            throw e;
        }
        return person;
    }

    @Override
    public List<Person> getPersonsByBookTitle(String bookTitle) throws EntityNotFoundException {
        List<Person> persons = new ArrayList<>();
        Optional<Book> book;
        Book b = new Book();
        try {
            persons = personRepository.findPersonsByBookTitle(bookTitle);
//          persons = personRepository.findByBooksContaining(book);
            if (persons.isEmpty()) throw new EntityNotFoundException(Person.class,0L);
            log.info("Persons with book title "+ bookTitle +" were found");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return persons;
    }

    @Override
    public List<Book> getAllBooksByPersonId(Long id) throws EntityNotFoundException {
        List<Book> books = new ArrayList<>();
        Person person;
        try {
            person = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Book.class,id));
            books = person.getAllBooks().stream().toList();
//            if (books.isEmpty()) throw new EntityNotFoundException(Book.class,0L);
            log.info("books of person with id "+ id  + " were found");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return books;
    }

    @Transactional
    @Override
    public Book removeBookFromPerson(Long personId, Long bookId) throws EntityNotFoundException {
        Person person;
        Book book;
        try {
            person = personRepository.findById(personId).orElseThrow(() -> new EntityNotFoundException(User.class,personId));
            book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException(Book.class,bookId));
            boolean isRemoved = personRepository.deleteSpecificPersonBook(personId,bookId)>0;
            if (!isRemoved) {
                log.error("Problem in deleting book");
            }
        }catch (EntityNotFoundException e){
            log.error("Error in deleting book");
            throw e;
        }
        return book;
    }
}