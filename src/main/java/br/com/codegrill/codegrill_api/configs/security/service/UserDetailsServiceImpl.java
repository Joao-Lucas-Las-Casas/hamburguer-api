package br.com.codegrill.codegrill_api.configs.security.service;

import br.com.codegrill.codegrill_api.exceptions.UnauthorizedException;
import br.com.codegrill.codegrill_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        final var user = userRepository.findByUsername(login)
                .orElseThrow(() -> new UnauthorizedException("Usuário não autorizado"));

        return new User(user.getUsername(), user.getPassword(),
                user.isEnabled(), user.isAccountNonExpired(),
                user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
    }
}
