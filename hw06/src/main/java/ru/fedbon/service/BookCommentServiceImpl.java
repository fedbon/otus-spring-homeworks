package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.exception.BookCommentNotFoundException;
import ru.fedbon.exception.BookNotFoundException;
import ru.fedbon.model.BookComment;
import ru.fedbon.repository.BookCommentRepository;
import ru.fedbon.repository.BookRepository;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {

    private final BookRepository bookRepository;

    private final BookCommentRepository bookCommentRepository;

    @Transactional
    @Override
    public void addBookComment(long id, String text) {
        var book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new BookNotFoundException(format("Не найдена книга с идентификатором %d", id)));
        var bookComment = new BookComment();
        bookComment.setText(text);
        bookComment.setBook(book);
        bookCommentRepository.save(bookComment);
    }

    @Transactional
    @Override
    public BookComment changeBookComment(long id, String text) {
        var bookComment = bookCommentRepository.findById(id)
                .orElseThrow(() ->
                        new BookCommentNotFoundException(format("Не найден комментарий с идентификатором %d", id)));
        bookComment.setText(text);
        bookCommentRepository.update(bookComment);
        return bookComment;
    }

    @Transactional(readOnly = true)
    @Override
    public BookComment getBookCommentById(long id) {
        return bookCommentRepository.findById(id)
                .orElseThrow(() ->
                        new BookCommentNotFoundException(format("Не найден комментарий с идентификатором %d", id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookComment> getAllCommentsByBookId(long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new BookNotFoundException(format("Не найдена книга с идентификатором %d", id)));
        return bookCommentRepository.findAllCommentsForBook(book);
    }

    @Transactional
    @Override
    public void deleteBookCommentById(long id) {
        bookCommentRepository.deleteById(id);
    }

}
