package ru.fedbon.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {

    @NotNull
    private Long id;

    @NotBlank
    private String text;

    @NotNull
    private Long bookId;
}
