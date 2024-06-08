package com.example.changebook.validator;
import com.example.changebook.dto.usersDTO.UserUpdateDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserUpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserUpdateDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdateDTO userUpdateDTO = (UserUpdateDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","empty");
        if(userUpdateDTO.getUsername().length()< 3 || userUpdateDTO.getUsername().length() > 50){
            errors.reject("username", "size");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","empty");
        if(userUpdateDTO.getPassword().length()< 3 || userUpdateDTO.getPassword().length() > 50){
            errors.reject("password", "size");
        }

//        if(teacherService.inUsernameExists(teachereInsertDTO.getUsername())){
//            errors.reject(("username","exists"));
//        }
    }
}
