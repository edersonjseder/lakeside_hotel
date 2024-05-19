package com.lakesidehotel.validations;

import com.lakesidehotel.validations.impl.UsernameConstraintImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.lakesidehotel.constants.UserMessagesConstants.USER_USERNAME_INVALID_MESSAGE;


@Documented
@Constraint(validatedBy = UsernameConstraintImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {
    // Default parameters of Validation Bean of Spring validation
    String message() default USER_USERNAME_INVALID_MESSAGE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
