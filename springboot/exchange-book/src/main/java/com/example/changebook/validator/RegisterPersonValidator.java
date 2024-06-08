package com.example.changebook.validator;

import com.example.changebook.dto.personDTO.RegisterPersonDTO;
import com.example.changebook.dto.usersDTO.UserInsertDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegisterPersonValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterPersonDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterPersonDTO registerPersonDTO = (RegisterPersonDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","empty");
        if(registerPersonDTO.getUsername().length()< 3 || registerPersonDTO.getUsername().length() > 50){
            errors.reject("username", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","empty");
        if(registerPersonDTO.getPassword().length()< 3 || registerPersonDTO.getPassword().length() > 50){
            errors.reject("password", "size");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"firstname","empty");
        if(registerPersonDTO.getFirstname().length()< 3 || registerPersonDTO.getFirstname().length() > 50){
            errors.reject("firstname", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"lastname","empty");
        if(registerPersonDTO.getLastname().length()< 3 || registerPersonDTO.getLastname().length() > 50){
            errors.reject("lastname", "size");
        }

//        if(teacherService.inUsernameExists(teachereInsertDTO.getUsername())){
//            errors.reject(("username","exists"));
//        }
    }
}