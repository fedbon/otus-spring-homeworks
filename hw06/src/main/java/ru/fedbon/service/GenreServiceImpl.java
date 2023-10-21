package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.exception.GenreNotFoundException;
import ru.fedbon.model.Genre;
import ru.fedbon.repository.GenreRepository;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    @Override
    public long getGenresCount() {
        return genreRepository.count();
    }

    @Transactional
    @Override
    public Genre addGenre(String genreName) {
        var genre = new Genre();
        genre.setName(genreName);
        genreRepository.save(genre);
        return genre;
    }

    @Transactional
    @Override
    public void changeGenre(long id, String genreName) {
        var genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException(format("Не найден жанр с идентификатором %d", id)));
        genre.setName(genreName);
        genreRepository.update(genre);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Genre getGenreById(long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException(format("Не найден жанр с идентификатором %d", id)));
    }

    @Transactional
    @Override
    public void deleteGenreById(long id) {
        genreRepository.deleteById(id);
    }

    @Transactional
    @Override
    public long deleteAllGenres() {
        return genreRepository.deleteAll();
    }
}
