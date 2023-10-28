package ru.fedbon.service;

import ru.fedbon.model.Author;

import java.util.List;

public interface AuthorService {
    long getCount();

    Author add(String name);

    Author change(Author authorDto);

    Author getById(long id);

    List<Author> getAll();

    void deleteById(long id);

    long deleteAll();
}
