package ru.fedbon.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.fedbon.model.Book;
import ru.fedbon.model.BookComment;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookCommentRepositoryJpa implements BookCommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void save(BookComment bookComment) {
        entityManager.persist(bookComment);
    }

    @Override
    public BookComment update(BookComment bookComment) {
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
