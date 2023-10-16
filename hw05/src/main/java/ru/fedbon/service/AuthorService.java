package ru.fedbon.service;

import ru.fedbon.model.Author;

import java.util.List;

public interface AuthorService {
    long getAuthorsCount();

    Author addAuthor(String authorName);

    Author changeAuthor(long id, String authorName);

    Author getAuthorById(long id);

    Author getAuthorByName(String authorName);

    List<Author> getAllAuthors();

    Author deleteAuthorById(long id);

    long deleteAllAuthors();
}
