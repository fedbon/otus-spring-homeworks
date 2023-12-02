package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.fedbon.dto.BookDto;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.mapper.BookMapper;
import ru.fedbon.model.Book;
import ru.fedbon.repository.AuthorRepository;
import ru.fedbon.repository.BookRepository;
import ru.fedbon.repository.GenreRepository;
import ru.fedbon.service.BookService;
import ru.fedbon.utils.ErrorMessage;

import java.util.List;

import static java.lang.String.format;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    @Override
    public long getCount() {
        return bookRepository.count();
    }

    @Override
    public Book create(BookDto bookDto) {
        var genre = genreRepository.findById(bookDto.getGenreId())
                .orElseThrow(() -> new NotFoundException(
                        String.format(ErrorMessage.GENRE_NOT_FOUND, bookDto.getGenreId())
                ));

        var author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(
                        String.format(ErrorMessage.AUTHOR_NOT_FOUND, bookDto.getAuthorId())
                ));

        var book = BookMapper.mapDtoToBook(bookDto, genre, author);
        return bookRepository.save(book);
    }

    @Override
    public Book update(BookDto bookDto) {
        var genre = genreRepository.findById(bookDto.getGenreId()).orElseThrow(() -> new NotFoundException(
                        String.format(ErrorMessage.GENRE_NOT_FOUND, bookDto.getGenreId())
                ));

        var author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(
                        String.format(ErrorMessage.AUTHOR_NOT_FOUND, bookDto.getAuthorId())
                ));

        bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format(ErrorMessage.BOOK_NOT_FOUND, bookDto.getId())
                ));

        return BookMapper.mapDtoToBook(bookDto, genre, author);
    }

    @Override
    public List<Book> getAll(Sort sort) {
        return bookRepository.findAll(sort);
    }

    @Override
    public Book getById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format(ErrorMessage.BOOK_NOT_FOUND, id)));
    }

    @Override
    public List<Book> getAllByGenreId(String genreId) {
        var genre = genreRepository.findById(genreId).orElseThrow(() -> new NotFoundException(
                        String.format(ErrorMessage.GENRE_NOT_FOUND, genreId)));
        return bookRepository.findAllByGenreId(genre.getId());
    }

    @Override
    public List<Book> getAllByAuthorId(String authorId) {
        var author = authorRepository.findById(authorId).orElseThrow(() ->
                        new NotFoundException(String.format(ErrorMessage.AUTHOR_NOT_FOUND, authorId)));
        return bookRepository.findAllByAuthorId(author.getId());
    }

    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        bookRepository.deleteAll();
    }
}
