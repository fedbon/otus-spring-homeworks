package ru.fedbon.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.fedbon.model.Book;
import ru.fedbon.model.Comment;
import ru.fedbon.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            entityManager.persist(comment);
            return comment;
        }
        return entityManager.merge(comment);
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAllByBook(Book book) {
        var query = entityManager.createQuery(
                "select c from Comment c where c.book = :book", Comment.class);
        query.setParameter("book", book);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        entityManager.remove(entityManager.find(Comment.class, id));
    }
}
