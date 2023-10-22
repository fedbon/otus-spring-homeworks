package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.exception.NotFoundException;
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
    public void add(long id, String text) {
        var book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(format("Не найдена книга с идентификатором %d", id)));
        var bookComment = new BookComment();
        bookComment.setText(text);
        bookComment.setBook(book);
        bookCommentRepository.save(bookComment);
    }

    @Transactional
    @Override
    public BookComment change(BookComment bookComment) {
        bookCommentRepository.findById(bookComment.getId())
                .orElseThrow(() ->
                        new NotFoundException(format("Не найден комментарий с идентификатором %d",
                                bookComment.getId())));
        bookComment.setText(bookComment.getText());
        bookCommentRepository.update(bookComment);
        return bookComment;
    }

    @Transactional(readOnly = true)
    @Override
    public BookComment getById(long id) {
        return bookCommentRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(format("Не найден комментарий с идентификатором %d", id)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookComment> getAllByBookId(long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(format("Не найдена книга с идентификатором %d", id)));
        return bookCommentRepository.findAllCommentsForBook(book);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookCommentRepository.deleteById(id);
    }

}
