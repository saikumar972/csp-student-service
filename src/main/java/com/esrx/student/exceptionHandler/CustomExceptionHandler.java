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
    public ResponseEntity<?> handleValidationErrors (MethodArgumentNotValidException exception){
        Map<String, Object> body = new LinkedHashMap<>();
        Map<String, String> errorMap=new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach((e)->{
            errorMap.put(e.getField(),e.getDefaultMessage());
        });
        // Put field errors at root so tests can assert $.fees etc.
        body.putAll(errorMap);
        body.put("httpStatus", HttpStatus.BAD_REQUEST.name());
        body.put("responseCode", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(CustomStudentException.class)
    public ResponseEntity<String> handleInvalidIdException(CustomStudentException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<String> handleTooManyRequests(TooManyRequestsException ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage());
    }

    @ExceptionHandler(RequestTimeoutException.class)
    public ResponseEntity<String> handleRequestTimeout(RequestTimeoutException ex) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(ex.getMessage());
    }

    @ExceptionHandler(BadGatewayException.class)
    public ResponseEntity<String> handleBadGateway(BadGatewayException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(ex.getMessage());
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<String> handleServiceUnavailable(ServiceUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
    }

    @ExceptionHandler(GatewayTimeoutException.class)
    public ResponseEntity<String> handleGatewayTimeout(GatewayTimeoutException ex) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(ex.getMessage());
    }
}
