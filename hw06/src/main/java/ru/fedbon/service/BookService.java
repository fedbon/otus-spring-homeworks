package ru.fedbon.service;

import ru.fedbon.dto.BookDto;
import ru.fedbon.model.Book;

import java.util.List;

public interface BookService {
    long getBooksCount();

    void addBook(BookDto bookDto);

    Book changeBook(BookDto bookDto);

    List<Book> getAllBooks();

    Book getBookById(long id);

    List<Book> getAllBooksByGenre(long id);

    List<Book> getAllBooksByAuthor(long id);

    void deleteBookById(long id);

    long deleteAllBooks();

}
