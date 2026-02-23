package com.example.practice_01.exception;

public class InvalidExceptionClass extends RuntimeException{
    public InvalidExceptionClass(String message) {
        super(message);
    }
}
