package ru.fedbon.service;

import ru.fedbon.model.Author;

import java.util.List;

public interface AuthorService {
    long getCount();

    Author add(String authorName);

    void change(Author author);

    List<Author> getAll();

    void deleteById(long id);

    long deleteAll();
}
