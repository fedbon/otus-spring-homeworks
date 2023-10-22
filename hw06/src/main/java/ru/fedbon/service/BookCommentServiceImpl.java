package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.dto.BookCommentDto;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.mapper.BookCommentMapper;
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
    public BookComment add(BookCommentDto bookCommentDto) {
        var book = bookRepository.findById(bookCommentDto.getBookId())
                .orElseThrow(() ->
                        new NotFoundException(format("Не найдена книга с идентификатором %d",
                                bookCommentDto.getBookId())));
        var bookComment = BookCommentMapper.mapDtoToBookComment(bookCommentDto, book);
        return bookCommentRepository.save(bookComment);
    }

    @Transactional
    @Override
    public BookComment change(BookCommentDto bookCommentDto) {
        var book = bookRepository.findById(bookCommentDto.getBookId())
                .orElseThrow(() ->
                        new NotFoundException(format("Не найдена книга с идентификатором %d",
                                bookCommentDto.getBookId())));
        bookCommentRepository.findById(bookCommentDto.getId())
                .orElseThrow(() ->
                        new NotFoundException(format("Не найден комментарий с идентификатором %d",
                                bookCommentDto.getId())));

        return BookCommentMapper.mapDtoToBookComment(bookCommentDto, book);
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
