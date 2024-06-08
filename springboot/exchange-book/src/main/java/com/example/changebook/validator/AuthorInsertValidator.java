package com.example.changebook.validator;

import com.example.changebook.dto.authorDTO.AuthorInsertDTO;
import com.example.changebook.dto.usersDTO.UserInsertDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AuthorInsertValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AuthorInsertDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        AuthorInsertDTO authorInsertDTO = (AuthorInsertDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","empty");
        if(authorInsertDTO.getName().length()< 3 || authorInsertDTO.getName().length() > 50){
            errors.reject("username", "size");
        }

//        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","empty");
//        if(userInsertDTO.getPassword().length()< 3 || userInsertDTO.getPassword().length() > 50){
//            errors.reject("password", "size");
//        }

//        if(teacherService.inUsernameExists(teachereInsertDTO.getUsername())){
//            errors.reject(("username","exists"));
//        }
    }
}
