package io.github.cursodsousa.libraryapi.exceptions;

public class OperationNotAllowedException extends RuntimeException{
    public OperationNotAllowedException(String message) {
        super(message);
    }
}
