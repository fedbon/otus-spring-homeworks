package ru.fedbon.stringifier;

import org.springframework.stereotype.Service;
import ru.fedbon.model.Author;

@Service
public class AuthorStringifierImpl implements AuthorStringifier {
    @Override
    public String stringify(Author author) {
        return "Author(id=" + author.getId() + ", name=" + author.getName() + ")";
    }
}
