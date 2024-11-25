package br.com.codegrill.codegrill_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(@NotBlank String email,
                       @NotBlank String password,
                       String token,
                       Long expiresIn) {
    public LoginDto(final String token, final Long expiresIn) {
        this(null, null, token, expiresIn);
    }
}
