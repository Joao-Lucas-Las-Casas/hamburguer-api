package br.com.codegrill.codegrill_api.services.mapers;

import br.com.codegrill.codegrill_api.dtos.UserDto;
import br.com.codegrill.codegrill_api.entities.RolesEntity;
import br.com.codegrill.codegrill_api.entities.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UsersMapper {
    public static UserEntity mapToUserEntity(final UserDto userDto, final Set<RolesEntity> roles) {
        return UserEntity.builder()
                .username(userDto.email())
                .password(new BCryptPasswordEncoder().encode(userDto.password()))
                .name(userDto.name())
                .phone(userDto.phone())
                .taxId(userDto.taxId())
                .roles(roles)
                .zipCode(userDto.zipCode())
                .image(userDto.image())
                .build();
    }
}
