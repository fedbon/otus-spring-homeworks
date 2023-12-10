package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.fedbon.dto.AuthorDto;
import ru.fedbon.mapper.AuthorMapper;
import ru.fedbon.repository.AuthorRepository;
import ru.fedbon.service.AuthorService;



@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;


    @Override
    public Flux<AuthorDto> getAll(Sort sort) {
        return authorRepository.findAll(sort)
                .map(AuthorMapper::mapAuthorToDto);
    }

}
