package ru.fedbon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.model.Author;
import ru.fedbon.repository.AuthorRepository;
import ru.fedbon.service.AuthorService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;


    @Transactional(readOnly = true)
    @Override
    public List<Author> getAll(Sort sort) {
        return authorRepository.findAll(sort);
    }

}
