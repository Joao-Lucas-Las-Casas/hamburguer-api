package br.com.codegrill.codegrill_api.services;

import br.com.codegrill.codegrill_api.dtos.UserDto;
import br.com.codegrill.codegrill_api.entities.RolesEntity;
import br.com.codegrill.codegrill_api.entities.UserEntity;
import br.com.codegrill.codegrill_api.enums.RoleNameEnum;
import br.com.codegrill.codegrill_api.repositories.RolesRepository;
import br.com.codegrill.codegrill_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import static br.com.codegrill.codegrill_api.services.mapers.UsersMapper.mapToUserEntity;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    @Transactional
    public UserEntity createUser(final UserDto userDto) {
        final var roles = new HashSet<RolesEntity>();
        final var rolesEntity = rolesRepository.findByRoleName(RoleNameEnum.ROLE_USER)
                .orElse(null);
        roles.add(rolesEntity);
        return userRepository.save(mapToUserEntity(userDto, roles));
    }
}
