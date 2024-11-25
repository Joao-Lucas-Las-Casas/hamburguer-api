package br.com.codegrill.codegrill_api.configs.security.filters;

import br.com.codegrill.codegrill_api.dtos.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
@NonNullApi
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final AuthSuccessHandler authSuccessHandler;

    @Override
    @SneakyThrows
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) {
        try {
            final var auth = authSuccessHandler.getAuthentication(request);
            if (isNull(auth)) {
                chain.doFilter(request, response);
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);
        } catch (final Exception e) {
            final var responseStream = response.getOutputStream();
            final var mapper = new ObjectMapper();
            final var exception = new ExceptionResponse("Token expired, log in again", LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).toString());

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            mapper.writeValue(responseStream, exception);
            responseStream.flush();
        }
    }
}