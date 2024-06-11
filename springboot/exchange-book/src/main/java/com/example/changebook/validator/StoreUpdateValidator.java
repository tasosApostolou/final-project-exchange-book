package com.example.changebook.validator;

import com.example.changebook.dto.StoreDTO.StoreUpdateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class StoreUpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return StoreUpdateDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        StoreUpdateDTO storeUpdateDTO = (StoreUpdateDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","empty");
        if(storeUpdateDTO.getName().length()< 3 || storeUpdateDTO.getName().length() > 50){
            errors.reject("name", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"address","empty");
        if(storeUpdateDTO.getAddress().length()< 3 || storeUpdateDTO.getAddress().length() > 50){
            errors.reject("address", "size");
        }
    }
}


