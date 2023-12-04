package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.dto.NewBookDto;
import ru.fedbon.dto.UpdateBookDto;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.mapper.BookMapper;
import ru.fedbon.model.Book;
import ru.fedbon.repository.AuthorRepository;
import ru.fedbon.repository.BookRepository;
import ru.fedbon.repository.GenreRepository;
import ru.fedbon.service.BookService;
import ru.fedbon.util.ErrorMessage;

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
    public Book create(NewBookDto newBookDto) {
        var genre = genreRepository.findById(newBookDto.getGenreId())
                .orElseThrow(() -> new NotFoundException(
                        String.format(ErrorMessage.GENRE_NOT_FOUND, newBookDto.getGenreId())
                ));

        var author = authorRepository.findById(newBookDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(
                        String.format(ErrorMessage.AUTHOR_NOT_FOUND, newBookDto.getAuthorId())
                ));

        return bookRepository.save(BookMapper.mapDtoToNewBook(newBookDto, genre, author));
    }

    @Transactional
    @Override
    public Book update(UpdateBookDto updateBookDto) {
        var genre = genreRepository.findById(updateBookDto.getGenreId()).orElseThrow(() -> new NotFoundException(
                String.format(ErrorMessage.GENRE_NOT_FOUND, updateBookDto.getGenreId())
        ));

        var author = authorRepository.findById(updateBookDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(
                        String.format(ErrorMessage.AUTHOR_NOT_FOUND, updateBookDto.getAuthorId())
                ));

        var book = bookRepository.findById(updateBookDto.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format(ErrorMessage.BOOK_NOT_FOUND, updateBookDto.getId())
                ));

        book.setTitle(updateBookDto.getTitle());
        book.setGenre(genre);
        book.setAuthor(author);

        return book;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll(Sort sort) {
        return bookRepository.findAll(sort);
    }

    @Override
    public Book getById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format(ErrorMessage.BOOK_NOT_FOUND, id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllByGenreId(long genreId) {
        var genre = genreRepository.findById(genreId).orElseThrow(() -> new NotFoundException(
                        String.format(ErrorMessage.GENRE_NOT_FOUND, genreId)));
        return bookRepository.findAllByGenreId(genre.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllByAuthorId(long authorId) {
        var author = authorRepository.findById(authorId).orElseThrow(() ->
                        new NotFoundException(String.format(ErrorMessage.AUTHOR_NOT_FOUND, authorId)));
        return bookRepository.findAllByAuthorId(author.getId());
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public void deleteAll() {
        bookRepository.deleteAll();
    }
}
