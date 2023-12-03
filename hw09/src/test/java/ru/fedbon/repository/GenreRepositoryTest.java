package ru.fedbon.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import ru.fedbon.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест GenreRepositoryJpa должен")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class GenreRepositoryTest {

    private static final int EXPECTED_GENRES_COUNT = 2;

    private static final long EXPECTED_GENRES_COUNT_AFTER_CLEANING = 0;

    private static final long EXISTING_GENRE_ID = 1L;

    private static final String NEW_GENRE_NAME = "Жанр_03";

    private static final String UPDATED_GENRE_NAME = "Жанр_01_updated";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("добавлять жанр в БД")
    void shouldInsertGenre() {
        var expectedGenre = new Genre();
        expectedGenre.setName(NEW_GENRE_NAME);

        genreRepository.save(expectedGenre);
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
    @DisplayName("изменять имеющийся в БД жанр при отключении объекта жанра от контекста")
    void shouldUpdateGenreWithDetaching() {
        var genre = testEntityManager.find(Genre.class, EXISTING_GENRE_ID);
        testEntityManager.detach(genre);
        genre.setName(UPDATED_GENRE_NAME);
        genreRepository.save(genre);
        var updatedGenre = testEntityManager.find(Genre.class, EXISTING_GENRE_ID);

        assertThat(updatedGenre.getName()).isEqualTo(UPDATED_GENRE_NAME);
    }

    @Test
    @DisplayName("возвращать ожидаемый жанр по идентификатору")
    void shouldReturnExpectedGenreById() {
        var expectedGenre = testEntityManager.find(Genre.class, EXISTING_GENRE_ID);
        var optionalActualGenre = genreRepository.findById(EXISTING_GENRE_ID);

        assertThat(optionalActualGenre)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("возвращать ожидаемый список жанров")
    void shouldReturnExpectedGenresList() {
        var expectedGenres = List.of(
                testEntityManager.find(Genre.class, 1L),
                testEntityManager.find(Genre.class, 2L)
        );
        var actualGenres = genreRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        assertThat(actualGenres).isNotNull().hasSize(EXPECTED_GENRES_COUNT)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedGenres);
    }

    @Test
    @DisplayName("удалять заданный жанр по его идентификатору")
    void shouldDeleteGenreById() {
        var existingGenre = testEntityManager.find(Genre.class, EXISTING_GENRE_ID);
        assertThat(existingGenre).isNotNull();

        genreRepository.deleteById(EXISTING_GENRE_ID);

        var deletedGenre = testEntityManager.find(Genre.class, EXISTING_GENRE_ID);
        assertThat(deletedGenre).isNull();
    }
}