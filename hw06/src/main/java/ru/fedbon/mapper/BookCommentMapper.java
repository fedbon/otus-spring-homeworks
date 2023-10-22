package ru.fedbon.mapper;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fedbon.dto.BookCommentDto;
import ru.fedbon.model.Book;
import ru.fedbon.model.BookComment;


@Component
@NoArgsConstructor
public class BookCommentMapper {

    public static BookComment mapDtoToNewBookComment(BookCommentDto bookCommentDto, Book book) {

        var bookCommentBuilder = BookComment.builder();
        bookCommentBuilder.text(bookCommentDto.getText());
        bookCommentBuilder.book(book);

        return bookCommentBuilder.build();
    }

    public static BookComment mapDtoToUpdatedBookComment(BookCommentDto bookCommentDto, Book book) {

        var bookCommentBuilder = BookComment.builder();
        bookCommentBuilder.id(bookCommentDto.getId());
        bookCommentBuilder.text(bookCommentDto.getText());
        bookCommentBuilder.book(book);

        return bookCommentBuilder.build();
    }
}
