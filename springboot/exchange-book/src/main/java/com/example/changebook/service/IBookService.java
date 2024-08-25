package com.example.changebook.service;


import com.example.changebook.dto.BookDTO.BookInsertDTO;
import com.example.changebook.dto.BookDTO.StoreBookInsertDTO;
import com.example.changebook.model.Author;
import com.example.changebook.model.Book;
import com.example.changebook.model.StoreBook;
import com.example.changebook.service.exceptions.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface IBookService {
    Book insertBookToPerson(Long personID,BookInsertDTO dto) throws Exception;
//    Book insertBookToStore(Long storeId,BookInsertDTO dto) throws Exception;

    StoreBook insertBookToStore(Long storeId, StoreBookInsertDTO dto) throws Exception;

    Book delete(Long id) throws EntityNotFoundException;
    List<Book> getBookByTitle(String title) throws EntityNotFoundException;
    Book getBookById(Long id) throws EntityNotFoundException;
    List<Book> getBooksByAuthorName(String name) throws EntityNotFoundException;
//    Set<Book>  GetBooksByAuthor(Author author) throws EntityNotFoundException;
    public List<Book> getBookByPersonId(Long id) throws EntityNotFoundException;
//    public List<BookInfoDTO> getBooksByTitleWithDetails(String title);
}
