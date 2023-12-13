package ru.fedbon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.fedbon.dto.CommentDto;
import ru.fedbon.service.CommentService;


@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping(value = "/api/books/{id}/comments")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CommentDto> handleGetAllByBookId(@PathVariable(value = "id") String id) {
        return commentService.getAllByBookId(id, Sort.by(Sort.Direction.ASC, "id"));
    }
}
