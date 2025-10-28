package com.esrx.student.exceptionHandler;

import com.esrx.student.dto.StudentErrorDto;
import com.esrx.student.utility.*;
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

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<StudentErrorDto> handleTooManyRequests(TooManyRequestsException ex) {
        return buildError(ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(RequestTimeoutException.class)
    public ResponseEntity<StudentErrorDto> handleRequestTimeout(RequestTimeoutException ex) {
        return buildError(ex.getMessage(), HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(BadGatewayException.class)
    public ResponseEntity<StudentErrorDto> handleBadGateway(BadGatewayException ex) {
        return buildError(ex.getMessage(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<StudentErrorDto> handleServiceUnavailable(ServiceUnavailableException ex) {
        return buildError(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(GatewayTimeoutException.class)
    public ResponseEntity<StudentErrorDto> handleGatewayTimeout(GatewayTimeoutException ex) {
        return buildError(ex.getMessage(), HttpStatus.GATEWAY_TIMEOUT);
    }

    // 🔧 Common builder method
    private ResponseEntity<StudentErrorDto> buildError(String message, HttpStatus status) {
        StudentErrorDto dto = StudentErrorDto.builder()
                .message(message)
                .httpStatus(status)
                .responseCode(status.value())
                .build();
        return ResponseEntity.status(status).body(dto);
    }
}


