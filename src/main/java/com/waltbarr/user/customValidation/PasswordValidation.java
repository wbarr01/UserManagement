package com.waltbarr.user.customValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidation {
    String message() default "Contraseña Invalida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
