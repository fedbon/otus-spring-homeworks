package ru.fedbon.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.fedbon.model.Book;
import ru.fedbon.model.BookComment;
import ru.fedbon.repository.BookCommentRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookCommentRepositoryJpa implements BookCommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public BookComment save(BookComment bookComment) {
        if (bookComment.getId() == null || bookComment.getId() == 0) {
            entityManager.persist(bookComment);
            return bookComment;
        }
        return entityManager.merge(bookComment);
    }

    @Override
    public Optional<BookComment> findById(long id) {
        return Optional.ofNullable(entityManager.find(BookComment.class, id));
    }

    @Override
    public List<BookComment> findAllCommentsForBook(Book book) {
        var query = entityManager.createQuery(
                "select c from BookComment c where c.book = :book", BookComment.class);
        query.setParameter("book", book);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        entityManager.remove(entityManager.find(BookComment.class, id));
    }
}