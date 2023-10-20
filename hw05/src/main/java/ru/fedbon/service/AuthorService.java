package ru.fedbon.service;

import ru.fedbon.model.Author;

import java.util.List;

public interface AuthorService {
    long getAuthorsCount();

    Author addAuthor(String authorName);

    void changeAuthor(long id, String authorName);

    List<Author> getAllAuthors();

    void deleteAuthorById(long id);

    long deleteAllAuthors();
}