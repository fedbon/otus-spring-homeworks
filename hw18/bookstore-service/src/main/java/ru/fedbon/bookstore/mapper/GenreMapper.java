package ru.fedbon.bookstore.mapper;

import org.springframework.stereotype.Component;
import ru.fedbon.bookstore.dto.GenreDto;
import ru.fedbon.bookstore.model.Genre;


@Component
public class GenreMapper {

    private GenreMapper() {
    }

    public static GenreDto mapGenreToDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}

