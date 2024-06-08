package com.example.changebook.repository;

import com.example.changebook.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAuthorById(Long id);
    List<Author> findAuthorByNameStartingWith(String name);
    Optional<Author> findByName(String name);
    Author findAuthorByName(String name);
}
