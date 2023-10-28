package ru.fedbon.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Genre;


import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    @EntityGraph(value = "genre-author-entity-graph")
    Optional<Book> findById(long id);

    @Override
    @EntityGraph(value = "genre-author-entity-graph")
    List<Book> findAll(Sort sort);

    @EntityGraph(attributePaths = {"author"})
    List<Book> findAllByGenre(Genre genre);

    @EntityGraph(attributePaths = {"genre"})
    List<Book> findAllByAuthor(Author author);
}
