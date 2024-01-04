package ru.fedbon.service;

import org.springframework.data.domain.Sort;
import ru.fedbon.dto.BookCreateDto;
import ru.fedbon.dto.BookDto;
import ru.fedbon.dto.BookUpdateDto;

import java.util.List;

public interface BookService {

    BookDto create(BookCreateDto bookCreateDto);

    BookDto update(BookUpdateDto bookUpdateDto);

    List<BookDto> getAll(Sort sort);

    BookDto getById(long id);

    List<BookDto> getAllByGenreId(long genreId);

    List<BookDto> getAllByAuthorId(long authorId);

    void deleteById(long id);

    void deleteAll();

}
