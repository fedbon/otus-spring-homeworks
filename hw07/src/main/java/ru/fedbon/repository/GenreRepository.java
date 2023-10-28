package ru.fedbon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fedbon.model.Genre;


public interface GenreRepository extends JpaRepository<Genre, Long>, GenreRepositoryCustom {
}
