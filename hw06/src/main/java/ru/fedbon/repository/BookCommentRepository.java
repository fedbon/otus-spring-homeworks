package ru.fedbon.repository;

import ru.fedbon.model.Book;
import ru.fedbon.model.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentRepository {

    BookComment save(BookComment bookComment);

    void update(BookComment bookComment);

    Optional<BookComment> findById(long id);

    List<BookComment> findAllCommentsForBook(Book book);

    void deleteById(long id);
}
