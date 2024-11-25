package br.com.codegrill.codegrill_api.controllers;

import br.com.codegrill.codegrill_api.dtos.CommentsDto;
import br.com.codegrill.codegrill_api.entities.CommentsEntity;
import br.com.codegrill.codegrill_api.services.CommentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;

    @PostMapping
    public ResponseEntity<CommentsEntity> sendComment(@RequestBody @Valid final CommentsDto commentsDto,
                                                      @RequestHeader("Authorization") final String token) {
        commentsService.sendComment(commentsDto, token);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CommentsEntity>> listComment() {
        return ResponseEntity.ok(commentsService.listComments());
    }
}
