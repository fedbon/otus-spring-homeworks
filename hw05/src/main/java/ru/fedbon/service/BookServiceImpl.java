package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fedbon.model.Book;
import ru.fedbon.repository.BookRepository;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final GenreService genreService;

    @Override
    public long getBooksCount() {
        return bookRepository.count();
    }

    @Override
    public Book addBook(String title, long genreId, long authorId) {
        var genre = genreService.getGenreById(genreId);
        var author = authorService.getAuthorById(authorId);
        var book = new Book();
        book.setTitle(title);
        book.setGenre(genre);
        book.setAuthor(author);
        return bookRepository.insert(book);
    }

    @Override
    public Book addBook(String title, String genreName, String authorName) {
        var genre = genreService.getGenreByName(genreName);
        var author = authorService.getAuthorByName(authorName);
        var book = new Book();
        book.setTitle(title);
        book.setGenre(genre);
        book.setAuthor(author);
        return bookRepository.insert(book);
    }

    @Override
    public Book changeBook(long id, String title, long genreId, long authorId) {
        var genre = genreService.getGenreById(genreId);
        var author = authorService.getAuthorById(authorId);
        var book = getBookById(id);
        book.setTitle(title);
        book.setGenre(genre);
        book.setAuthor(author);
        return bookRepository.update(book);
    }

    @Override
    public Book changeBook(long id, String title, String genreName, String authorName) {
        var genre = genreService.getGenreByName(genreName);
        var author = authorService.getAuthorByName(authorName);
        var book = getBookById(id);
        book.setTitle(title);
        book.setGenre(genre);
        book.setAuthor(author);
        return bookRepository.update(book);
    }

    @Override
    public Book getBookById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найдена книга с идентификатором %d", id)));
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
    public Book deleteBookById(long id) {
        var book = getBookById(id);
        bookRepository.deleteById(id);
        return book;
    }

    @Override
    public long deleteAllBooks() {
        return bookRepository.deleteAll();
    }
}
