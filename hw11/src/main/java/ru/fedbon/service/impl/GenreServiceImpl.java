package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.fedbon.dto.GenreDto;
import ru.fedbon.mapper.GenreMapper;
import ru.fedbon.repository.GenreRepository;
import ru.fedbon.service.GenreService;



@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;


    @Override
    public Flux<GenreDto> getAll(Sort sort) {
        return genreRepository.findAll()
                .map(GenreMapper::mapGenreToDto);
    }

}
