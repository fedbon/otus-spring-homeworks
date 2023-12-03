package ru.fedbon.mapper;

import org.springframework.stereotype.Component;
import ru.fedbon.dto.NewBookDto;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Genre;


@Component
public class BookMapper {

    private BookMapper() {
    }

    public static Book mapDtoToNewBook(NewBookDto newBookDto, Genre genre, Author author) {

        var bookBuilder = Book.builder();
        bookBuilder.title(newBookDto.getTitle());
        bookBuilder.genre(genre);
        bookBuilder.author(author);

        return bookBuilder.build();
    }
}
