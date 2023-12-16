package ru.fedbon.service;

import org.springframework.data.domain.Sort;
import ru.fedbon.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAll(Sort sort);
}
