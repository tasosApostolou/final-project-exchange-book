package com.example.changebook.repository;

import com.example.changebook.model.Book;
import com.example.changebook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsernameStartingWith(String username);

    Optional<User> findByRole(String role);
    Optional<User> findByUsername(String username);
    User findUserByUsername(String username);
    User findUserById(Long id);

    @Query("SELECT u FROM User u JOIN u.books b WHERE b.id = :bookId")
    List<User> findUsersByBookId(Long bookId);

    @Query("SELECT u FROM User u JOIN u.books b WHERE b.title = :title")
    List<User> findUsersByBookTitle(String title);

    List<User> findByBooksContaining(Optional<Book> book);
    List<User> findByBooksContains(Book book);
    List<User> findUserByBooksContains(Book book);
    @Query("SELECT u.id FROM User u JOIN u.books b WHERE b.id = :bookId")
    List<Long> findUserIdsByBookId(Long bookId);


}