package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.fedbon.dto.CommentDto;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.mapper.CommentMapper;
import ru.fedbon.repository.BookRepository;
import ru.fedbon.repository.CommentRepository;
import ru.fedbon.service.CommentService;
import ru.fedbon.util.ErrorMessage;


import static java.lang.String.format;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;


    @Override
    public Flux<CommentDto> getAllByBookId(String bookId, Sort sort) {
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(() -> new NotFoundException(format(ErrorMessage.BOOK_NOT_FOUND, bookId))))
                .flatMapMany(book -> commentRepository.findAllByBook(book)
                        .map(CommentMapper::mapCommentToDto));
    }
}
