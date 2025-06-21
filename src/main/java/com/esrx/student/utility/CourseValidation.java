package com.esrx.student.utility;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = CourseValidator.class)
public @interface CourseValidation {
    public String message() default "Invalid course type it should be either commerce or science";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
