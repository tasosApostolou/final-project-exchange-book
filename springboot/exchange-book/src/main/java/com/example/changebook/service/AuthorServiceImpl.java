package com.example.changebook.service;

import com.example.changebook.dto.authorDTO.AuthorInsertDTO;
import com.example.changebook.mapper.Mapper;
import com.example.changebook.model.Author;
import com.example.changebook.model.Book;
import com.example.changebook.repository.AuthorRepository;
import com.example.changebook.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements IAuthorService{

    private final AuthorRepository authorRepository;
    @Override
    @Transactional
    public Author insert(AuthorInsertDTO dto) throws Exception {
        Author author = null;

        try{
            author = authorRepository.save(Mapper.mapToAuthor(dto));
            if(author.getId()==null){
                throw new Exception("Insert error");
            }
            log.info("insert succes for author with id"+ author.getId());
            return author;
        }catch (Exception e){
            log.error("insert error "+ e.getMessage());
            throw e;
        }
    }
    @Override
    @Transactional
    public Author delete(Long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public List<Author> getAuthorsByName(String name) throws EntityNotFoundException {
        List<Author> authors = new ArrayList<>();
        try {
            authors = authorRepository.findAuthorByNameStartingWith(name);
            if (authors.isEmpty()) throw new EntityNotFoundException(Book.class,0L);
            log.info("Books with title starting with "+ name +" were found");
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return authors;
    }

    @Override
    public Author getAuthorById(Long id) throws EntityNotFoundException {
        Author author;
        try {
            author = authorRepository.findAuthorById(id);
            if(author==null)throw new EntityNotFoundException(Author.class,id);
        }catch (EntityNotFoundException e){
            log.error(e.getMessage());
            throw e;
        }
        return author;
    }
}
