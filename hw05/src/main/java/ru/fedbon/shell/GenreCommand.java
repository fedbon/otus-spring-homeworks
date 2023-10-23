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
    public String handleGetCount() {
        var count = genreService.getCount();
        return format("Общее количество жанров в БД: %d", count);
    }

    @ShellMethod(key = {"add-new-genre", "new-genre"},
            value = "Добавляет новый жанр в БД: укажите название жанра")
    public String handleAdd(String genreName) {
        var genre = genreService.add(genreName);
        return format("Добавлен новый жанр: %s", stringifier.stringify(genre));
    }

    @ShellMethod(key = {"change-genre"},
            value = "Изменяет существующий в БД жанр: укажите идентификатор жанра, название жанра")
    public String handleChange(long id, String genreName) {
        var genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Не найден жанр с идентификатором %d", id)));
        genre.setGenreName(genreName);
        genreService.change(genre);
        return format("Жанр изменен: %s", stringifier.stringify(genre));
    }

    @ShellMethod(key = {"show-all-genres", "all-genres"},
            value = "Выводит список всех жанров в БД")
    public String handleGetAll() {
        var genres = genreService.getAll();
        return genres.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"delete-genre-from-db", "delete-genre"},
            value = "Удаляет жанр из БД по его идентификатору: укажите идентификатор жанра")
    public String handleDeleteById(long id) {
        genreService.deleteById(id);
        return format("Жанр с id=%d удален", id);
    }

    @ShellMethod(key = {"delete-all-genres-from-db", "delete-all-genres"},
            value = "Удаляет все жанры из БД")
    public String handleDeleteAll() {
        var count = genreService.deleteAll();
        return format("%d жанров удалено", count);
    }
}
