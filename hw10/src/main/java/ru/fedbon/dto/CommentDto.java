package ru.fedbon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fedbon.model.Comment;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {

    @NotBlank
    private Long id;

    @NotBlank
    private String text;

    @NotBlank
    private Long bookId;

    public static CommentDto transformDomainToDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getBook().getId());
    }
}
