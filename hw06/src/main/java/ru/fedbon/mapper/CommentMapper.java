package ru.fedbon.mapper;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fedbon.dto.CommentDto;
import ru.fedbon.model.Book;
import ru.fedbon.model.Comment;


@Component
@NoArgsConstructor
public class CommentMapper {

    public static Comment mapDtoToNewBookComment(CommentDto commentDto, Book book) {

        var bookCommentBuilder = Comment.builder();
        bookCommentBuilder.text(commentDto.getText());
        bookCommentBuilder.book(book);

        return bookCommentBuilder.build();
    }

    public static Comment mapDtoToUpdatedBookComment(CommentDto commentDto, Book book) {

        var bookCommentBuilder = Comment.builder();
        bookCommentBuilder.id(commentDto.getId());
        bookCommentBuilder.text(commentDto.getText());
        bookCommentBuilder.book(book);

        return bookCommentBuilder.build();
    }
}
