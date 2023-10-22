package ru.fedbon.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.repository.GenreRepository;
import ru.fedbon.stringifier.GenreStringifier;
import ru.fedbon.service.GenreService;

import java.util.stream.Collectors;

import static java.lang.String.format;

@ShellComponent
@RequiredArgsConstructor
public class GenreCommand {

    private final GenreService genreService;

    private final GenreRepository genreRepository;

    private final GenreStringifier stringifier;

    @ShellMethod(key = {"get-genres-count", "genres-count"},
            value = "Возвращает количество всех жанров в БД")
    public String handleGetAllGenresCount() {
        var count = genreService.getCount();
        return format("Общее количество жанров в БД: %d", count);
    }

    @ShellMethod(key = {"add-new-genre", "new-genre"},
            value = "Добавляет новый жанр в БД: укажите название жанра")
    public String handleAddGenre(String genreName) {
        genreService.add(genreName);
        return format("Добавлен новый жанр с названием: %s", genreName);
    }

    @ShellMethod(key = {"change-genre-by-id"},
            value = "Изменяет существующий в БД жанр: укажите идентификатор жанра, название жанра")
    public String handleChangeGenre(long id, String genreName) {
        var genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Не найден жанр с идентификатором %d", id)));
        genre.setName(genreName);
        return format("Жанр изменен: %s", stringifier.stringify(genreService.change(genre)));
    }

    @ShellMethod(key = {"get-all-genres", "all-genres"},
            value = "Выводит список всех жанров в БД")
    public String handleGetAllGenres() {
        var genres = genreService.getAll();
        return genres.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"get-genre-by-id", "genre-by-id"},
            value = "Ищет жанр в БД по его идентификатору: укажите идентификатор жанра")
    public String handleGetGenreById(long id) {
        var genre = genreService.getById(id);
        return format("Жанр найден: %s", stringifier.stringify(genre));
    }

    @ShellMethod(key = {"delete-genre-by-id"},
            value = "Удаляет жанр из БД по его идентификатору: укажите идентификатор жанра")
    public String handleDeleteGenreById(long id) {
        genreService.deleteById(id);
        return format("Жанр с id=%d удален", id);
    }

    @ShellMethod(key = {"delete-all-genres"},
            value = "Удаляет все жанры из БД")
    public String handleDeleteAllGenres() {
        var count = genreService.deleteAll();
        return format("%d жанров удалено", count);
    }
}