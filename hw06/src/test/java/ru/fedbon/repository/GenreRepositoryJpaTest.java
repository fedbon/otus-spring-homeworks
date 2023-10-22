package ru.fedbon.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.fedbon.model.Genre;
import ru.fedbon.repository.impl.GenreRepositoryJpa;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест GenreRepositoryJpa должен")
@DataJpaTest
@Import(value = {GenreRepositoryJpa.class})
class GenreRepositoryJpaTest {

    private static final int EXPECTED_GENRES_COUNT = 2;

    private static final long EXPECTED_GENRES_COUNT_AFTER_CLEANING = 0;

    private static final long EXISTING_GENRE_ID = 1L;

    private static final String NEW_GENRE_NAME = "Жанр_03";

    private static final String UPDATED_GENRE_NAME = "Жанр_01_updated";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Test
    @DisplayName("возвращать ожидаемое количество жанров в БД")
    void shouldReturnExpectedGenresCount() {
        var actualGenresCount = genreRepositoryJpa.count();
        assertThat(actualGenresCount).isEqualTo(EXPECTED_GENRES_COUNT);
    }

    @Test
    @DisplayName("добавлять жанр в БД")
    void shouldInsertGenre() {
        var expectedGenre = new Genre();
        expectedGenre.setName(NEW_GENRE_NAME);

        genreRepositoryJpa.save(expectedGenre);
        assertThat(expectedGenre.getId()).isGreaterThan(0);

        var actualGenre = testEntityManager.find(Genre.class, expectedGenre.getId());

        assertThat(actualGenre)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);

        assertThat(actualGenre)
                .matches(genre -> genre.getId() == 3)
                .matches(genre -> genre.getName().equals(NEW_GENRE_NAME));
    }

    @Test
    @DisplayName("изменять имеющийся в БД жанр")
    void shouldUpdateGenre() {
        var genre = testEntityManager.find(Genre.class, EXISTING_GENRE_ID);
        genre.setName(UPDATED_GENRE_NAME);
        testEntityManager.flush();
        var updatedGenre = testEntityManager.find(Genre.class, EXISTING_GENRE_ID);

        assertThat(updatedGenre.getName()).isEqualTo(UPDATED_GENRE_NAME);
    }

    @Test
    @DisplayName("возвращать ожидаемый жанр по идентификатору")
    void shouldReturnExpectedGenreById() {
        var expectedGenre = testEntityManager.find(Genre.class, EXISTING_GENRE_ID);
        var optionalActualGenre = genreRepositoryJpa.findById(EXISTING_GENRE_ID);

        assertThat(optionalActualGenre)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("возвращать ожидаемый список жанров")
    void shouldReturnExpectedGenresList() {
        var genres = genreRepositoryJpa.findAll();

        assertThat(genres).isNotNull().hasSize(EXPECTED_GENRES_COUNT)
                .allMatch(genre -> !genre.getName().isEmpty())
                .containsOnlyOnce(testEntityManager.find(Genre.class, EXISTING_GENRE_ID));
    }

    @Test
    @DisplayName("удалять заданный жанр по его идентификатору")
    void shouldDeleteGenreById() {
        var existingGenre = testEntityManager.find(Genre.class, EXISTING_GENRE_ID);
        assertThat(existingGenre).isNotNull();

        genreRepositoryJpa.deleteById(EXISTING_GENRE_ID);

        var deletedGenre = testEntityManager.find(Genre.class, EXISTING_GENRE_ID);
        assertThat(deletedGenre).isNull();
    }

    @Test
    @DisplayName("удалять все жанры из БД")
    void shouldDeleteAllGenres() {
        var actualCountBeforeCleaning = genreRepositoryJpa.count();
        assertThat(actualCountBeforeCleaning).isEqualTo(EXPECTED_GENRES_COUNT);

        genreRepositoryJpa.deleteAll();

        var actualCountAfterCleaning = genreRepositoryJpa.count();
        assertThat(actualCountAfterCleaning).isEqualTo(EXPECTED_GENRES_COUNT_AFTER_CLEANING);
    }
}