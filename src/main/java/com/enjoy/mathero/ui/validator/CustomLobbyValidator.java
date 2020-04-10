package com.enjoy.mathero.ui.validator;

import com.enjoy.mathero.ui.model.request.CustomLobbyRequestModel;
import com.enjoy.mathero.ui.model.request.QuestionRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CustomLobbyValidator implements Validator {

    @Autowired
    QuestionValidator questionValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomLobbyRequestModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomLobbyRequestModel details = (CustomLobbyRequestModel) target;

        if(details.getAuthorId() == null)
            errors.rejectValue("authorId", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "authorId", "error.field.empty");
        }

        if(details.getQuestions() == null || details.getQuestions().size()!=20)
            errors.rejectValue("questions", "error.customlobby.questions.amount");
        else{
            for(int i = 0;i<details.getQuestions().size();i++){
                errors.pushNestedPath("questions[" + i + "]");
                ValidationUtils.invokeValidator(questionValidator, details.getQuestions().get(i), errors);
                errors.popNestedPath();
            }
        }
    }
}
