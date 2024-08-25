package com.example.changebook.service;

import com.example.changebook.dto.StoreDTO.StoreRegisterDTO;
import com.example.changebook.dto.StoreDTO.StoreUpdateDTO;
import com.example.changebook.model.Book;
import com.example.changebook.model.Store;
import com.example.changebook.model.StoreBook;
import com.example.changebook.service.exceptions.EntityAlreadyExistsException;
import com.example.changebook.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IStoreService {
    Store registerStore(StoreRegisterDTO dto) throws EntityAlreadyExistsException;
    Store deleteStore(Long id) throws EntityNotFoundException;

    List<StoreBook> getAllBooksByStoreId(Long id) throws EntityNotFoundException;
    Book removeBookFromStore(Long storeId, Long bookId) throws EntityNotFoundException;
    List<StoreBook> getStoreBooksByBookTitle(String title) throws EntityNotFoundException;
   Store updateStore(StoreUpdateDTO dto) throws EntityNotFoundException;
   Store getStoreById(Long id) throws EntityNotFoundException;
//    List<Store> getStoresByName(String lastname) throws EntityNotFoundException;
//    Store getStoreByUserId(Long id) throws EntityNotFoundException;
//    List<Store> getStoresByBookTitle(String bookTitle) throws EntityNotFoundException;

    }

