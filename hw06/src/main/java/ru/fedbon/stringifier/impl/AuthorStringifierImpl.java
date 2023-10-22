package ru.fedbon.stringifier.impl;

import org.springframework.stereotype.Component;
import ru.fedbon.model.Author;
import ru.fedbon.stringifier.AuthorStringifier;

@Component
public class AuthorStringifierImpl implements AuthorStringifier {

    @Override
    public String stringify(Author author) {
        return "Author(id=" + author.getId() + ", name=" + author.getName() + ")";
    }
}
