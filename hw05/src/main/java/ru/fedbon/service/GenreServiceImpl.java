package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fedbon.exception.GenreNotFoundException;
import ru.fedbon.model.Genre;
import ru.fedbon.repository.GenreRepository;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public long getGenresCount() {
        return genreRepository.count();
    }

    @Override
    public Genre addGenre(String genreName) {
        var genre = new Genre();
        genre.setGenreName(genreName);
        genreRepository.insert(genre);
        return genre;
    }

    @Override
    public void changeGenre(long id, String genreName) {
        var genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException(format("Не найден жанр с идентификатором %d", id)));
        genre.setGenreName(genreName);
        genreRepository.update(genre);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public void deleteGenreById(long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public long deleteAllGenres() {
        return genreRepository.deleteAll();
    }
}
