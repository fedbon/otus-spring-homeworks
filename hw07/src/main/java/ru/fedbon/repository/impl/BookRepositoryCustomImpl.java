package ru.fedbon.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.fedbon.repository.BookRepositoryCustom;

@Repository
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public long deleteAllCustom() {
        return entityManager.createQuery(
                "delete from Book b").executeUpdate();
    }
}
