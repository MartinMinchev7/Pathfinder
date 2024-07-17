package bg.softuni.pathfinder.validation.annotation;

import bg.softuni.pathfinder.validation.validator.ValidatePasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = ValidatePasswordValidator.class)
public @interface ValidatePasswords {
    String message() default "Test password validator";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
