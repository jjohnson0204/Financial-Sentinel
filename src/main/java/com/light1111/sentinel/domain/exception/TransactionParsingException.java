package com.light1111.sentinel.domain.exception;

public class TransactionParsingException extends RuntimeException {
    public TransactionParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
