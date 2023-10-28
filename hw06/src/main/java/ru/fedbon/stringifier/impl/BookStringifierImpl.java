package ru.fedbon.stringifier.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fedbon.model.Book;
import ru.fedbon.stringifier.AuthorStringifier;
import ru.fedbon.stringifier.BookStringifier;
import ru.fedbon.stringifier.GenreStringifier;

@Component
@RequiredArgsConstructor
public class BookStringifierImpl implements BookStringifier {

    private final GenreStringifier genreStringifier;

    private final AuthorStringifier authorStringifier;

    @Override
    public String stringify(Book book) {
        return "Book(id=" + book.getId() + ", title=" + book.getTitle() +
                ", genre=" + genreStringifier.stringify(book.getGenre()) +
                ", author=" + authorStringifier.stringify(book.getAuthor()) + ")";
    }
}
