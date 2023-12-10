package ru.fedbon.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.fedbon.model.Genre;


public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

}
