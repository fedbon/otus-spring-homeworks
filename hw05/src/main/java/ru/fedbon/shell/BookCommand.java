package ru.fedbon.shell;

import lombok.RequiredArgsConstructor;
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

    @ShellMethod(key = {"get-books-count", "books-count"},
            value = "Возвращает количество всех книг в БД")
    public String handleGetCount() {
        var count = bookService.getCount();
        return format("Общее количество книг в БД: %d", count);
    }

    @ShellMethod(key = {"add-new-book-with-ids", "new-book"},
            value = "Добавляет новую книгу в БД: укажите название книги, идентификатор жанра, идентификатор автора")
    public String handleAdd(String title, long genreId, long authorId) {
        var bookDto = new BookDto();
        bookDto.setTitle(title);
        bookDto.setGenreId(genreId);
        bookDto.setAuthorId(authorId);

        bookService.add(bookDto);

        return format("Добавлена новая книга c названием: %s", title);
    }

    @ShellMethod(key = {"change-book-with-ids", "change-book"},
            value = "Изменяет существующую в БД книгу: укажите идентификатор книги, название книги, " +
                    "идентификатор жанра, идентификатор автора")
    public String handleChange(long id, String title, long genreId, long authorId) {
        var bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setTitle(title);
        bookDto.setGenreId(genreId);
        bookDto.setAuthorId(authorId);

        bookService.change(bookDto);

        return format("Книга с id=%d изменена", id);
    }

    @ShellMethod(key = {"show-all-books", "all-books"},
            value = "Выводит список всех книг в БД")
    public String handleGetAll() {
        var books = bookService.getAll();
        return books.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"show-books-by-genre", "books-by-genre"},
            value = "Выводит список всех книг определенного жанра: укажите жанр")
    public String handleGetAllByGenreName(String genreName) {
        var books = bookService.getAllByGenreName(genreName);
        return books.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"show-books-by-author", "books-by-author"},
            value = "Выводит список всех книг определенного автора: укажите имя автора")
    public String handleGetAllByAuthorName(String authorName) {
        var books = bookService.getAllByAuthorName(authorName);
        return books.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"delete-book-from-db", "delete-book"},
            value = "Удаляет книгу из БД по ее идентификатору: укажите идентификатор книги")
    public String handleDeleteById(long id) {
        bookService.deleteById(id);
        return format("Книга c id=%d удалена", id);
    }

    @ShellMethod(key = {"delete-all-books-from-db", "delete-all-books"},
            value = "Удаляет все книги из БД")
    public String handleDeleteAll() {
        var count = bookService.deleteAll();
        return format("%d книг удалено", count);
    }
}
