package br.com.codegrill.codegrill_api.dtos;

import br.com.codegrill.codegrill_api.enums.CategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductsDto(
        Long id,
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotBlank
        String image,
        @NotNull
        BigDecimal price,
        BigDecimal discount,
        @NotNull
        CategoryEnum category,
        String introduction) {
}
