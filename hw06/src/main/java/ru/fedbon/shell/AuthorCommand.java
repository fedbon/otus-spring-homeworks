package ru.fedbon.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.repository.AuthorRepository;
import ru.fedbon.stringifier.AuthorStringifier;
import ru.fedbon.service.AuthorService;

import java.util.stream.Collectors;

import static java.lang.String.format;

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommand {

    private final AuthorService authorService;

    private final AuthorRepository authorRepository;

    private final AuthorStringifier stringifier;

    @ShellMethod(key = {"get-authors-count", "authors-count"},
            value = "Возвращает количество всех авторов в БД")
    public String handleGetCount() {
        var count = authorService.getCount();
        return format("Общее количество авторов в БД: %d", count);
    }

    @ShellMethod(key = {"add-new-author", "new-author"},
            value = "Добавляет нового автора в БД: укажите имя автора")
    public String handleAdd(String authorName) {
        return format("Добавлен новый автор: %s", stringifier.stringify(authorService.add(authorName)));
    }

    @ShellMethod(key = {"change-author-by-id"},
            value = "Изменяет существующего в БД автора: укажите идентификатор автора, имя автора")
    public String handleChange(long id, String authorName) {
        var author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(format("Не найден автор с идентификатором %d", id)));
        author.setName(authorName);
        return format("Автор изменен: %s", stringifier.stringify(authorService.change(author)));
    }

    @ShellMethod(key = {"get-all-authors", "all-authors"},
            value = "Выводит список всех авторов в БД")
    public String handleGetAll() {
        var authors = authorService.getAll();
        return authors.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"get-author-by-id", "author-by-id"},
            value = "Ищет автора в БД по его идентификатору: укажите идентификатор автора")
    public String handleGetById(long id) {
        var author = authorService.getById(id);
        return format("Автор найден: %s", stringifier.stringify(author));
    }

    @ShellMethod(key = {"delete-author-by-id"},
            value = "Удаляет автора из БД по его идентификатору: укажите идентификатор автора")
    public String handleDeleteById(long id) {
        authorService.deleteById(id);
        return format("Автор c id=%d удален", id);
    }

    @ShellMethod(key = {"delete-all-authors"},
            value = "Удаляет всех авторов из БД")
    public String handleDeleteAll() {
        var count = authorService.deleteAll();
        return format("%d авторов удалено", count);
    }
}
