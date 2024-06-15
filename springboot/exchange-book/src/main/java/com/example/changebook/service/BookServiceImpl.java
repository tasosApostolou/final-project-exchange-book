package com.example.changebook.service;

import com.example.changebook.dto.BookDTO.BookInsertDTO;
import com.example.changebook.dto.BookDTO.StoreBookInsertDTO;
import com.example.changebook.mapper.Mapper;
import com.example.changebook.model.*;
import com.example.changebook.repository.*;
import com.example.changebook.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements IBookService{
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PersonRepository personRepository;
    private final StoreRepository storeRepository;
    private final StoreBooksRepository storeBooksRepository;

    @Override
    public Book getBookById(Long id) throws EntityNotFoundException {
        Book book;
        try {
            book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Book.class, id));
            log.info("book with id "+ id + " found");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return book;
    }

    @Override
    public List<Book> getBookByPersonId(Long id) throws EntityNotFoundException {
        List<Book> books = new ArrayList<>();
        try {
            books = bookRepository.findBooksByPersonId(id);
            if (books.isEmpty()) throw new EntityNotFoundException(Book.class,id);
            log.info("books of person with id "+ id  + " were found");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return books;
    }
    @Override
    public List<Book> getBookByTitle(String title) throws EntityNotFoundException {
        List<Book> books = new ArrayList<>();
        try {
            books = bookRepository.findBookByTitleStartingWith(title);
            if (books.isEmpty()) throw new EntityNotFoundException(Book.class,0L);
            log.info("Books with title starting with "+ title +" were found");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return books;
    }

    @Override
    @Transactional
    public Book insertBookToPerson(Long personId,BookInsertDTO dto) throws Exception {
        Book book;
        Author insertedAuthor;
        Book inserted;
        Person person;
        Author author = new Author();
        try{
            person = personRepository.findById(personId).orElseThrow(() -> new EntityNotFoundException(Person.class,personId));
            author = Mapper.mapToAuthor(dto.author);
            insertedAuthor = authorRepository.findAuthorByName(dto.author.getName());
            if(insertedAuthor == null){ //create new author if does not exist
                insertedAuthor = authorRepository.save(author);
            }
            Book bookToAdd = Mapper.mapToBook(dto);
//            List<Book> listWithSameTitleBooks = person.getBooks().stream().filter(book1 -> book1.getTitle()== dto.Title).collect(Collectors.toList());
            List<Book> listWithSameTitleBooks = bookRepository.findBookByTitle(dto.getTitle());
            Optional<Book> optBook= listWithSameTitleBooks.stream().filter(book1 -> book1.isTheSameBook(bookToAdd)).findFirst();//lets say that if book title and author name is the same then book is the same
            if (optBook.isPresent()){
                inserted = optBook.get();
                inserted.addAuthor(insertedAuthor);
                inserted.addPerson(person);
            }
            else{
//            if (!listWithSameTitleBooks.stream().anyMatch(b -> b.isTheSameBook(bookToAdd))){
               bookToAdd.addAuthor(insertedAuthor);
               bookToAdd.addPerson(person);
                inserted = bookRepository.save(bookToAdd); // save book if does not exists
            }
            if (inserted == null){
                throw new Exception("Problem in inserting book");
            }
            log.info("insert succes for book with id"+ inserted.getId());
            return inserted;
        }catch (Exception e){
            log.error("insert error " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public StoreBook insertBookToStore(Long storeId, StoreBookInsertDTO dto) throws Exception {
        Book book;
        Author insertedAuthor;
        Store store;
        Author author = new Author();
        StoreBook storeBook;
        try{
            store = storeRepository.findById(storeId).orElseThrow(() -> new EntityNotFoundException(Person.class,storeId));
            author = Mapper.mapToAuthor(dto.book.author);
//            insertedAuthor = authorRepository.findByName(dto.authorInsertDTO.getName()).orElse(authorRepository.save(author));
            insertedAuthor = authorRepository.findAuthorByName(dto.book.author.getName());
            if(insertedAuthor == null){ //creates new author if does not exist
                insertedAuthor = authorRepository.save(author);
            }
            book = Mapper.mapToBook(dto.book);
            Book bookToAdd =  new Book();
            Boolean thereIsSameBook = store.getAllBooks().stream().anyMatch(bookStore -> bookStore.getBook().isTheSameBook(book));//lets say if author name and book title is the same so books is the same
            if (!thereIsSameBook){
                bookToAdd = bookRepository.save(book); // save book if does not exists
            }
            bookToAdd.addAuthor(insertedAuthor);
            storeBook = new StoreBook(store,bookToAdd,dto.Price);
            store.getStoreBooks().add(storeBook);
            bookToAdd.getStoreBooks().add(storeBook);
            storeBook = storeBooksRepository.save(storeBook);
            log.info("insert succes for book with id"+ bookToAdd.getId());
            return storeBook;
        }catch (Exception e){
            log.error("insert error " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public Book delete(Long id) throws EntityNotFoundException {
        return null;
    }

    public List<Book> getBooksByAuthorName(String authorName) throws EntityNotFoundException {
        List<Book> books = new ArrayList<>();
        try {
            books = bookRepository.findByAuthor_Name(authorName);
            if (books.isEmpty()) throw new EntityNotFoundException(Author.class,0L);
            log.info("Books with author name "+ authorName +" were found");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return books;
    }
}
