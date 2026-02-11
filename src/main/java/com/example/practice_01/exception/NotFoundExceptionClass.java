package com.example.practice_01.exception;

public class NotFoundExceptionClass extends RuntimeException{
    public NotFoundExceptionClass(String message) {
        super(message);
    }
}
