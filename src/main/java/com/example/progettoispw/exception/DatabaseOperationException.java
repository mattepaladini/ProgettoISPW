package com.example.progettoispw.exception;

public class DatabaseOperationException extends BaseException {
    public DatabaseOperationException(String message) {
        super(message);
    }

    public DatabaseOperationException(DatabaseExceptionMessages errorType) {
        super(errorType.getMessage());
    }
}

