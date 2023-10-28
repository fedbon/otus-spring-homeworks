package ru.fedbon.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.fedbon.repository.GenreRepositoryCustom;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryCustomImpl implements GenreRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public long deleteAllCustom() {
        return entityManager.createQuery(
                "delete from Genre g").executeUpdate();
    }
}
