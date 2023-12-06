package ru.fedbon.service;

import ru.fedbon.dto.CommentCreateDto;
import ru.fedbon.dto.CommentDto;
import ru.fedbon.model.Comment;

import java.util.List;


public interface CommentService {

    CommentDto create(CommentCreateDto commentCreateDto);

    Comment update(CommentDto commentDto);

    Comment getById(long id);

    List<Comment> getAllByBookId(long bookId);

    void deleteById(long id);
}
