package ru.fedbon.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.fedbon.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест GenreRepositoryJdbc должен")
@JdbcTest
@Import(value = {GenreRepositoryJdbc.class})
class GenreRepositoryJdbcTest {

    private static final long EXPECTED_GENRES_COUNT = 2;

    private static final long EXPECTED_GENRES_COUNT_AFTER_CLEANING = 0;

    private static final long EXISTING_GENRE_ID = 1L;

    private static final String EXISTING_GENRE_NAME = "Жанр_01";

    private static final String NEW_GENRE_NAME = "Жанр_03";

    private static final String UPDATED_GENRE_NAME = "Жанр_01_updated";

    @Autowired
    private GenreRepositoryJdbc genreRepositoryJdbc;

    @Test
    @DisplayName("возвращать ожидаемое количество жанров в БД")
    void shouldReturnExpectedGenresCount() {
        var actualGenresCount = genreRepositoryJdbc.count();
        assertThat(actualGenresCount).isEqualTo(EXPECTED_GENRES_COUNT);
    }

    @Test
    @DisplayName("добавлять жанр в БД")
    void shouldInsertGenre() {
        var expectedGenre = new Genre();
        expectedGenre.setGenreName(NEW_GENRE_NAME);

        genreRepositoryJdbc.insert(expectedGenre);
        var actualGenre = genreRepositoryJdbc.findById(expectedGenre.getId());

        assertThat(actualGenre)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("изменять имеющийся в БД жанр")
    void shouldUpdateGenre() {
        var expectedGenre = new Genre(EXISTING_GENRE_ID, UPDATED_GENRE_NAME);

        genreRepositoryJdbc.update(expectedGenre);
        var actualGenre = genreRepositoryJdbc.findById(expectedGenre.getId());

        assertThat(actualGenre)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("возвращать ожидаемый жанр по идентификатору")
    void shouldReturnExpectedGenreById() {
        var expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);

        var actualGenre = genreRepositoryJdbc.findById(expectedGenre.getId());

        assertThat(actualGenre)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("возвращать ожидаемый список жанров")
    void shouldReturnExpectedGenresList() {
        var expectedGenres = List.of(
                new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME),
                new Genre(EXISTING_GENRE_ID + 1L, EXISTING_GENRE_NAME.replace('1', '2'))
        );

        var actualGenres = genreRepositoryJdbc.findAll();

        assertThat(actualGenres)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

    @Test
    @DisplayName("удалять заданный жанр по его идентификатору")
    void shouldDeleteGenreById() {
        var existingGenre = genreRepositoryJdbc.findById(EXISTING_GENRE_ID);
        assertThat(existingGenre).isNotEmpty();

        genreRepositoryJdbc.deleteById(EXISTING_GENRE_ID);

        var deletedGenre = genreRepositoryJdbc.findById(EXISTING_GENRE_ID);
        assertThat(deletedGenre).isEmpty();
    }

    @Test
    @DisplayName("удалять все жанры из БД")
    void shouldDeleteAllGenres() {
        var actualCountBeforeCleaning = genreRepositoryJdbc.count();
        assertThat(actualCountBeforeCleaning).isEqualTo(EXPECTED_GENRES_COUNT);

        genreRepositoryJdbc.deleteAll();

        var actualCountAfterCleaning = genreRepositoryJdbc.count();
        assertThat(actualCountAfterCleaning).isEqualTo(EXPECTED_GENRES_COUNT_AFTER_CLEANING);
    }
}