package ru.fedbon.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.fedbon.model.jpa.Author;
import ru.fedbon.model.mongo.AuthorDocument;

@Service
public class AuthorConverter implements Converter<Author, AuthorDocument> {

    @Override
    public AuthorDocument convert(Author author) {
        return new AuthorDocument(String.valueOf(author.getId()),author.getName());
    }
}
