package ru.fedbon.repository;

import ru.fedbon.model.Book;
import ru.fedbon.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findAllByBook(Book book);

    void deleteById(long id);
}
