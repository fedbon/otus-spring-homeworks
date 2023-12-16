package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.model.Comment;
import ru.fedbon.repository.BookRepository;
import ru.fedbon.repository.CommentRepository;
import ru.fedbon.service.CommentService;
import ru.fedbon.util.ErrorMessage;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAllByBookId(long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new NotFoundException(format(ErrorMessage.BOOK_NOT_FOUND, bookId)));
        return commentRepository.findAllByBook(book);
    }

}
