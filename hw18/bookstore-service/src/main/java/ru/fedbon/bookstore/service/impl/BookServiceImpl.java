package ru.fedbon.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.bookstore.dto.BookCreateDto;
import ru.fedbon.bookstore.dto.BookDto;
import ru.fedbon.bookstore.dto.BookUpdateDto;
import ru.fedbon.bookstore.exception.NotFoundException;
import ru.fedbon.bookstore.mapper.BookMapper;
import ru.fedbon.bookstore.repository.AuthorRepository;
import ru.fedbon.bookstore.repository.BookRepository;
import ru.fedbon.bookstore.repository.GenreRepository;
import ru.fedbon.bookstore.service.BookService;
import ru.fedbon.bookstore.util.ErrorMessage;

import java.util.List;

import static java.lang.String.format;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;


    @Transactional
    @Override
    public BookDto create(BookCreateDto bookCreateDto) {
        var genre = genreRepository.findById(bookCreateDto.getGenreId())
                .orElseThrow(() -> new NotFoundException(
                        format(ErrorMessage.GENRE_NOT_FOUND, bookCreateDto.getGenreId())
                ));

        var author = authorRepository.findById(bookCreateDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(
                        format(ErrorMessage.AUTHOR_NOT_FOUND, bookCreateDto.getAuthorId())
                ));

        var book = bookRepository.save(BookMapper.mapDtoToNewBook(bookCreateDto, genre, author));

        return BookMapper.mapBookToDto(book);
    }

    @Transactional
    @Override
    public BookDto update(BookUpdateDto bookUpdateDto) {
        var genre = genreRepository.findById(bookUpdateDto.getGenreId()).orElseThrow(() -> new NotFoundException(
                format(ErrorMessage.GENRE_NOT_FOUND, bookUpdateDto.getGenreId())
        ));

        var author = authorRepository.findById(bookUpdateDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(
                        format(ErrorMessage.AUTHOR_NOT_FOUND, bookUpdateDto.getAuthorId())
                ));

        var book = bookRepository.findById(bookUpdateDto.getId())
                .orElseThrow(() -> new NotFoundException(
                        format(ErrorMessage.BOOK_NOT_FOUND, bookUpdateDto.getId())
                ));

        book.setTitle(bookUpdateDto.getTitle());
        book.setGenre(genre);
        book.setAuthor(author);

        return BookMapper.mapBookToDto(book);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAll(Sort sort) {
        return bookRepository.findAll(sort)
                .stream()
                .map(BookMapper::mapBookToDto)
                .toList();
    }

    @Override
    public BookDto getById(long id) {
        return BookMapper.mapBookToDto(bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format(ErrorMessage.BOOK_NOT_FOUND, id))));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAllByGenreId(long genreId) {
        var genre = genreRepository.findById(genreId).orElseThrow(() -> new NotFoundException(
                        format(ErrorMessage.GENRE_NOT_FOUND, genreId)));
        return bookRepository.findAllByGenreId(genre.getId())
                .stream()
                .map(BookMapper::mapBookToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> getAllByAuthorId(long authorId) {
        var author = authorRepository.findById(authorId).orElseThrow(() ->
                        new NotFoundException(format(ErrorMessage.AUTHOR_NOT_FOUND, authorId)));
        return bookRepository.findAllByAuthorId(author.getId())
                .stream()
                .map(BookMapper::mapBookToDto)
                .toList();
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        bookRepository.deleteAll();
    }
}
