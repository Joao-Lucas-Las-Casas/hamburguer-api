package br.com.codegrill.codegrill_api.controllers;

import br.com.codegrill.codegrill_api.dtos.LoginDto;
import br.com.codegrill.codegrill_api.dtos.LoginResponseDto;
import br.com.codegrill.codegrill_api.services.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid final LoginDto loginRequest) {
        final var loginDTO = loginService.login(loginRequest);
        return ResponseEntity.ok(new LoginResponseDto(loginDTO.token()));
    }
}
