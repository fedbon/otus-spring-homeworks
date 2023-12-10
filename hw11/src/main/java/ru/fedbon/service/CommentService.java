package ru.fedbon.service;

import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import ru.fedbon.dto.CommentDto;



public interface CommentService {

    Flux<CommentDto> getAllByBookId(String bookId, Sort sort);
}
