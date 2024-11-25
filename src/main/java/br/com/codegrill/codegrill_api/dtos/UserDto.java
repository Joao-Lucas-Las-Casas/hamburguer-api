package br.com.codegrill.codegrill_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserDto(@NotBlank String name,
                      @NotBlank String email,
                      @NotBlank String password,
                      @NotBlank String taxId,
                      String phone,
                      String zipCode,
                      String image) {
}
