package com.example.changebook.repository;

import com.example.changebook.model.Author;
import com.example.changebook.model.Book;
import com.example.changebook.model.Person;
import com.example.changebook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    List<Person> findByLastnameStartingWith(String name);
    @Query("SELECT p FROM Person p JOIN p.books b WHERE b.title = :title")
    List<Person> findPersonsByBookTitle(String title);
    Person findPersonById(Long id);
    @Query("SELECT p FROM Person p JOIN p.books b WHERE b.id = :bookId")
    List<Person> findPersonsByBookId(Long bookId);
    List<Person> findByBooksContaining(Optional<Book> book);
    List<Person> findByBooksContains(Book book);
    List<Person> findPersonByBooksContains(Book book);
    @Query("SELECT p.id FROM Person p JOIN p.books b WHERE b.id = :bookId")
    List<Long> findPersonIdsByBookId(Long bookId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM person_books WHERE person_id = :personId AND book_id = :bookId", nativeQuery = true)
    int deleteSpecificPersonBook(@Param("personId") Long userId, @Param("bookId") Long bookId);

}
