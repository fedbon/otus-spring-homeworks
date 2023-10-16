package ru.fedbon.service;

import ru.fedbon.model.Book;

import java.util.List;

public interface BookService {
    long getBooksCount();

    Book addBook(String title, long genreId, long authorId);

    Book addBook(String title, String genreName, String authorName);

    Book changeBook(long id, String title, long genreId, long authorId);

    Book changeBook(long id, String title, String genreName, String authorName);

    Book getBookById(long id);

    List<Book> getAllBooks();

    List<Book> getAllBooksByGenre(String genreName);

    List<Book> getAllBooksByAuthor(String authorName);

    Book deleteBookById(long id);

    long deleteAllBooks();

}
