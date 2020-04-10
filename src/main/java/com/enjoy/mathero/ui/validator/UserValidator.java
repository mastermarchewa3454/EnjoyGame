package com.enjoy.mathero.ui.validator;

import com.enjoy.mathero.ui.model.request.UserDetailsRequestModel;
import com.enjoy.mathero.ui.model.request.UserLoginRequestModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    private static final int MAX_USERNAME_LENGTH = 50;
    private static final int MAX_FIRST_NAME_LENGTH = 50;
    private static final int MAX_LAST_NAME_LENGTH = 50;
    private static final int MAX_PASSWORD_LENGTH = 50;
    private static final int MAX_EMAIL_LENGTH = 50;
    private static final String EMAIL_PATTERN_STRING = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_PATTERN_STRING);

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDetailsRequestModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDetailsRequestModel details = (UserDetailsRequestModel) target;
        if(details.getUsername() == null)
            errors.rejectValue("username", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "username", "error.field.empty");
            if(details.getUsername().length()>MAX_USERNAME_LENGTH)
                errors.rejectValue("username", "error.user.username.toolong");
        }

        if(details.getFirstName() == null)
            errors.rejectValue("firstName", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "firstName", "error.field.empty");
            if(details.getFirstName().length()>MAX_FIRST_NAME_LENGTH)
                errors.rejectValue("firstname", "error.user.firstname.toolong");
        }

        if(details.getLastName() == null)
            errors.rejectValue("lastName", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "lastName", "error.field.empty");
            if(details.getLastName().length()>MAX_USERNAME_LENGTH)
                errors.rejectValue("lastname", "error.user.lastname.toolong");
        }

        if(details.getClassName() == null)
            errors.rejectValue("className", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "className", "error.field.empty");
        }

        if(details.getPassword() == null)
            errors.rejectValue("password", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "password", "error.field.empty");
            if(details.getPassword().length()>MAX_PASSWORD_LENGTH)
                errors.rejectValue("password", "error.user.password.toolong");
        }

        if(details.getEmail() == null)
            errors.rejectValue("email", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "email", "error.field.empty");
            if(details.getEmail().length()>MAX_EMAIL_LENGTH)
                errors.rejectValue("password", "error.user.password.toolong");
            Matcher matcher = EMAIL_PATTERN.matcher(details.getEmail());
            if(!matcher.matches())
                errors.rejectValue("email", "error.user.email.pattern");
        }
    }
}
