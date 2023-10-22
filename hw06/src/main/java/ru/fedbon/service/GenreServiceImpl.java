package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.exception.NotFoundException;
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
    public long getCount() {
        return genreRepository.count();
    }

    @Transactional
    @Override
    public void add(String name) {
        var genre = new Genre();
        genre.setName(name);
        genreRepository.save(genre);
    }

    @Transactional
    @Override
    public Genre change(Genre genre) {
        genreRepository.findById(genre.getId())
                .orElseThrow(() -> new NotFoundException(format("Не найден жанр с идентификатором %d", genre.getId())));
        genre.setName(genre.getName());
        genreRepository.update(genre);
        return genre;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
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
        return genreRepository.deleteAll();
    }
}