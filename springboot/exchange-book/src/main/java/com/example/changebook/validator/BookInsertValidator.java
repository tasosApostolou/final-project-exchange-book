package com.example.changebook.validator;

import com.example.changebook.dto.BookDTO.BookInsertDTO;
import com.example.changebook.dto.authorDTO.AuthorInsertDTO;
import com.example.changebook.dto.usersDTO.UserInsertDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BookInsertValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BookInsertDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookInsertDTO bookInsertDTO = (BookInsertDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"title","empty");
        if(bookInsertDTO.getTitle().length()< 3 || bookInsertDTO.getTitle().length() > 50){
            errors.reject("username", "size");
        }
    }

}
