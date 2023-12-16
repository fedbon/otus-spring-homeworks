package ru.fedbon.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.fedbon.model.jpa.Genre;
import ru.fedbon.model.mongo.GenreDocument;

@Service
public class GenreConverter implements Converter<Genre, GenreDocument> {

    @Override
    public GenreDocument convert(Genre genre) {
        return new GenreDocument(String.valueOf(genre.getId()), genre.getName());
    }
}
