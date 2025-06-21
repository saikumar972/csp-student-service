package com.esrx.student.utility;

public class InvalidStudentIdException extends RuntimeException{
    public InvalidStudentIdException(String message){
        super(message);
    }
}
