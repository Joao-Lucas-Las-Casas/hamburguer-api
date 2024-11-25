package br.com.codegrill.codegrill_api.configs.security.filters;

import br.com.codegrill.codegrill_api.configs.security.service.UserDetailsServiceImpl;
import br.com.codegrill.codegrill_api.dtos.LoginDto;
import br.com.codegrill.codegrill_api.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class AuthSuccessHandler {
    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.expiration}")
    private Integer expTime;
    @Value("${jwt.secret}")
    private String secret;

    public LoginDto generateToken(final User userDetails) {
        final var user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Nome de usuário não encontrado"));

        final var expiresIn = Instant.ofEpochMilli(ZonedDateTime
                .now(ZoneId.of("America/Sao_Paulo"))
                .toInstant()
                .toEpochMilli() + expTime);

        final var token = JWT.create()
                .withIssuer("CodeGrill API")
                .withSubject(user.getUsername())
                .withClaim("roles", user.getRoles().stream().map(rolesEntity -> rolesEntity.getRoleName().name()).toList())
                .withExpiresAt(expiresIn)
                .sign(Algorithm.HMAC256(secret));

        return new LoginDto(token, expiresIn.toEpochMilli());
    }

    public UsernamePasswordAuthenticationToken getAuthentication(final HttpServletRequest request) {
        final var token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isNull(token) || !token.startsWith(TOKEN_PREFIX)) {
            return null;
        }
        final var username = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("CodeGrill API")
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
        if (isNull(username) || username.isEmpty()) {
            return null;
        }
        final var userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
    }
}