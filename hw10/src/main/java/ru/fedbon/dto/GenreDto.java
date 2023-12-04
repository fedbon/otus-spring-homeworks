package ru.fedbon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fedbon.model.Genre;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenreDto {

    @NotBlank
    private Long id;

    @NotBlank
    private String name;

    public static GenreDto transformDomainToDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}
