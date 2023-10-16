package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
        return genreRepository.insert(genre);
    }

    @Override
    public Genre changeGenre(long id, String genreName) {
        var genre = getGenreById(id);
        genre.setGenreName(genreName);
        return genreRepository.update(genre);
    }

    @Override
    public Genre getGenreById(long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден жанр с идентификатором %d", id)));
    }

    @Override
    public Genre getGenreByName(String genreName) {
        return genreRepository.findByName(genreName)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден жанр с названием %s", genreName)));
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre deleteGenreById(long id) {
        var genre = getGenreById(id);
        genreRepository.deleteById(id);
        return genre;
    }

    @Override
    public long deleteAllGenres() {
        return genreRepository.deleteAll();
    }
}
