package ru.fedbon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.fedbon.dto.CommentDto;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.mapper.CommentMapper;
import ru.fedbon.repository.BookRepository;
import ru.fedbon.repository.CommentRepository;
import ru.fedbon.util.ErrorMessage;

import static java.lang.String.format;


@RestController
@RequiredArgsConstructor
public class CommentController {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    @GetMapping(value = "/api/books/{id}/comments")
    public Flux<CommentDto> handleGetAllByBookId(@PathVariable(value = "id") String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(() -> new NotFoundException(format(ErrorMessage.BOOK_NOT_FOUND, id))))
                .flatMapMany(book -> commentRepository.findAllByBook(book)
                        .map(CommentMapper::mapCommentToDto));
    }
}
