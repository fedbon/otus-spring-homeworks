package ru.fedbon.mapper;


import org.springframework.stereotype.Component;
import ru.fedbon.dto.CommentDto;
import ru.fedbon.model.Book;
import ru.fedbon.model.Comment;


@Component
public class CommentMapper {

    private CommentMapper() {

    }

    public static Comment mapDtoToComment(CommentDto commentDto, Book book) {

        var commentBuilder = Comment.builder();
        commentBuilder.text(commentDto.getText());
        commentBuilder.book(book);

        return commentBuilder.build();
    }
}
