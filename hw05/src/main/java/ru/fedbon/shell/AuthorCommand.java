package ru.fedbon.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.fedbon.stringifier.AuthorStringifier;
import ru.fedbon.service.AuthorService;

import java.util.stream.Collectors;

import static java.lang.String.format;

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommand {

    private final AuthorService authorService;

    private final AuthorStringifier converter;

    @ShellMethod(key = {"get-authors-count", "authors-count"},
            value = "Возвращает количество всех авторов в БД")
    public String handleGetAuthorsCount() {
        var count = authorService.getAuthorsCount();
        return format("Общее количество авторов в БД: %d", count);
    }

    @ShellMethod(key = {"add-new-author", "new-author"},
            value = "Добавляет нового автора в БД: укажите имя автора")
    public String handleAddAuthor(String authorName) {
        var author = authorService.addAuthor(authorName);
        return format("Добавлен новый автор: %s", converter.stringify(author));
    }

    @ShellMethod(key = {"change-author"},
            value = "Изменяет существующего в БД автора: укажите идентификатор автора, имя автора")
    public String handleChangeAuthor(long id, String authorName) {
        var author = authorService.changeAuthor(id, authorName);
        return format("Автор изменен: %s", converter.stringify(author));
    }

    @ShellMethod(key = {"find-author-by-id", "author-by-id"},
            value = "Ищет автора в БД по его идентификатору: укажите идентификатор автора")
    public String handleGetAuthorById(long id) {
        var author = authorService.getAuthorById(id);
        return format("Автор найден: %s", converter.stringify(author));
    }

    @ShellMethod(key = {"find-author-by-name", "author-by-name"},
            value = "Ищет автора в БД по его имени: укажите имя автора")
    public String handleGetAuthorByName(String authorName) {
        var author = authorService.getAuthorByName(authorName);
        return format("Автор найден: %s", converter.stringify(author));
    }

    @ShellMethod(key = {"show-all-authors", "all-authors"},
            value = "Выводит список всех авторов в БД")
    public String handleGetAllAuthors() {
        var authors = authorService.getAllAuthors();
        return authors.stream().map(converter::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"delete-author-from-db", "delete-author"},
            value = "Удаляет автора из БД по его идентификатору: укажите идентификатор автора")
    public String handleDeleteAuthorById(long id) {
        var author = authorService.deleteAuthorById(id);
        return format("Автор удален: %s", converter.stringify(author));
    }

    @ShellMethod(key = {"delete-all-authors-from-db", "delete-all-authors"},
            value = "Удаляет всех авторов из БД")
    public String handleDeleteAllAuthors() {
        var count = authorService.deleteAllAuthors();
        return format("%d авторов удалено", count);
    }
}
