package com.example.changebook.validator;

import com.example.changebook.dto.usersDTO.UserInsertDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserInsertValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserInsertDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserInsertDTO userInsertDTO = (UserInsertDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","empty");
        if(userInsertDTO.getUsername().length()< 3 || userInsertDTO.getUsername().length() > 50){
            errors.reject("username", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","empty");
        if(userInsertDTO.getPassword().length()< 3 || userInsertDTO.getPassword().length() > 500){
            errors.reject("password", "size");
        }



    }
}