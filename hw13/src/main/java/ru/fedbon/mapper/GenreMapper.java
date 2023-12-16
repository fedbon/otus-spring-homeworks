package ru.fedbon.mapper;

import org.springframework.stereotype.Component;
import ru.fedbon.dto.GenreDto;
import ru.fedbon.model.Genre;


@Component
public class GenreMapper {

    private GenreMapper() {
    }

    public static GenreDto mapGenreToDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}

