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

    private final AuthorStringifier stringifier;

    @ShellMethod(key = {"get-authors-count", "authors-count"},
            value = "Возвращает количество всех авторов в БД")
    public String handleGetAuthorsCount() {
        var count = authorService.getAuthorsCount();
        return format("Общее количество авторов в БД: %d", count);
    }

    @ShellMethod(key = {"add-new-author", "new-author"},
            value = "Добавляет нового автора в БД: укажите имя автора")
    public String handleAddAuthor(String authorName) {
        authorService.addAuthor(authorName);
        return format("Добавлен новый автор с именем: %s", authorName);
    }

    @ShellMethod(key = {"change-author-by-id"},
            value = "Изменяет существующего в БД автора: укажите идентификатор автора, имя автора")
    public String handleChangeAuthor(long id, String authorName) {
        return format("Автор изменен: %s", stringifier.stringify(authorService.changeAuthor(id, authorName)));
    }

    @ShellMethod(key = {"get-all-authors", "all-authors"},
            value = "Выводит список всех авторов в БД")
    public String handleGetAllAuthors() {
        var authors = authorService.getAllAuthors();
        return authors.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"get-author-by-id", "author-by-id"},
            value = "Ищет автора в БД по его идентификатору: укажите идентификатор автора")
    public String handleGetAuthorById(long id) {
        var author = authorService.getAuthorById(id);
        return format("Автор найден: %s", stringifier.stringify(author));
    }

    @ShellMethod(key = {"delete-author-by-id"},
            value = "Удаляет автора из БД по его идентификатору: укажите идентификатор автора")
    public String handleDeleteAuthorById(long id) {
        authorService.deleteAuthorById(id);
        return format("Автор c id=%d удален", id);
    }

    @ShellMethod(key = {"delete-all-authors"},
            value = "Удаляет всех авторов из БД")
    public String handleDeleteAllAuthors() {
        var count = authorService.deleteAllAuthors();
        return format("%d авторов удалено", count);
    }
}
