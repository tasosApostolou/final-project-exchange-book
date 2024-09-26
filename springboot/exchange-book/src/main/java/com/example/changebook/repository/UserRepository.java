package com.example.changebook.repository;

import com.example.changebook.model.Book;
import com.example.changebook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsernameStartingWith(String username);

    Optional<User> findByRole(String role);
    Optional<User> findByUsername(String username);
    User findUserByUsername(String username);
    User findUserById(Long id);




}