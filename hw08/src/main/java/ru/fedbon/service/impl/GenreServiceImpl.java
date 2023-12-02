package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.model.Genre;
import ru.fedbon.repository.GenreRepository;
import ru.fedbon.service.GenreService;
import ru.fedbon.utils.ErrorMessage;

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
    public Genre create(String name) {
        var genre = new Genre();
        genre.setName(name);
        return genreRepository.save(genre);
    }

    @Override
    public Genre update(Genre genreDto) {
        var genre = genreRepository.findById(genreDto.getId())
                .orElseThrow(() ->
                        new NotFoundException(format(ErrorMessage.GENRE_NOT_FOUND, genreDto.getId())));
        genre.setName(genreDto.getName());
        return genre;
    }

    @Override
    public List<Genre> getAll(Sort sort) {
        return genreRepository.findAll(sort);
    }

    @Override
    public Genre getById(String id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format(ErrorMessage.GENRE_NOT_FOUND, id)));
    }

    @Override
    public void deleteById(String id) {
        genreRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        genreRepository.deleteAll();
    }
}
