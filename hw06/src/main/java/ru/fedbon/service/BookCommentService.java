package ru.fedbon.service;

import ru.fedbon.dto.BookCommentDto;
import ru.fedbon.model.BookComment;

import java.util.List;


public interface BookCommentService {

    BookComment add(BookCommentDto bookCommentDto);

    BookComment change(BookCommentDto bookCommentDto);

    BookComment getById(long id);

    List<BookComment> getAllByBookId(long id);

    void deleteById(long id);
}
