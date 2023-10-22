package ru.fedbon.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.fedbon.model.Genre;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public long count() {
        return entityManager.createQuery("select count(g) from Genre g", Long.class).getSingleResult();
    }

    @Override
    public void save(Genre genre) {
        entityManager.persist(genre);
    }

    @Override
    public Genre update(Genre genre) {
        return entityManager.merge(genre);
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }

    @Override
    public List<Genre> findAll() {
        return entityManager.createQuery("select g from Genre g order by g.id", Genre.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        entityManager.remove(entityManager.find(Genre.class, id));
    }

    @Override
    public long deleteAll() {
        return entityManager.createQuery("delete from Genre g").executeUpdate();
    }
}
