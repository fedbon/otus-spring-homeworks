package ru.fedbon.service;

import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.fedbon.dto.BookCreateDto;
import ru.fedbon.dto.BookDto;
import ru.fedbon.dto.BookUpdateDto;


public interface BookService {

    Mono<BookDto> create(BookCreateDto bookCreateDto);

    Mono<BookDto> update(BookUpdateDto bookUpdateDto);

    Flux<BookDto> getAll(Sort sort);

    Mono<BookDto> getById(String id);

    Flux<BookDto> getAllByGenreId(String genreId);

    Flux<BookDto> getAllByAuthorId(String authorId);

    Mono<Void> deleteById(String id);

    Mono<Void> deleteAll();

}
