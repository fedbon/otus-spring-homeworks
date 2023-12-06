package ru.fedbon.service;

import org.springframework.data.domain.Sort;
import ru.fedbon.dto.AuthorDto;


import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAll(Sort sort);

}
