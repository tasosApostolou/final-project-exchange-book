package com.example.changebook.validator;

import com.example.changebook.dto.personDTO.PersonUpdateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PersonUpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PersonUpdateDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonUpdateDTO personUpdateDTO = (PersonUpdateDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"firstname","empty");
        if(personUpdateDTO.getFirstname().length()< 3 || personUpdateDTO.getFirstname().length() > 50){
            errors.reject("firstname", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"lastname","empty");
        if(personUpdateDTO.getLastname().length()< 3 || personUpdateDTO.getLastname().length() > 50){
            errors.reject("password", "size");
        }

//        if(teacherService.inUsernameExists(teachereInsertDTO.getUsername())){
//            errors.reject(("username","exists"));
//        }
    }
}


