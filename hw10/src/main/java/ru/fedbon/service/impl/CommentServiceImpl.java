package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.dto.CommentDto;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.mapper.CommentMapper;
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

    @Transactional
    @Override
    public Comment create(CommentDto commentDto) {
        var book = bookRepository.findById(commentDto.getBookId())
                .orElseThrow(() ->
                        new NotFoundException(format(ErrorMessage.BOOK_NOT_FOUND,
                                commentDto.getBookId())));
        var bookComment = CommentMapper.mapDtoToComment(commentDto, book);
        return commentRepository.save(bookComment);
    }

    @Transactional
    @Override
    public Comment update(CommentDto commentDto) {
        var comment = commentRepository.findById(commentDto.getId())
                .orElseThrow(() ->
                        new NotFoundException(format(ErrorMessage.COMMENT_NOT_FOUND,
                                commentDto.getId())));

        comment.setText(commentDto.getText());

        return comment;
    }

    @Transactional(readOnly = true)
    @Override
    public Comment getById(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(format(ErrorMessage.COMMENT_NOT_FOUND, id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAllByBookId(long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new NotFoundException(format(ErrorMessage.BOOK_NOT_FOUND, bookId)));
        return commentRepository.findAllByBook(book);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

}
