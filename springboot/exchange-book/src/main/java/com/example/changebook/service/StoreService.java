package com.example.changebook.service;

import com.example.changebook.dto.StoreDTO.StoreReadOnlyDTO;
import com.example.changebook.dto.StoreDTO.StoreRegisterDTO;
import com.example.changebook.dto.StoreDTO.StoreUpdateDTO;
import com.example.changebook.dto.usersDTO.UserReadOnlyDTO;
import com.example.changebook.mapper.Mapper;
import com.example.changebook.model.*;
import com.example.changebook.repository.*;
import com.example.changebook.service.exceptions.EntityAlreadyExistsException;
import com.example.changebook.service.exceptions.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
            user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
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
    public Store updateStore(StoreUpdateDTO dto) throws EntityNotFoundException {
        Store store;
        Store storeToUpdate;
        try {
            store = storeRepository.findById(dto.getId()).orElseThrow(()-> new EntityNotFoundException(User.class, dto.getId()));
            User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException(User.class,dto.getUserId()));
            storeToUpdate = Mapper.mapToStore(dto);
            storeToUpdate.setUser(user);
            store = storeRepository.save(storeToUpdate);
            log.info("Store with id: "+ store.getId()+ " was updated");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return store;
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
    public List<StoreBook> getAllBooksByStoreId(Long id) throws EntityNotFoundException {
        List<StoreBook> books = new ArrayList<>();
        Store store;
        try {
            store = storeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Store.class,id));
            System.out.println(store.getId());
            books = store.getAllBooks().stream().toList();
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

    @Override
    public List<StoreBook> getStoreBooksByBookTitle(String title) throws EntityNotFoundException {
        List<StoreBook> storeBooks = new ArrayList<>();
        try{
            storeBooks = storeBooksRepository.findByBook_Title(title);
            if (storeBooks.isEmpty()) throw new EntityNotFoundException(Book.class,0L);
            log.info("Books with title "+ title +" were found");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return  storeBooks;
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



}