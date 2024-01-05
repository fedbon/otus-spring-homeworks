package ru.fedbon.bookstore.mapper;

import org.springframework.stereotype.Component;
import ru.fedbon.bookstore.dto.AuthorDto;
import ru.fedbon.bookstore.model.Author;


@Component
public class AuthorMapper {

    private AuthorMapper() {
    }

    public static AuthorDto mapAuthorToDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
}
