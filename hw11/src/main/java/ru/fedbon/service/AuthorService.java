package ru.fedbon.service;

import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import ru.fedbon.dto.AuthorDto;



public interface AuthorService {

    Flux<AuthorDto> getAll(Sort sort);

}
