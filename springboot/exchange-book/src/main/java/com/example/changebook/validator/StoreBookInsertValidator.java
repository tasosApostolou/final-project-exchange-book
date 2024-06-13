package com.example.changebook.validator;

import com.example.changebook.dto.BookDTO.BookInsertDTO;
import com.example.changebook.dto.BookDTO.StoreBookInsertDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class StoreBookInsertValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return StoreBookInsertDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        StoreBookInsertDTO storeBookInsertDTO = (StoreBookInsertDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"book","empty");
        if(storeBookInsertDTO.getBook().getTitle().length()< 3 || storeBookInsertDTO.getBook().getTitle().length() > 50){
            errors.reject("title", "size");
        }
        if(storeBookInsertDTO.getPrice()<=0){

            errors.reject("price", "positive number");
        }
    }
}
