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
    private String id;

    private String title;

    private String genreId;

    private String authorId;
}
