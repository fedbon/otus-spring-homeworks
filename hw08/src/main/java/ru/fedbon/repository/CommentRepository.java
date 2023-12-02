package ru.fedbon.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.fedbon.model.Comment;


import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findAllByBookId(String id);
}
