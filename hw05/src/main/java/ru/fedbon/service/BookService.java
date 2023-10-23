package ru.fedbon.service;

import ru.fedbon.dto.BookDto;
import ru.fedbon.model.Book;

import java.util.List;

public interface BookService {
    long getCount();

    BookDto add(BookDto bookDto);

    void change(BookDto bookDto);

    List<Book> getAll();

    List<Book> getAllByGenreName(String genreName);

    List<Book> getAllByAuthorName(String authorName);

    void deleteById(long id);

    long deleteAll();

}
