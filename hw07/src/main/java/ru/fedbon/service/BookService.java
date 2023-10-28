package ru.fedbon.service;

import ru.fedbon.dto.BookDto;
import ru.fedbon.model.Book;

import java.util.List;

public interface BookService {
    long getCount();

    Book create(BookDto bookDto);

    Book update(BookDto bookDto);

    List<Book> getAll();

    Book getById(long id);

    List<Book> getAllByGenreId(long id);

    List<Book> getAllByAuthorId(long id);

    void deleteById(long id);

    long deleteAll();

}
