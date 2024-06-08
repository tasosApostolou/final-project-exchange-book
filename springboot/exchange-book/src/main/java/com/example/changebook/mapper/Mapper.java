package com.example.changebook.mapper;

import com.example.changebook.dto.BookDTO.*;
import com.example.changebook.dto.NotificationDTO.NotificationReadOnlyDTO;
import com.example.changebook.dto.NotificationDTO.NotificationUpdateDTO;
import com.example.changebook.dto.StoreDTO.StoreReadOnlyDTO;
import com.example.changebook.dto.StoreDTO.StoreRegisterDTO;
import com.example.changebook.dto.authorDTO.AuthorInsertDTO;
import com.example.changebook.dto.authorDTO.AuthorReadOnlyDTO;


import com.example.changebook.dto.loginDTO.LoginResponseDTO;
import com.example.changebook.dto.personDTO.PersonReadonlyDTO;
import com.example.changebook.dto.personDTO.PersonUpdateDTO;
import com.example.changebook.dto.personDTO.RegisterPersonDTO;
import com.example.changebook.dto.usersDTO.UserInsertDTO;
import com.example.changebook.dto.usersDTO.UserReadOnlyDTO;
import com.example.changebook.dto.usersDTO.UserUpdateDTO;
import com.example.changebook.model.*;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static Author mapToAuthor(AuthorInsertDTO dto) {
        Author author = new Author();
        author.setName(dto.name);
        return author;
    }

    public static AuthorReadOnlyDTO mapToReadOnlyDTO(Author author) {
        //      return new AuthorReadOnlyDTO(teacher.getId(), auth.getSsn(), teacher.getFirstname(), teacher.getLastname(), teacher.getUser(), teacher.getSpeciality());
        AuthorReadOnlyDTO authorDTO = new AuthorReadOnlyDTO(author.getId(), author.getName());
        return authorDTO;
    }

    public static Book mapToBook(BookInsertDTO dto) {
        Book book = new Book();
        book.setTitle(dto.Title);

//        Set<Book> books = new HashSet<>();
//        for(BookInsertDTO bookInsertDTO :  dto.authorInsertDTO.booksDTO){
//            books.add(new Book(bookInsertDTO.Title,mapToAuthor(dto.authorInsertDTO)));
//        }
        Author author = new Author();

        author.setName(dto.author.name);
//        book.setAuthor(author);

        book = new Book(null,dto.getTitle(),author);
        return book;
    }

//    public static Notification mapToNotification(NotificationInsertDTO dto){
////    Author author = new Author();
////    BookInsertDTO bookInsertDTO = dto.getBook();
////    Book book = mapToBook(dto.getBook());
////    AuthorInsertDTO authorInsertDTO = bookInsertDTO.getAuthorInsertDTO();
////    author = mapToAuthor(authorInsertDTO);
////    book.setAuthor(author);
////    return new Notification(null,mapToUser(dto.getUser()),book,dto.getTimestamp(),dto.getIsSeen());
//        return new Notification(null,dto.getInterested(),dto.getUser(),dto.getBook(),dto.getTimestamp(),dto.getIsSeen());
//
//    }
    public static Notification mapToNotification(NotificationUpdateDTO dto){
        Notification notification = new Notification();
        notification.setId(dto.getId());
        notification.setInterestedUser(dto.getInterested());
        notification.setHolderUSer(dto.getUser());
        notification.setBook(dto.getBook());
        notification.setType(dto.getNotificationType());
        notification.setSeen(dto.getIsSeen());
        return notification;
    }

    public static Book mapToBook(BookUpdateDTO dto){
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setTitle(dto.getTitle());
        return book;

    }
    public static BookReadOnlyDTO mapToReadOnlyDTO(Book book) {
        AuthorReadOnlyDTO authorReadOnlyDTO = new AuthorReadOnlyDTO();
        authorReadOnlyDTO = mapToReadOnlyDTO(book.getAuthor());
        BookReadOnlyDTO bookReadOnlyDTO = new BookReadOnlyDTO();
        bookReadOnlyDTO.setId(book.getId());
        bookReadOnlyDTO.setTitle(book.getTitle());
        bookReadOnlyDTO.setAuthor(authorReadOnlyDTO);
        return bookReadOnlyDTO;
    }

    public static StoreBookReadOnlyDTO mapToReadOnlyDTO(StoreBook bookStore){
        AuthorReadOnlyDTO authorReadOnlyDTO = new AuthorReadOnlyDTO();
        authorReadOnlyDTO = mapToReadOnlyDTO(bookStore.getBook().getAuthor());
        StoreBookReadOnlyDTO readOnlyDTO = new StoreBookReadOnlyDTO(bookStore.getStore().getId(),bookStore.getBook().getId(), bookStore.getPrice());
        return readOnlyDTO;
    }
    public static NotificationReadOnlyDTO mapToReadOnlyDTO(Notification notification){
        NotificationReadOnlyDTO readOnlyDTO = new NotificationReadOnlyDTO();
        readOnlyDTO.setId(notification.getId());
        readOnlyDTO.setInterested(mapToReadOnlyDTO(notification.getInterestedUser()));
        readOnlyDTO.setUser(mapToReadOnlyDTO(notification.getHolderUSer()));
        readOnlyDTO.setBook(mapToReadOnlyDTO(notification.getBook()));
        readOnlyDTO.setCreatedAt(notification.getCreatedAt());
        readOnlyDTO.setNotificationType(notification.getType());
        readOnlyDTO.setIsSeen(notification.getIsSeen());
        return readOnlyDTO;
    }


    public static BookReadWithPersonsSetDTO mapToReadOnlyDTOwithPersonDetails(Book book) {
        UserReadOnlyDTO userReadOnlyDTO;
        // //     return new TeacherReadOnlyDTO(book.getId(), auth.getSsn(), teacher.getFirstname(), teacher.getLastname(), teacher.getUser(), teacher.getSpeciality());
        AuthorReadOnlyDTO authorReadOnlyDTO = new AuthorReadOnlyDTO();
        authorReadOnlyDTO = mapToReadOnlyDTO(book.getAuthor());
        BookReadWithPersonsSetDTO bookReadOnlyDTO = new BookReadWithPersonsSetDTO();
        bookReadOnlyDTO.setId(book.getId());
        bookReadOnlyDTO.setTitle(book.getTitle());
        bookReadOnlyDTO.setAuthor(authorReadOnlyDTO);
        List<Person> persons = new ArrayList<>();
        persons = book.getAllPersons().stream().toList();
        List<PersonReadonlyDTO> personDTOs = new ArrayList<>();
        for (Person p : persons){
            personDTOs.add(mapToReadOnlyDTO(p));
        }
        bookReadOnlyDTO.setPersons(personDTOs);
        return bookReadOnlyDTO;
    }

    public static PersonReadonlyDTO mapToReadOnlyDTO(Person person){
        PersonReadonlyDTO personReadonlyDTO = new PersonReadonlyDTO(person.getId(),person.getFirstname(),person.getLastname());
        personReadonlyDTO.setUserId(person.getUser().getId());
//        personReadonlyDTO.setUser(mapToReadOnlyDTO(person.getUser()));
        return personReadonlyDTO;
    }
    public static StoreReadOnlyDTO mapToReadOnlyDTO(Store store){
        StoreReadOnlyDTO storeReadOnlyDTO = new StoreReadOnlyDTO(store.getId(), store.getName(), store.getAddress());
        return storeReadOnlyDTO;
    }
    public static User mapToUser(UserInsertDTO dto) {
        return new User(null, dto.getUsername(), dto.getPassword(), Role.valueOf(dto.getRole()));

    }

    public static User mapToUser(UserUpdateDTO dto) {
        return new User(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getRole());
    }

    public static Person mapToPerson(PersonUpdateDTO dto){
        Person person = new Person(dto.getFirstname(), dto.getLastname());
        person.setId(dto.getId());
        return person;

    }
    public static Person extractPersonFromRegisterPersonDTO(RegisterPersonDTO dto){
        return new Person(dto.getFirstname(), dto.getLastname());
    }

    public static User extractUserFromRegisterPersonDTO(RegisterPersonDTO dto){
        return User.NEW_PERSON(dto.getUsername(), dto.getPassword());
    }

    public static Store extractStoreFromStoreRegisterDTO(StoreRegisterDTO dto){
        return new Store(dto.getName(), dto.getAddress());
    }

    public static User extractUserFromStoreRegisterDTO(StoreRegisterDTO dto){
        return User.NEW_STORE(dto.getUsername(), dto.getPassword());
    }

    public static UserReadOnlyDTO mapToReadOnlyDTO(User user) {
        return new UserReadOnlyDTO(user.getId(), user.getUsername(), String.valueOf(user.getRole()));
    }

    public static LoginResponseDTO mapToLoginResponseDTO(User user){
        if (user.getRole() == Role.PERSONAL){
            return new LoginResponseDTO(user.getId(),user.getUsername(),Role.PERSONAL,user.getPerson().getId());
        }else {
            return new LoginResponseDTO(user.getId(),user.getUsername(),Role.STORE,user.getStore().getId());
        }
    }

}