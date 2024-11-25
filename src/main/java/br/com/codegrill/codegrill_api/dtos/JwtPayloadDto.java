package br.com.codegrill.codegrill_api.dtos;

import br.com.codegrill.codegrill_api.enums.RoleNameEnum;

import java.util.List;

public record JwtPayloadDto(String sub, String iss, List<RoleNameEnum> roles, String exp) {
}
