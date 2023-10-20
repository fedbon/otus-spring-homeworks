package ru.fedbon.service;

import ru.fedbon.dto.BookDto;
import ru.fedbon.model.Book;

import java.util.List;

public interface BookService {
    long getBooksCount();

    BookDto addBook(BookDto bookDto);

    void changeBook(BookDto bookDto);

    List<Book> getAllBooks();

    List<Book> getAllBooksByGenre(String genreName);

    List<Book> getAllBooksByAuthor(String authorName);

    void deleteBookById(long id);

    long deleteAllBooks();

}
