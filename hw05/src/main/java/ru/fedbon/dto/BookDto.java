package ru.fedbon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {
    private Long id;

    private String title;

    private Long genreId;

    private Long authorId;
}
