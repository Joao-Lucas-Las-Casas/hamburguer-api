package br.com.codegrill.codegrill_api.services;

import br.com.codegrill.codegrill_api.configs.security.filters.AuthSuccessHandler;
import br.com.codegrill.codegrill_api.dtos.LoginDto;
import br.com.codegrill.codegrill_api.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final AuthSuccessHandler authSuccessHandler;

    public LoginDto login(final LoginDto loginDto) {
        try {
            final var authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());
            final var authentication = authenticationManager.authenticate(authenticationToken);
            return authSuccessHandler.generateToken((User) authentication.getPrincipal());
        } catch (final Exception e) {
            throw new NotFoundException("Usuário inválido, troque o usuário ou a senha e tente novamente");
        }
    }
}
