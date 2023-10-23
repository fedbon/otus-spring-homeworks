package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.model.Genre;
import ru.fedbon.repository.GenreRepository;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public long getCount() {
        return genreRepository.count();
    }

    @Override
    public Genre add(String genreName) {
        var genre = new Genre();
        genre.setGenreName(genreName);
        genreRepository.insert(genre);
        return genre;
    }

    @Override
    public void change(Genre genreDto) {
        var genre = genreRepository.findById(genreDto.getId())
                .orElseThrow(() ->
                        new NotFoundException(format("Не найден жанр с идентификатором %d", genreDto.getId())));
        genre.setGenreName(genreDto.getGenreName());
        genreRepository.update(genre);
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public long deleteAll() {
        return genreRepository.deleteAll();
    }
}
