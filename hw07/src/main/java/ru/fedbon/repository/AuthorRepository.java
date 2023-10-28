package ru.fedbon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fedbon.model.Author;


public interface AuthorRepository extends JpaRepository<Author, Long>, AuthorRepositoryCustom {
}
