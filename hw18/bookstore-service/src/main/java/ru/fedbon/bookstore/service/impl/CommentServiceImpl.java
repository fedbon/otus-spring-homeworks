package ru.fedbon.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.bookstore.dto.CommentDto;
import ru.fedbon.bookstore.exception.NotFoundException;
import ru.fedbon.bookstore.mapper.CommentMapper;
import ru.fedbon.bookstore.repository.BookRepository;
import ru.fedbon.bookstore.repository.CommentRepository;
import ru.fedbon.bookstore.service.CommentService;
import ru.fedbon.bookstore.util.ErrorMessage;

import java.util.List;

import static java.lang.String.format;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;


    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getAllByBookId(long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(format(ErrorMessage.BOOK_NOT_FOUND, bookId)));
        return commentRepository.findAllByBook(book)
                .stream()
                .map(CommentMapper::mapCommentToDto)
                .toList();
    }
}
