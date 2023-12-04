package ru.fedbon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fedbon.model.Author;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorDto {

    @NotBlank
    private Long id;

    @NotBlank
    private String name;

    public static AuthorDto transformDomainToDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
}
