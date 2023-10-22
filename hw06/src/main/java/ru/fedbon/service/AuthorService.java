package ru.fedbon.service;

import ru.fedbon.model.Author;

import java.util.List;

public interface AuthorService {
    long getAuthorsCount();

    void addAuthor(String authorName);

    Author changeAuthor(long id, String authorName);

    Author getAuthorById(long id);

    List<Author> getAllAuthors();

    void deleteAuthorById(long id);

    long deleteAllAuthors();
}
