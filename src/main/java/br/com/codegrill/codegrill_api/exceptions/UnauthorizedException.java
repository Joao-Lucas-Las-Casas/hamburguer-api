package br.com.codegrill.codegrill_api.exceptions;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(final String message) {
        super(message, 401);
    }
}
