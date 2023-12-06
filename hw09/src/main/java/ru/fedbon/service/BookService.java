package ru.fedbon.service;

import org.springframework.data.domain.Sort;
import ru.fedbon.dto.BookCreateDto;
import ru.fedbon.dto.BookDto;
import ru.fedbon.dto.BookUpdateDto;
import ru.fedbon.model.Book;

import java.util.List;

public interface BookService {

    BookDto create(BookCreateDto bookCreateDto);

    Book update(BookUpdateDto bookUpdateDto);

    List<Book> getAll(Sort sort);

    Book getById(long id);

    List<Book> getAllByGenreId(long genreId);

    List<Book> getAllByAuthorId(long authorId);

    void deleteById(long id);

    void deleteAll();

}
