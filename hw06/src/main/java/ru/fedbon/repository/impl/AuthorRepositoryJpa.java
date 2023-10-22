package ru.fedbon.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.fedbon.model.Author;
import ru.fedbon.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public long count() {
        return entityManager.createQuery("select count(a) from Author a", Long.class).getSingleResult();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null || author.getId() == 0) {
            entityManager.persist(author);
            return author;
        }
        return entityManager.merge(author);
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        return entityManager.createQuery("select a from Author a order by a.id", Author.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        entityManager.remove(entityManager.find(Author.class, id));
    }

    @Override
    public long deleteAll() {
        return entityManager.createQuery("delete from Author a").executeUpdate();
    }

}
