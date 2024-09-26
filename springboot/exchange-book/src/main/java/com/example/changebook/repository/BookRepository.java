package com.example.changebook.repository;

import com.example.changebook.model.Author;
import com.example.changebook.model.Book;
import com.example.changebook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  List<Book>  findBookByTitleStartingWith(String title);
  Book findBookById(Long id);
  List<Book> findBookByTitle(String title);
  List<Book> findByAuthor_Name(String name);
  List<Book> findByTitleContaining(String title);
  Book findBookByAuthor(Author author);
  List<Book> findAllByAuthor(Author author);


//  @Query("SELECT b FROM Book b JOIN b.users u WHERE u.id = :userId")
//  List<Book> findBooksByUserId(Long userId); 1
    @Query("SELECT b FROM Book b JOIN b.persons p WHERE p.id = :personId")
  List<Book> findBooksByPersonId(Long personId);


//  @Query("SELECT b FROM Book b JOIN b.users u WHERE u.username = :title")
//  List<Book> findBooksByUserUsername(String title);

}
