package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.model.Genre;
import ru.fedbon.repository.GenreRepository;
import ru.fedbon.service.GenreService;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    @Override
    public long getCount() {
        return genreRepository.count();
    }

    @Transactional
    @Override
    public Genre create(String name) {
        var genre = new Genre();
        genre.setName(name);
        return genreRepository.save(genre);
    }

    @Transactional
    @Override
    public Genre update(Genre genreDto) {
        var genre = genreRepository.findById(genreDto.getId())
                .orElseThrow(() ->
                        new NotFoundException(format("Не найден жанр с идентификатором %d", genreDto.getId())));
        genre.setName(genreDto.getName());
        return genre;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));
    }

    @Transactional(readOnly = true)
    @Override
    public Genre getById(long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Не найден жанр с идентификатором %d", id)));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }

    @Transactional
    @Override
    public long deleteAll() {
        return genreRepository.deleteAllCustom();
    }
}
