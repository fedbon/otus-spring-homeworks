package ru.fedbon.mapper;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fedbon.dto.CommentDto;
import ru.fedbon.model.Book;
import ru.fedbon.model.Comment;


@Component
@NoArgsConstructor
public class CommentMapper {

    public static Comment mapDtoToComment(CommentDto commentDto, Book book) {

        var commentBuilder = Comment.builder();
        commentBuilder.text(commentDto.getText());
        commentBuilder.book(book);

        return commentBuilder.build();
    }
}
