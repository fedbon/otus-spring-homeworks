package ru.fedbon.stringifier.impl;

import org.springframework.stereotype.Component;
import ru.fedbon.model.Genre;
import ru.fedbon.stringifier.GenreStringifier;

@Component
public class GenreStringifierImpl implements GenreStringifier {
    @Override
    public String stringify(Genre genre) {
        return "Genre(id=" + genre.getId() + ", genreName=" + genre.getName() + ")";
    }
}
