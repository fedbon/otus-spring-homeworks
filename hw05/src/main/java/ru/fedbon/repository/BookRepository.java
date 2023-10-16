package ru.fedbon.repository;

import ru.fedbon.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    long count();

    Book insert(Book book);

    Book update(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    List<Book> findAllByGenre(String genreName);

    List<Book> findAllByAuthor(String authorName);

    void deleteById(long id);

    long deleteAll();
}
