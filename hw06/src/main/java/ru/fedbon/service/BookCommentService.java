package ru.fedbon.service;

import ru.fedbon.model.BookComment;

import java.util.List;


public interface BookCommentService {

    void add(long id, String text);

    BookComment change(BookComment bookComment);

    BookComment getById(long id);

    List<BookComment> getAllByBookId(long id);

    void deleteById(long id);
}
