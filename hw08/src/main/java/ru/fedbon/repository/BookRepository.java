package ru.fedbon.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.fedbon.model.Book;


import java.util.List;


public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findAllByGenreId(String id);

    List<Book> findAllByAuthorId(String id);
}
