package com.enjoy.mathero.ui.validator;

import com.enjoy.mathero.ui.model.request.QuestionRequestModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Class that validates question model
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
@Component
public class QuestionValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return QuestionRequestModel.class.equals(clazz);
    }

    /**
     * Performs validation
     * @param target question details
     * @param errors errors generated during validation
     */
    @Override
    public void validate(Object target, Errors errors) {
        QuestionRequestModel details = (QuestionRequestModel) target;

        if(details.getContent() == null)
            errors.rejectValue("content", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "content", "error.field.empty");
        }

        if(details.getCorrectAnswer() == null)
            errors.rejectValue("correctAnswer", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "correctAnswer", "error.field.empty");
        }
    }
}
