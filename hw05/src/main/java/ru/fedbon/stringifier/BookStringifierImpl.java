package ru.fedbon.stringifier;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fedbon.model.Book;

@Service
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
