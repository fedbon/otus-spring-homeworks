package ru.fedbon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fedbon.model.Book;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {

    @NotBlank
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String genre;

    public static BookDto transformDomainToDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getGenre().getName());
    }
}
