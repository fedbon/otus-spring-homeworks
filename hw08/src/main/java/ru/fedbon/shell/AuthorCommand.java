package ru.fedbon.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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

    @ShellMethod(key = {"authors-count"},
            value = "Возвращает количество всех авторов в БД")
    public String handleGetCount() {
        var count = authorService.getCount();
        return format("Общее количество авторов в БД: %s", count);
    }

    @ShellMethod(key = {"new-author"},
            value = "Добавляет нового автора в БД: укажите имя автора")
    public String handleAdd(String authorName) {
        return format("Добавлен новый автор: %s", stringifier.stringify(authorService.create(authorName)));
    }

    @ShellMethod(key = {"change-author-by-id"},
            value = "Изменяет существующего в БД автора: укажите идентификатор автора, имя автора")
    public String handleChange(String id, String authorName) {
        var author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(format("Не найден автор с идентификатором %d", id)));
        author.setName(authorName);
        return format("Автор изменен: %s", stringifier.stringify(authorService.update(author)));
    }

    @ShellMethod(key = {"all-authors"},
            value = "Выводит список всех авторов в БД")
    public String handleGetAll() {
        var authors = authorService.getAll(Sort.by(Sort.Direction.ASC,"id"));
        return authors.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"author-by-id"},
            value = "Ищет автора в БД по его идентификатору: укажите идентификатор автора")
    public String handleGetById(String id) {
        var author = authorService.getById(id);
        return format("Автор найден: %s", stringifier.stringify(author));
    }

    @ShellMethod(key = {"delete-author-by-id"},
            value = "Удаляет автора из БД по его идентификатору: укажите идентификатор автора")
    public String handleDeleteById(String id) {
        authorService.deleteById(id);
        return format("Автор c id=%s удален", id);
    }

    @ShellMethod(key = {"delete-all-authors"},
            value = "Удаляет всех авторов из БД")
    public String handleDeleteAll() {
        authorService.deleteAll();
        return "Все авторы удалены";
    }
}
