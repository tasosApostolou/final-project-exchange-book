package com.example.changebook.service;


import com.example.changebook.dto.authorDTO.AuthorInsertDTO;
import com.example.changebook.model.Author;
import com.example.changebook.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IAuthorService {
    Author insert(AuthorInsertDTO dto) throws Exception;

//    Teacher update(TeacherUpdateDTO dto) throws EntityNotFoundException;

    Author delete(Long id) throws EntityNotFoundException;
    List<Author> getAuthorsByName(String lastname) throws EntityNotFoundException;
    Author getAuthorById(Long id) throws EntityNotFoundException;
}
