package com.esrx.student.controller.exceptionHandler;

import com.esrx.student.utility.InvalidIdAndNameException;
import com.esrx.student.utility.InvalidStudentIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> handleValidationErrors (MethodArgumentNotValidException exception){
        Map<String, String> errorMap=new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach((e)->{
            errorMap.put(e.getField(),e.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(InvalidStudentIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidIdException(InvalidStudentIdException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(InvalidIdAndNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidIdAndNameException(InvalidIdAndNameException exception){
        return exception.getMessage();
    }



}
