package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;


    @Override
    public long getBooksCount() {
        return bookRepository.count();
    }

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
        bookRepository.insert(book);

        return bookDto;
    }

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

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAllBooksByGenre(String genreName) {
        return bookRepository.findAllByGenre(genreName);
    }

    @Override
    public List<Book> getAllBooksByAuthor(String authorName) {
        return bookRepository.findAllByAuthor(authorName);
    }

    @Override
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public long deleteAllBooks() {
        return bookRepository.deleteAll();
    }
}
