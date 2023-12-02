package ru.fedbon.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.fedbon.dto.BookDto;
import ru.fedbon.service.BookService;
import ru.fedbon.stringifier.BookStringifier;

import java.util.stream.Collectors;

import static java.lang.String.format;

@ShellComponent
@RequiredArgsConstructor
public class BookCommand {

    private final BookService bookService;

    private final BookStringifier stringifier;

    @ShellMethod(key = {"books-count"},
            value = "Возвращает количество всех книг в БД")
    public String handleGetCount() {
        var count = bookService.getCount();
        return format("Общее количество книг в БД: %s", count);
    }

    @ShellMethod(key = {"add-new-book", "new-book"},
            value = "Добавляет новую книгу в БД: укажите название книги, идентификатор жанра, идентификатор автора")
    public String handleAdd(String title, String genreId, String authorId) {
        var bookDto = new BookDto();
        bookDto.setTitle(title);
        bookDto.setGenreId(genreId);
        bookDto.setAuthorId(authorId);

        return format("Добавлена новая книга: %s", stringifier.stringify(bookService.create(bookDto)));
    }

    @ShellMethod(key = {"change-book-by-id"},
            value = "Изменяет существующую в БД книгу: укажите идентификатор книги, название книги, " +
                    "идентификатор жанра, идентификатор автора")
    public String handleChange(String id, String title, String genreId, String authorId) {
        var bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setTitle(title);
        bookDto.setGenreId(genreId);
        bookDto.setAuthorId(authorId);

        return format("Книга изменена: %s", stringifier.stringify(bookService.update(bookDto)));
    }

    @ShellMethod(key = {"all-books"},
            value = "Выводит список всех книг в БД")
    public String handleGetAll() {
        var books = bookService.getAll(Sort.by(Sort.Direction.ASC,"id"));
        return books.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"book-by-id"},
            value = "Ищет книгу в БД по ее идентификатору: укажите идентификатор книги")
    public String handleGetById(String id) {
        var book = bookService.getById(id);
        return format("Книга найдена: %s", stringifier.stringify(book));
    }

    @ShellMethod(key = {"books-by-genre-id"},
            value = "Выводит список всех книг определенного жанра: укажите идентификатор жанра")
    public String handleGetAllByGenreId(String id) {
        var books = bookService.getAllByGenreId(id);
        return books.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"books-by-author-id"},
            value = "Выводит список всех книг определенного автора: укажите идентификатор автора")
    public String handleGetAllByAuthorId(String id) {
        var books = bookService.getAllByAuthorId(id);
        return books.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"delete-book-by-id"},
            value = "Удаляет книгу из БД по ее идентификатору: укажите идентификатор книги")
    public String handleDeleteById(String id) {
        bookService.deleteById(id);
        return format("Книга c id=%s удалена", id);
    }

    @ShellMethod(key = {"delete-all-books"},
            value = "Удаляет все книги из БД")
    public String handleDeleteAll() {
        bookService.deleteAll();
        return "Все книги удалены";
    }
}
