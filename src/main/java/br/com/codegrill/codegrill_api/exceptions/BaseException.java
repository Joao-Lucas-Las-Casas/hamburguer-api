package br.com.codegrill.codegrill_api.exceptions;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final Integer code;

    protected BaseException(final String message, final Integer code) {
        super(message);
        this.code = code;
    }
}
