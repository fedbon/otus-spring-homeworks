package ru.fedbon.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.fedbon.model.Author;


public interface AuthorRepository extends MongoRepository<Author, String> {

}
