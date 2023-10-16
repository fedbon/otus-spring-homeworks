package ru.fedbon.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.fedbon.stringifier.GenreStringifier;
import ru.fedbon.service.GenreService;

import java.util.stream.Collectors;

import static java.lang.String.format;

@ShellComponent
@RequiredArgsConstructor
public class GenreCommand {

    private final GenreService genreService;

    private final GenreStringifier converter;

    @ShellMethod(key = {"get-genres-count", "genres-count"},
            value = "Возвращает количество всех жанров в БД")
    public String handleGetAllGenresCount() {
        var count = genreService.getGenresCount();
        return format("Общее количество жанров в БД: %d", count);
    }

    @ShellMethod(key = {"add-new-genre", "new-genre"},
            value = "Добавляет новый жанр в БД: укажите название жанра")
    public String handleAddGenre(String genreName) {
        var genre = genreService.addGenre(genreName);
        return format("Добавлен новый жанр: %s", converter.stringify(genre));
    }

    @ShellMethod(key = {"change-genre"},
            value = "Изменяет существующий в БД жанр: укажите идентификатор жанра, название жанра")
    public String handleChangeGenre(long id, String genreName) {
        var genre = genreService.changeGenre(id, genreName);
        return format("Жанр изменен: %s", converter.stringify(genre));
    }

    @ShellMethod(key = {"find-genre-by-id", "genre-by-id"},
            value = "Ищет жанр в БД по его идентификатору: укажите идентификатор жанра")
    public String handleGetGenreById(long id) {
        var genre = genreService.getGenreById(id);
        return format("Жанр найден: %s", converter.stringify(genre));
    }

    @ShellMethod(key = {"find-genre-by-name", "genre-by-name"},
            value = "Ищет жанр в БД по его названию: укажите название жанра")
    public String handleGetGenreByName(String genreName) {
        var genre = genreService.getGenreByName(genreName);
        return format("Жанр найден: %s", converter.stringify(genre));
    }

    @ShellMethod(key = {"show-all-genres", "all-genres"},
            value = "Выводит список всех жанров в БД")
    public String handleGetAllGenres() {
        var genres = genreService.getAllGenres();
        return genres.stream().map(converter::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"delete-genre-from-db", "delete-genre"},
            value = "Удаляет жанр из БД по его идентификатору: укажите идентификатор жанра")
    public String handleDeleteGenreById(long id) {
        var genre = genreService.deleteGenreById(id);
        return format("Жанр удален: %s", converter.stringify(genre));
    }

    @ShellMethod(key = {"delete-all-genres-from-db", "delete-all-genres"},
            value = "Удаляет все жанры из БД")
    public String handleDeleteAllGenres() {
        var count = genreService.deleteAllGenres();
        return format("%d жанров удалено", count);
    }
}
