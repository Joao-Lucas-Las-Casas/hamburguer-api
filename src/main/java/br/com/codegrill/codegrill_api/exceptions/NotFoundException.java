package br.com.codegrill.codegrill_api.exceptions;

public class NotFoundException extends BaseException {
    public NotFoundException(final String message) {
        super(message, 404);
    }
}
