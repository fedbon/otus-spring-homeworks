package ru.fedbon.service;

import ru.fedbon.model.Comment;

import java.util.List;


public interface CommentService {

    List<Comment> getAllByBookId(long bookId);

}
