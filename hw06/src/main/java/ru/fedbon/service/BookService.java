package ru.fedbon.service;

import ru.fedbon.dto.BookDto;
import ru.fedbon.model.Book;

import java.util.List;

public interface BookService {
    long getCount();

    Book add(BookDto bookDto);

    Book change(BookDto bookDto);

    List<Book> getAll();

    Book getById(long id);

    List<Book> getAllByGenreId(long id);

    List<Book> getAllByAuthorId(long id);

    void deleteById(long id);

    long deleteAll();

}
