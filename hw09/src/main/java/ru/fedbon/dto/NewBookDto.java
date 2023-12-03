package ru.fedbon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewBookDto {

    @NotBlank
    private String title;

    @NotBlank
    private Long genreId;

    @NotBlank
    private Long authorId;
}
