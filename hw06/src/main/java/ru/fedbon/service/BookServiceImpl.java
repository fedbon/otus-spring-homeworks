package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.dto.BookDto;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.mapper.BookMapper;
import ru.fedbon.model.Book;
import ru.fedbon.repository.AuthorRepository;
import ru.fedbon.repository.BookRepository;
import ru.fedbon.repository.GenreRepository;

import java.util.List;

import static java.lang.String.format;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @Override
    public long getCount() {
        return bookRepository.count();
    }

    @Transactional
    @Override
    public void add(BookDto bookDto) {
        var genre = genreRepository.findById(bookDto.getGenreId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Не найден жанр с идентификатором %d", bookDto.getGenreId())
                ));

        var author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Не найден автор с идентификатором %d", bookDto.getAuthorId())
                ));

        var book = BookMapper.mapDtoToBook(bookDto, genre, author);
        bookRepository.save(book);
    }

    @Transactional
    public Book change(BookDto bookDto) {
        var genre = genreRepository.findById(bookDto.getGenreId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Не найден жанр с идентификатором %d", bookDto.getGenreId())
                ));

        var author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Не найден автор с идентификатором %d", bookDto.getAuthorId())
                ));

        bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Не найдена книга с идентификатором %d", bookDto.getId())
                ));

        var book = BookMapper.mapDtoToBook(bookDto, genre, author);
        bookRepository.update(book);
        return book;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Не найдена книга с идентификатором %d", id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllByGenreId(long id) {
        var genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Не найден жанр с идентификатором %d", id)
                ));
        return bookRepository.findAllByGenre(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllByAuthorId(long id) {
        var author = authorRepository.findById(id).orElseThrow(() ->
                        new NotFoundException(String.format("Не найден автор с идентификатором %d", id)
        ));
        return bookRepository.findAllByAuthor(author);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public long deleteAll() {
        return bookRepository.deleteAll();
    }
}
