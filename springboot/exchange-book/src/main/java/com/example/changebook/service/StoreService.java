package com.example.changebook.service;

import com.example.changebook.dto.StoreDTO.StoreRegisterDTO;
import com.example.changebook.mapper.Mapper;
import com.example.changebook.model.*;
import com.example.changebook.repository.*;
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
public class StoreService implements IStoreService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final StoreBooksRepository storeBooksRepository;


    @Override
    @Transactional
    public Store registerStore(StoreRegisterDTO dto) throws EntityAlreadyExistsException {
        Store store;
        User user;

        try {
//            person = new Person(dto.getFirstname(), dto.getLastname());
//            user = User.NEW_PERSON(dto.getUsername(), dto.getPassword());
            store = Mapper.extractStoreFromStoreRegisterDTO(dto);
            user = Mapper.extractUserFromStoreRegisterDTO(dto);
            Optional<User> returnedUser = userRepository.findByUsername(dto.getUsername());
            if (returnedUser.isPresent()) throw new EntityAlreadyExistsException(User.class, dto.getUsername());
            store.addUser(user);
            storeRepository.save(store);
            log.info("Store added");
        } catch (EntityAlreadyExistsException e) {
            log.error("Problem in create store user" + e.getMessage());
            throw e;
        }
        return store;
    }

    @Override
    @Transactional
    public Store updateStore(StoreRegisterDTO personDTO) throws EntityNotFoundException {
        return null;
    }

    @Transactional
    @Override
    public Store deleteStore(Long id) throws EntityNotFoundException {
        Store store;
        try {
            store =  storeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Person.class,id));
//            notificationRepository.deleteAllByInterestedUser(person.getUser());
            storeRepository.deleteById(id);
            log.info("Store user deleted");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return store;
    }
    @Override
    public List<Store> getStoresByName(String lastname) throws EntityNotFoundException {
        return null;
    }

    @Override
    public Store getStoreById(Long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public Store getStoreByUserId(Long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public List<Store> getStoresByBookTitle(String bookTitle) throws EntityNotFoundException {
        return null;
    }

    @Override
    public List<StoreBook> getAllBooksByStoreId(Long id) throws EntityNotFoundException {
        List<StoreBook> books = new ArrayList<>();
        Store store;
        try {
            store = storeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Store.class,id));
            System.out.println(store.getId());
            books = store.getAllBooks().stream().toList();
            System.out.println(books.get(0).getBook().getId()+" bookid");
            System.out.println(books.get(0).getStore().getId()+" store_id");
            log.info("books of store with id "+ id  + " were found");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return books;
    }

    @Override
    @Transactional
    public Book removeBookFromStore(Long storeId, Long bookId) throws EntityNotFoundException {
        StoreBook storeBook;
        try{
            storeBook = storeBooksRepository.findById(new StoreBookId(storeId, bookId)).orElseThrow(() -> new EntityNotFoundException(Book.class,bookId));
            storeBook.getStore().getStoreBooks().remove(storeBook);
            storeBook.getBook().getStoreBooks().remove(storeBook);
            storeBooksRepository.delete(storeBook);
        }catch (EntityNotFoundException e){
            log.error("StoreBook not found");
            throw e;
        }
        return storeBook.getBook();
    }
}