package ru.fedbon.service;

import ru.fedbon.dto.CommentDto;

import java.util.List;


public interface CommentService {

    List<CommentDto> getAllByBookId(long bookId);
}
