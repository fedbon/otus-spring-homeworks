package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.dto.BookDto;
import ru.fedbon.exception.AuthorNotFoundException;
import ru.fedbon.exception.BookNotFoundException;
import ru.fedbon.exception.GenreNotFoundException;
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
    public long getBooksCount() {
        return bookRepository.count();
    }

    @Transactional
    @Override
    public BookDto addBook(BookDto bookDto) {
        var genre = genreRepository.findById(bookDto.getGenreId())
                .orElseThrow(() -> new GenreNotFoundException(
                        String.format("Не найден жанр с идентификатором %d", bookDto.getGenreId())
                ));

        var author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException(
                        String.format("Не найден автор с идентификатором %d", bookDto.getAuthorId())
                ));

        var book = BookMapper.mapDtoToBook(bookDto, genre, author);
        bookRepository.save(book);

        return bookDto;
    }

    @Transactional
    public void changeBook(BookDto bookDto) {
        var genre = genreRepository.findById(bookDto.getGenreId())
                .orElseThrow(() -> new GenreNotFoundException(
                        String.format("Не найден жанр с идентификатором %d", bookDto.getGenreId())
                ));

        var author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException(
                        String.format("Не найден автор с идентификатором %d", bookDto.getAuthorId())
                ));

        bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new BookNotFoundException(
                        String.format("Не найдена книга с идентификатором %d", bookDto.getId())
                ));

        var book = BookMapper.mapDtoToBook(bookDto, genre, author);
        bookRepository.update(book);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(format("Не найдена книга с идентификатором %d", id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBooksByGenre(long id) {
        var genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException(
                        String.format("Не найден жанр с идентификатором %d", id)
                ));
        return bookRepository.findAllByGenre(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBooksByAuthor(long id) {
        var author = authorRepository.findById(id).orElseThrow(() ->
                        new AuthorNotFoundException(String.format("Не найден автор с идентификатором %d", id)
        ));
        return bookRepository.findAllByAuthor(author);
    }

    @Transactional
    @Override
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public long deleteAllBooks() {
        return bookRepository.deleteAll();
    }
}
