package com.example.rpg.exception;

// 例外の宣言 [cite: 12]
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
