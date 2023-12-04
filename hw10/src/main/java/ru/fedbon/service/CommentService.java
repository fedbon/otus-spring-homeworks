package ru.fedbon.service;

import ru.fedbon.dto.CommentDto;
import ru.fedbon.model.Comment;

import java.util.List;


public interface CommentService {

    Comment create(CommentDto commentDto);

    Comment update(CommentDto commentDto);

    Comment getById(long id);

    List<Comment> getAllByBookId(long bookId);

    void deleteById(long id);
}
