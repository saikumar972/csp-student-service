package com.esrx.student.exceptionHandler;

import com.esrx.student.dto.StudentErrorDto;
import com.esrx.student.utility.CustomStudentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StudentErrorDto> handleValidationErrors (MethodArgumentNotValidException exception){
        Map<String, String> errorMap=new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach((e)->{
            errorMap.put(e.getField(),e.getDefaultMessage());
        });
        StudentErrorDto studentErrorDto= StudentErrorDto.builder()
                .errorMap(errorMap)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(studentErrorDto);
    }

    @ExceptionHandler(CustomStudentException.class)
    public ResponseEntity<StudentErrorDto> handleInvalidIdException(CustomStudentException exception){
        StudentErrorDto studentErrorDto= StudentErrorDto.builder()
                .message(exception.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND)
                .responseCode(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(studentErrorDto);
    }

}
