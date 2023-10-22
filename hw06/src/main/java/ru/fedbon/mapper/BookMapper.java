package ru.fedbon.mapper;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fedbon.dto.BookDto;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Genre;

@Component
@NoArgsConstructor
public class BookMapper {

    public static Book mapDtoToBook(BookDto bookDto, Genre genre, Author author) {

        var bookBuilder = Book.builder();

        if (bookDto != null) {
            bookBuilder.id(bookDto.getId());
            bookBuilder.title(bookDto.getTitle());
            bookBuilder.genre(genre);
            bookBuilder.author(author);
        }

        return bookBuilder.build();
    }
}
