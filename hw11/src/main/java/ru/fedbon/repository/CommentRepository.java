package ru.fedbon.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.fedbon.model.Book;
import ru.fedbon.model.Comment;




public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

    Flux<Comment> findAllByBook(Book book);
}
