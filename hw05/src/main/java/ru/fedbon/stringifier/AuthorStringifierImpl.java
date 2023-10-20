package ru.fedbon.stringifier;

import org.springframework.stereotype.Component;
import ru.fedbon.model.Author;

@Component
public class AuthorStringifierImpl implements AuthorStringifier {
    @Override
    public String stringify(Author author) {
        return "Author(id=" + author.getId() + ", name=" + author.getName() + ")";
    }
}
