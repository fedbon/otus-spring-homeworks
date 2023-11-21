package ru.fedbon.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Genre;


import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "genre-author-entity-graph")
    Optional<Book> findById(long id);

    @EntityGraph(value = "genre-author-entity-graph")
    List<Book> findAll();

    @EntityGraph(attributePaths = {"author"})
    List<Book> findAllByGenre(Genre genre);

    @EntityGraph(attributePaths = {"genre"})
    List<Book> findAllByAuthor(Author author);

    @Modifying
    @Query("delete from Book")
    void deleteAll();
}
