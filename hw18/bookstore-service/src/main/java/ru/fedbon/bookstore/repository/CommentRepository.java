package ru.fedbon.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fedbon.bookstore.model.Book;
import ru.fedbon.bookstore.model.Comment;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBook(Book book);
}
