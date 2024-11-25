package br.com.codegrill.codegrill_api.services;

import br.com.codegrill.codegrill_api.dtos.CommentsDto;
import br.com.codegrill.codegrill_api.dtos.JwtPayloadDto;
import br.com.codegrill.codegrill_api.entities.CommentsEntity;
import br.com.codegrill.codegrill_api.exceptions.NotFoundException;
import br.com.codegrill.codegrill_api.exceptions.UnauthorizedException;
import br.com.codegrill.codegrill_api.repositories.CommentsRepository;
import br.com.codegrill.codegrill_api.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

import static br.com.codegrill.codegrill_api.services.mapers.CommentsMapper.mapToCommentsEntity;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;

    public void sendComment(final CommentsDto commentsDto, final String token) {
        final var userEmail = decoderTokenJwt(token);
        final var userEntity = userRepository.findByUsername(userEmail).orElseThrow(() ->
                new NotFoundException("Usuário não encontrado"));
        commentsRepository.save(mapToCommentsEntity(commentsDto, userEntity));
    }

    public List<CommentsEntity> listComments() {
        return commentsRepository.findAll().stream().limit(3).toList();
    }

    private static String decoderTokenJwt(final String jwtToken) {
        try {
            final var token = jwtToken.replace("Bearer ", "");
            final var chunks = token.split("\\.");
            final var payload = new String(Base64.getUrlDecoder().decode(chunks[1]));
            final var identification = new ObjectMapper().readValue(payload, JwtPayloadDto.class);
            return identification.sub();
        } catch (final Exception e) {
            throw new UnauthorizedException("User unauthorized");
        }
    }
}
