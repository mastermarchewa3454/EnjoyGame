package com.enjoy.mathero.ui.validator;

import com.enjoy.mathero.ui.model.request.MaxStageRequestModel;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class MaxStageValidator implements Validator {

    private static final int MAX_STAGE_AVAILABLE = 5;

    @Override
    public boolean supports(Class<?> clazz) {
        return MaxStageRequestModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MaxStageRequestModel details = (MaxStageRequestModel) target;
        if(details.getMaxStageCanPlay() == null)
            errors.rejectValue("maxStageCanPlay", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors,"maxStageCanPlay", "error.field.empty");
            if(details.getMaxStageCanPlay()>MAX_STAGE_AVAILABLE)
                errors.rejectValue("maxStageCanPlay", "error.user.maxstage");
            if(details.getMaxStageCanPlay()<0)
                errors.rejectValue("maxStageCanPlay", "error.number.negative");
        }
    }
}
