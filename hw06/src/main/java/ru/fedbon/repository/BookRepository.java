package ru.fedbon.repository;

import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Genre;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    long count();

    Book save(Book book);

    void update(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    List<Book> findAllByGenre(Genre genre);

    List<Book> findAllByAuthor(Author author);

    void deleteById(long id);

    long deleteAll();
}
