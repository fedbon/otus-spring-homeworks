package ru.fedbon.stringifier;

import org.springframework.stereotype.Component;
import ru.fedbon.model.Genre;

@Component
public class GenreStringifierImpl implements GenreStringifier {
    @Override
    public String stringify(Genre genre) {
        return "Genre(id=" + genre.getId() + ", genreName=" + genre.getGenreName() + ")";
    }
}
