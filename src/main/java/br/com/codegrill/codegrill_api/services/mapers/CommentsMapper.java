package br.com.codegrill.codegrill_api.services.mapers;

import br.com.codegrill.codegrill_api.dtos.CommentsDto;
import br.com.codegrill.codegrill_api.entities.CommentsEntity;
import br.com.codegrill.codegrill_api.entities.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentsMapper {
    public static CommentsEntity mapToCommentsEntity(final CommentsDto commentsDto, final UserEntity userEntity) {
        return CommentsEntity.builder()
                .comment(commentsDto.comment())
                .sender(userEntity)
                .build();
    }
}
