package com.example.changebook.service;

import com.example.changebook.dto.StoreDTO.StoreRegisterDTO;
import com.example.changebook.model.Book;
import com.example.changebook.model.Store;
import com.example.changebook.model.StoreBook;
import com.example.changebook.service.exceptions.EntityAlreadyExistsException;
import com.example.changebook.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IStoreService {
    Store registerStore(StoreRegisterDTO dto) throws EntityAlreadyExistsException;
    Store updateStore(StoreRegisterDTO personDTO) throws EntityNotFoundException;
    Store deleteStore(Long id) throws EntityNotFoundException;
    List<Store> getStoresByName(String lastname) throws EntityNotFoundException;
    Store getStoreById(Long id) throws EntityNotFoundException;
    Store getStoreByUserId(Long id) throws EntityNotFoundException;
    List<Store> getStoresByBookTitle(String bookTitle) throws EntityNotFoundException;

    List<StoreBook> getAllBooksByStoreId(Long id) throws EntityNotFoundException;
    Book removeBookFromStore(Long personId, Long bookId) throws EntityNotFoundException;
}
