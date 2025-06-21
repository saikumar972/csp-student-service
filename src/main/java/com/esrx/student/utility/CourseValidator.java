package com.esrx.student.utility;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class CourseValidator implements ConstraintValidator<CourseValidation,String> {
    private static final List<String> VALID_COURSES = List.of("commerce", "science");
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> courseList=List.of("commerce","science");
        return value != null && VALID_COURSES.contains(value.toLowerCase());
    }
}
