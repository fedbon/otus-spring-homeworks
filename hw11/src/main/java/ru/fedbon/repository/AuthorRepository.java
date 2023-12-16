package ru.fedbon.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.fedbon.model.Author;


public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

}
