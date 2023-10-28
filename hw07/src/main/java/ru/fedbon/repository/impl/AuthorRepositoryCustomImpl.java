package ru.fedbon.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.fedbon.repository.AuthorRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public long deleteAllCustom() {
        return entityManager.createQuery(
                "delete from Author a").executeUpdate();
    }
}
