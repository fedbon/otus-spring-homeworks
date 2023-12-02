package ru.fedbon.service;

import ru.fedbon.dto.CommentDto;
import ru.fedbon.model.Comment;

import java.util.List;


public interface CommentService {

    Comment create(CommentDto commentDto);

    Comment update(CommentDto commentDto);

    Comment getById(String id);

    List<Comment> getAllByBookId(String bookId);

    void deleteById(String id);
}
