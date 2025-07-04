package com.waltbarr.user.customValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordValidator implements ConstraintValidator<PasswordValidation,String> {

    @Value("${user.password.regex}")
    private String passwordRegex;
    private String  message = "Contraseña Invalida";// could be customizable too
    private Pattern pattern;

    /**
     * Initialize Validator with Regex
     */
    @Override
    public void initialize(PasswordValidation constraintAnnotation) {
        pattern = Pattern.compile(passwordRegex);
    }

    /**
     * Validate password field
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if(password==null) return false;

        if( !pattern.matcher(password).matches()){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
        return true;
    }
}
