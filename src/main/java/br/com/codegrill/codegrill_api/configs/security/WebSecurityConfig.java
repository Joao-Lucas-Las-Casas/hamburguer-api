package br.com.codegrill.codegrill_api.configs.security;

import br.com.codegrill.codegrill_api.configs.security.filters.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityConfiguration(final HttpSecurity httpSecurity) {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authManager -> {
                    final var productsBasePatch = "/v1/products";
                    authManager
                            .requestMatchers(
                                    antMatcher(HttpMethod.OPTIONS, "/**"),
                                    antMatcher(HttpMethod.POST, "/v1/users/create"),
                                    antMatcher(HttpMethod.POST, "/v1/login"),
                                    antMatcher(HttpMethod.GET, "/v1/comments"),
                                    antMatcher(HttpMethod.GET, productsBasePatch),
                                    antMatcher(HttpMethod.GET, productsBasePatch.concat("/**"))
                            ).permitAll()
                            .requestMatchers("/v1/comments").hasRole("USER")
                            .requestMatchers(
                                    antMatcher(HttpMethod.POST, productsBasePatch),
                                    antMatcher(HttpMethod.PUT, productsBasePatch.concat("/**")),
                                    antMatcher(HttpMethod.DELETE, productsBasePatch.concat("/**"))
                            ).hasRole("ADMIN")
                            .anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
