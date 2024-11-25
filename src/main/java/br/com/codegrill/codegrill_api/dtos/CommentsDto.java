package br.com.codegrill.codegrill_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record CommentsDto(@NotBlank String comment) {
}
