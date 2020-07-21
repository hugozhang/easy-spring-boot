package com.juma.template.advice;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collection;

public class CollectionValidator implements Validator {

    private Validator validator;

    public CollectionValidator(LocalValidatorFactoryBean validatorFactoryBean){
        this.validator = validatorFactoryBean;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(Collection.class.isAssignableFrom(target.getClass())) {
            Collection collection = (Collection) target;
            for (Object object : collection) {
                ValidationUtils.invokeValidator(validator,object,errors);
            }
        } else {
            ValidationUtils.invokeValidator(validator,target,errors);
        }

    }
}
