package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.fedbon.dto.BookCreateDto;
import ru.fedbon.dto.BookDto;
import ru.fedbon.dto.BookUpdateDto;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.mapper.BookMapper;
import ru.fedbon.repository.AuthorRepository;
import ru.fedbon.repository.BookRepository;
import ru.fedbon.repository.GenreRepository;
import ru.fedbon.service.BookService;
import ru.fedbon.util.ErrorMessage;



import static java.lang.String.format;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;


    @Override
    public Mono<BookDto> create(BookCreateDto bookCreateDto) {
        return genreRepository.findById(bookCreateDto.getGenreId())
                .switchIfEmpty(Mono.error(new NotFoundException(
                        String.format(ErrorMessage.GENRE_NOT_FOUND, bookCreateDto.getGenreId())
                )))
                .zipWith(authorRepository.findById(bookCreateDto.getAuthorId())
                        .switchIfEmpty(Mono.error(new NotFoundException(
                                String.format(ErrorMessage.AUTHOR_NOT_FOUND, bookCreateDto.getAuthorId())
                        ))))
                .flatMap(tuple -> {
                    var genre = tuple.getT1();
                    var author = tuple.getT2();
                    var newBook = BookMapper.mapDtoToNewBook(bookCreateDto, genre, author);
                    return bookRepository.save(newBook)
                            .map(BookMapper::mapBookToDto);
                });
    }

    @Override
    public Mono<BookDto> update(BookUpdateDto bookUpdateDto) {
        return genreRepository.findById(bookUpdateDto.getGenreId())
                .switchIfEmpty(Mono.error(new NotFoundException(
                        String.format(ErrorMessage.GENRE_NOT_FOUND, bookUpdateDto.getGenreId())
                )))
                .zipWith(authorRepository.findById(bookUpdateDto.getAuthorId())
                        .switchIfEmpty(Mono.error(new NotFoundException(
                                String.format(ErrorMessage.AUTHOR_NOT_FOUND, bookUpdateDto.getAuthorId())
                        ))))
                .flatMap(tuple -> bookRepository.findById(bookUpdateDto.getId())
                        .switchIfEmpty(Mono.error(new NotFoundException(
                                String.format(ErrorMessage.BOOK_NOT_FOUND, bookUpdateDto.getId())
                        )))
                        .flatMap(book -> {
                            var genre = tuple.getT1();
                            var author = tuple.getT2();
                            book.setTitle(bookUpdateDto.getTitle());
                            book.setGenre(genre);
                            book.setAuthor(author);
                            return bookRepository.save(book)
                                    .map(BookMapper::mapBookToDto);
                        }));
    }

    @Override
    public Flux<BookDto> getAll(Sort sort) {
        return bookRepository.findAll(sort)
                .map(BookMapper::mapBookToDto);
    }

    @Override
    public Mono<BookDto> getById(String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(format(ErrorMessage.BOOK_NOT_FOUND, id))))
                .map(BookMapper::mapBookToDto);
    }

    @Override
    public Flux<BookDto> getAllByGenreId(String genreId) {
        return genreRepository.findById(genreId)
                .switchIfEmpty(Mono.error(new NotFoundException(
                        String.format(ErrorMessage.GENRE_NOT_FOUND, genreId))))
                .flatMapMany(genre -> bookRepository.findAllByGenreId(genre.getId())
                        .map(BookMapper::mapBookToDto));
    }

    @Override
    public Flux<BookDto> getAllByAuthorId(String authorId) {
        return authorRepository.findById(authorId)
                .switchIfEmpty(Mono.error(new NotFoundException(
                        String.format(ErrorMessage.AUTHOR_NOT_FOUND, authorId))))
                .flatMapMany(author -> bookRepository.findAllByAuthorId(author.getId())
                        .map(BookMapper::mapBookToDto));
    }


    @Override
    public Mono<Void> deleteById(String id) {
        return bookRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteAll() {
        return bookRepository.deleteAll();
    }
}
