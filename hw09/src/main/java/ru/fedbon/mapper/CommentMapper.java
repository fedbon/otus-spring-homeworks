package ru.fedbon.mapper;


import org.springframework.stereotype.Component;
import ru.fedbon.dto.CommentCreateDto;
import ru.fedbon.dto.CommentDto;
import ru.fedbon.model.Book;
import ru.fedbon.model.Comment;


@Component
public class CommentMapper {

    private CommentMapper() {

    }

    public static Comment mapDtoToComment(CommentCreateDto commentCreateDto, Book book) {

        var commentBuilder = Comment.builder();
        commentBuilder.text(commentCreateDto.getText());
        commentBuilder.book(book);

        return commentBuilder.build();
    }

    public static CommentDto mapCommentToDto(Comment comment) {

        var commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setBookId(comment.getBook().getId());

        return commentDto;
    }
}
