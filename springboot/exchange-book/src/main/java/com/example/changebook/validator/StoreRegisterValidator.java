package com.example.changebook.validator;

import com.example.changebook.dto.StoreDTO.StoreRegisterDTO;
import com.example.changebook.dto.personDTO.RegisterPersonDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class StoreRegisterValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return StoreRegisterDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        StoreRegisterDTO storeRegisterDTO = (StoreRegisterDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "empty");
        if (storeRegisterDTO.getUsername().length() < 3 || storeRegisterDTO.getUsername().length() > 50) {
            errors.reject("username", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty");
        if (storeRegisterDTO.getPassword().length() < 3 || storeRegisterDTO.getPassword().length() > 500) {
            errors.reject("password", "size");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "empty");
        if (storeRegisterDTO.getName().length() < 3 || storeRegisterDTO.getName().length() > 50) {
            errors.reject("name", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "empty");
        if (storeRegisterDTO.getAddress().length() < 3 || storeRegisterDTO.getAddress().length() > 50) {
            errors.reject("address", "size");
        }
    }
}
