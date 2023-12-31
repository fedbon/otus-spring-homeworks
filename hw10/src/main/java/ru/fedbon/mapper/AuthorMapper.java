package ru.fedbon.mapper;

import org.springframework.stereotype.Component;
import ru.fedbon.dto.AuthorDto;
import ru.fedbon.model.Author;

@Component
public class AuthorMapper {

    private AuthorMapper() {
    }

    public static AuthorDto mapAuthorToDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
}
