package ru.fedbon.stringifier;

import org.springframework.stereotype.Service;
import ru.fedbon.model.Genre;

@Service
public class GenreStringifierImpl implements GenreStringifier {
    @Override
    public String stringify(Genre genre) {
        return "Genre(id=" + genre.getId() + ", genreName=" + genre.getGenreName() + ")";
    }
}
