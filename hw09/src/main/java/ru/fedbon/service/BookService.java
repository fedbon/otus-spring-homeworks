package ru.fedbon.service;

import org.springframework.data.domain.Sort;
import ru.fedbon.dto.NewBookDto;
import ru.fedbon.dto.UpdateBookDto;
import ru.fedbon.model.Book;

import java.util.List;

public interface BookService {

    Book create(NewBookDto newBookDto);

    Book update(UpdateBookDto updateBookDto);

    List<Book> getAll(Sort sort);

    Book getById(long id);

    List<Book> getAllByGenreId(long genreId);

    List<Book> getAllByAuthorId(long authorId);

    void deleteById(long id);

    void deleteAll();

}
