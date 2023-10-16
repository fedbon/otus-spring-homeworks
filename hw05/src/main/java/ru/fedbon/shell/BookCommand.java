package ru.fedbon.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.fedbon.service.BookService;
import ru.fedbon.stringifier.BookStringifier;

import java.util.stream.Collectors;

import static java.lang.String.format;

@ShellComponent
@RequiredArgsConstructor
public class BookCommand {

    private final BookService bookService;

    private final BookStringifier converter;

    @ShellMethod(key = {"get-books-count", "books-count"},
            value = "Возвращает количество всех книг в БД")
    public String handleGetBooksCount() {
        var count = bookService.getBooksCount();
        return format("Общее количество книг в БД: %d", count);
    }

    @ShellMethod(key = {"add-new-book-with-ids", "new-book-ids"},
            value = "Добавляет новую книгу в БД: укажите название книги, идентификатор жанра, идентификатор автора")
    public String handleAddBookWithIds(String title, long genreId, long authorId) {
        var book = bookService.addBook(title, genreId, authorId);
        return format("Добавлена новая книга: %s", converter.stringify(book));
    }

    @ShellMethod(key = {"add-new-book-with-names", "new-book-names"},
            value = "Добавляет новую книгу в БД: укажите название книги, жанр, имя автора")
    public String handleAddBookWithNames(String title, String genreName, String authorName) {
        var book = bookService.addBook(title, genreName, authorName);
        return format("Добавлена новая книга: %s", converter.stringify(book));
    }

    @ShellMethod(key = {"change-book-with-ids", "change-book-ids"},
            value = "Изменяет существующую в БД книгу: укажите идентификатор книги, название книги, " +
                    "идентификатор жанра, идентификатор автора")
    public String handleChangeBookWithIds(long id, String title, long genreId, long authorId) {
        var book = bookService.changeBook(id, title, genreId, authorId);
        return format("Книга изменена: %s", converter.stringify(book));
    }

    @ShellMethod(key = {"change-book-with-names", "change-book-names"},
            value = "Изменяет существующую в БД книгу: укажите идентификатор книги, название книги, жанр, имя автора")
    public String handleChangeBookWithNames(long id, String title, String genreName, String authorName) {
        var book = bookService.changeBook(id, title, genreName, authorName);
        return format("Книга изменена: %s", converter.stringify(book));
    }

    @ShellMethod(key = {"find-book-by-id", "book-by-id"},
            value = "Ищет книгу в БД по ее идентификатору: укажите идентификатор книги")
    public String handleGetBookById(long id) {
        var book = bookService.getBookById(id);
        return format("Книга найдена: %s", converter.stringify(book));
    }

    @ShellMethod(key = {"show-all-books", "all-books"},
            value = "Выводит список всех книг в БД")
    public String handleGetAllBooks() {
        var books = bookService.getAllBooks();
        return books.stream().map(converter::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"show-books-by-genre", "books-by-genre"},
            value = "Выводит список всех книг определенного жанра: укажите жанр")
    public String handleGetAllBooksByGenre(String genreName) {
        var books = bookService.getAllBooksByGenre(genreName);
        return books.stream().map(converter::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"show-books-by-author", "books-by-author"},
            value = "Выводит список всех книг определенного автора: укажите имя автора")
    public String handleGetAllBooksByAuthor(String authorName) {
        var books = bookService.getAllBooksByAuthor(authorName);
        return books.stream().map(converter::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"delete-book-from-db", "delete-book"},
            value = "Удаляет книгу из БД по ее идентификатору: укажите идентификатор книги")
    public String handleDeleteBookById(long id) {
        var book = bookService.deleteBookById(id);
        return format("Книга удалена: %s", converter.stringify(book));
    }

    @ShellMethod(key = {"delete-all-books-from-db", "delete-all-books"},
            value = "Удаляет все книги из БД")
    public String handleDeleteAllBooks() {
        var count = bookService.deleteAllBooks();
        return format("%d книг удалено", count);
    }
}
