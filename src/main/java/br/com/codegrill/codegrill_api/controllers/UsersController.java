package br.com.codegrill.codegrill_api.controllers;

import br.com.codegrill.codegrill_api.dtos.UserDto;
import br.com.codegrill.codegrill_api.entities.UserEntity;
import br.com.codegrill.codegrill_api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserEntity> createUser(@RequestBody @Valid final UserDto userDto) {
        return ResponseEntity.status(CREATED).body(userService.createUser(userDto));
    }
}
