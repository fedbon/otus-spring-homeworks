package ru.fedbon.service;

import ru.fedbon.model.Author;

import java.util.List;

public interface AuthorService {
    long getCount();

    void add(String name);

    Author change(Author author);

    Author getById(long id);

    List<Author> getAll();

    void deleteById(long id);

    long deleteAll();
}
