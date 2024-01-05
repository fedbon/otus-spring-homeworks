package ru.fedbon.bookstore.service;

import org.springframework.data.domain.Sort;
import ru.fedbon.bookstore.dto.AuthorDto;


import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAll(Sort sort);

}
