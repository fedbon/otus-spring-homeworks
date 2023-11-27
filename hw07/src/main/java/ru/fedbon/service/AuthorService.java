package ru.fedbon.service;

import org.springframework.data.domain.Sort;
import ru.fedbon.model.Author;

import java.util.List;

public interface AuthorService {
    long getCount();

    Author create(String name);

    Author update(Author authorDto);

    Author getById(long id);

    List<Author> getAll(Sort sort);

    void deleteById(long id);

    void deleteAll();
}
