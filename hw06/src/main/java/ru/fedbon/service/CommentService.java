package ru.fedbon.service;

import ru.fedbon.dto.CommentDto;
import ru.fedbon.model.Comment;

import java.util.List;


public interface CommentService {

    Comment add(CommentDto commentDto);

    Comment change(CommentDto commentDto);

    Comment getById(long id);

    List<Comment> getAllByBookId(long id);

    void deleteById(long id);
}
