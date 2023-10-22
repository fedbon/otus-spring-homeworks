package ru.fedbon.service;

import ru.fedbon.model.BookComment;

import java.util.List;


public interface BookCommentService {

    void addBookComment(long id, String text);

    BookComment changeBookComment(long id, String text);

    BookComment getBookCommentById(long id);

    List<BookComment> getAllCommentsByBookId(long id);

    void deleteBookCommentById(long id);
}
