package io.github.cursodsousa.libraryapi.exceptions;

public class DuplicateRecordException extends RuntimeException{
    public DuplicateRecordException(String message) {
        super(message);
    }
}
