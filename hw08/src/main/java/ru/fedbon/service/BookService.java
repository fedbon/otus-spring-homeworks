package ru.fedbon.service;

import org.springframework.data.domain.Sort;
import ru.fedbon.dto.BookDto;
import ru.fedbon.model.Book;

import java.util.List;

public interface BookService {
    long getCount();

    Book create(BookDto bookDto);

    Book update(BookDto bookDto);

    List<Book> getAll(Sort sort);

    Book getById(String id);

    List<Book> getAllByGenreId(String genreId);

    List<Book> getAllByAuthorId(String authorId);

    void deleteById(String id);

    void deleteAll();

}
