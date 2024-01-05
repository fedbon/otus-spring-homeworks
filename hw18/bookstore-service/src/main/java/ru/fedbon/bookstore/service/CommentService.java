package ru.fedbon.bookstore.service;



import ru.fedbon.bookstore.dto.CommentDto;

import java.util.List;


public interface CommentService {

    List<CommentDto> getAllByBookId(long bookId);
}
