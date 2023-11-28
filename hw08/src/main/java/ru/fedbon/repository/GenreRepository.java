package ru.fedbon.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.fedbon.model.Genre;


public interface GenreRepository extends MongoRepository<Genre, String> {

}
