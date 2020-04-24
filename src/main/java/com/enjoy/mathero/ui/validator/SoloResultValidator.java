package com.enjoy.mathero.ui.validator;

import com.enjoy.mathero.ui.model.request.SoloResultRequestModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Class that validates solo result model
 *
 * @author Kamil Rogoda
 * @version 1.0.0
 */
@Component
public class SoloResultValidator implements Validator {

    private final static int MAX_STAGE_AVAILABLE = 5;

    @Override
    public boolean supports(Class<?> clazz) {
        return SoloResultRequestModel.class.equals(clazz);
    }

    /**
     * Performs validation
     * @param target solo result details
     * @param errors errors generated during validation
     */
    @Override
    public void validate(Object target, Errors errors) {
        SoloResultRequestModel details = (SoloResultRequestModel) target;

        if(details.getEasyCorrect() == null)
            errors.rejectValue("easyCorrect", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "easyCorrect", "error.field.empty");
            if(details.getEasyCorrect()>details.getEasyTotal())
                errors.rejectValue("easyCorrect", "error.result.questions.correctovertotal");
            if(details.getEasyCorrect()<0)
                errors.rejectValue("easyCorrect", "error.number.negative");
        }

        if(details.getEasyTotal() == null)
            errors.rejectValue("easyTotal", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "easyTotal", "error.field.empty");
            if(details.getEasyTotal()<0)
                errors.rejectValue("easyTotal", "error.number.negative");
        }

        if(details.getMediumCorrect() == null)
            errors.rejectValue("mediumCorrect", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "mediumCorrect", "error.field.empty");
            if(details.getMediumCorrect()>details.getMediumTotal())
                errors.rejectValue("mediumCorrect", "error.result.questions.correctovertotal");
            if(details.getMediumCorrect()<0)
                errors.rejectValue("mediumCorrect", "error.number.negative");
        }

        if(details.getMediumTotal() == null)
            errors.rejectValue("mediumTotal", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "mediumTotal", "error.field.empty");
            if(details.getMediumTotal()<0)
                errors.rejectValue("mediumTotal", "error.number.negative");
        }

        if(details.getHardCorrect() == null)
            errors.rejectValue("hardCorrect", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "hardCorrect", "error.field.empty");
            if(details.getHardCorrect()>details.getHardTotal())
                errors.rejectValue("hardCorrect", "error.result.questions.correctovertotal");
            if(details.getHardCorrect()<0)
                errors.rejectValue("hardCorrect", "error.number.negative");
        }

        if(details.getHardTotal() == null)
            errors.rejectValue("hardTotal", "error.field.required");
        else{
            ValidationUtils.rejectIfEmpty(errors, "hardTotal", "error.field.empty");
            if(details.getHardTotal()<0)
                errors.rejectValue("hardTotal", "error.number.negative");
        }

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
