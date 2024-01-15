package ru.fedbon.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.fedbon.bookstore.model.Genre;


public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Modifying
    @Query("delete from Genre")
    void deleteAll();
}
