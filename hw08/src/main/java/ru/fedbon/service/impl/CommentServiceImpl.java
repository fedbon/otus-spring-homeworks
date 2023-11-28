package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fedbon.dto.CommentDto;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.mapper.CommentMapper;
import ru.fedbon.model.Comment;
import ru.fedbon.repository.BookRepository;
import ru.fedbon.repository.CommentRepository;
import ru.fedbon.service.CommentService;
import ru.fedbon.utils.ErrorMessage;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    @Override
    public Comment create(CommentDto commentDto) {
        var book = bookRepository.findById(commentDto.getBookId())
                .orElseThrow(() ->
                        new NotFoundException(format(ErrorMessage.BOOK_NOT_FOUND,
                                commentDto.getBookId())));
        var bookComment = CommentMapper.mapDtoToComment(commentDto, book);
        return commentRepository.save(bookComment);
    }

    @Override
    public Comment update(CommentDto commentDto) {
        var comment = commentRepository.findById(commentDto.getId())
                .orElseThrow(() ->
                        new NotFoundException(format(ErrorMessage.COMMENT_NOT_FOUND,
                                commentDto.getId())));

        comment.setText(commentDto.getText());

        return comment;
    }

    @Override
    public Comment getById(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(format(ErrorMessage.COMMENT_NOT_FOUND, id)));
    }

    @Override
    public List<Comment> getAllByBookId(String id) {
        var book = bookRepository.findById(id).orElseThrow(() ->
                        new NotFoundException(format(ErrorMessage.BOOK_NOT_FOUND, id)));
        return commentRepository.findAllByBookId(book.getId());
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

}
