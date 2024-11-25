package br.com.codegrill.codegrill_api.handlers;

import br.com.codegrill.codegrill_api.dtos.ExceptionResponse;
import br.com.codegrill.codegrill_api.dtos.ExceptionValidatorsResponse;
import br.com.codegrill.codegrill_api.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";

    @ExceptionHandler(value = { BaseException.class })
    public ResponseEntity<ExceptionResponse> resourceNotFoundExceptionHandler(final BaseException ex) {
        final var statusCode = HttpStatus.valueOf(ex.getCode());
        final var exception = new ExceptionResponse(ex.getMessage(), LocalDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)).toString());
        return new ResponseEntity<>(exception, statusCode);
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ExceptionValidatorsResponse> handleValidationExceptions(final MethodArgumentNotValidException ex) {
        final var errors = new HashMap<String, List<String>>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            final var fieldName = ((FieldError) error).getField();
            final var errorMessage = error.getDefaultMessage();

            errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
        });

        final var exceptionValidatorsResponse = new ExceptionValidatorsResponse("Bad Request", ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)), errors);

        return ResponseEntity.badRequest().body(exceptionValidatorsResponse);
    }
}
