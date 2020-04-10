package com.enjoy.mathero.ui.validator;

import com.enjoy.mathero.ui.model.request.DuoResultRequestModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class DuoResultValidator implements Validator {

    private final static int MAX_STAGE_AVAILABLE = 5;

    @Override
    public boolean supports(Class<?> clazz) {
        return DuoResultRequestModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DuoResultRequestModel details = (DuoResultRequestModel) target;

        if(details.getScore() == null)
            errors.rejectValue("score", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "score", "error.field.empty");
            if(details.getScore()<0)
                errors.rejectValue("score", "error.number.negative");
        }

        if(details.getStageNumber() == null)
            errors.rejectValue("stageNumber", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "stageNumber", "error.field.empty");
            if(details.getStageNumber()>MAX_STAGE_AVAILABLE)
                errors.rejectValue("stageNumber", "error.result.stagenumber.toobig");
            if(details.getStageNumber()<0)
                errors.rejectValue("stageNumber", "error.number.negative");
        }
    }
}
