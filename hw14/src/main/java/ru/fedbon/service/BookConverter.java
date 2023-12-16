package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.fedbon.model.jpa.Author;
import ru.fedbon.model.jpa.Book;
import ru.fedbon.model.jpa.Genre;
import ru.fedbon.model.mongo.AuthorDocument;
import ru.fedbon.model.mongo.BookDocument;
import ru.fedbon.model.mongo.GenreDocument;

@Service
@RequiredArgsConstructor
public class BookConverter implements Converter<Book, BookDocument> {

    private final Converter<Author, AuthorDocument> authorConverter;

    private final Converter<Genre, GenreDocument> genreConverter;


    @Override
    public BookDocument convert(Book book) {
        var genre = book.getGenre();
        var author = book.getAuthor();
        return new BookDocument(String.valueOf(book.getId()),
                        book.getTitle(),
                genreConverter.convert(genre),
                authorConverter.convert(author));
    }
}
