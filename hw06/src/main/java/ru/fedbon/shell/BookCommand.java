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
    public String handleGetBooksCount() {
        var count = bookService.getCount();
        return format("Общее количество книг в БД: %d", count);
    }

    @ShellMethod(key = {"add-new-book", "new-book"},
            value = "Добавляет новую книгу в БД: укажите название книги, идентификатор жанра, идентификатор автора")
    public String handleAddBook(String title, long genreId, long authorId) {
        var bookDto = new BookDto();
        bookDto.setTitle(title);
        bookDto.setGenreId(genreId);
        bookDto.setAuthorId(authorId);

        bookService.add(bookDto);

        return format("Добавлена новая книга c названием: %s", title);
    }

    @ShellMethod(key = {"change-book-by-id"},
            value = "Изменяет существующую в БД книгу: укажите идентификатор книги, название книги, " +
                    "идентификатор жанра, идентификатор автора")
    public String handleChangeBook(long id, String title, long genreId, long authorId) {
        var bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setTitle(title);
        bookDto.setGenreId(genreId);
        bookDto.setAuthorId(authorId);

        return format("Книга изменена: %s", stringifier.stringify(bookService.change(bookDto)));
    }

    @ShellMethod(key = {"get-all-books", "all-books"},
            value = "Выводит список всех книг в БД")
    public String handleGetAllBooks() {
        var books = bookService.getAll();
        return books.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"get-book-by-id", "book-by-id"},
            value = "Ищет книгу в БД по ее идентификатору: укажите идентификатор книги")
    public String handleGetBookById(long id) {
        var book = bookService.getById(id);
        return format("Книга найдена: %s", stringifier.stringify(book));
    }

    @ShellMethod(key = {"books-by-genre-id"},
            value = "Выводит список всех книг определенного жанра: укажите идентификатор жанра")
    public String handleGetAllBooksByGenre(long id) {
        var books = bookService.getAllByGenreId(id);
        return books.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"books-by-author-id"},
            value = "Выводит список всех книг определенного автора: укажите идентификатор автора")
    public String handleGetAllBooksByAuthor(long id) {
        var books = bookService.getAllByAuthorId(id);
        return books.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"delete-book-by-id"},
            value = "Удаляет книгу из БД по ее идентификатору: укажите идентификатор книги")
    public String handleDeleteBookById(long id) {
        bookService.deleteById(id);
        return format("Книга c id=%d удалена", id);
    }

    @ShellMethod(key = {"delete-all-books"},
            value = "Удаляет все книги из БД")
    public String handleDeleteAllBooks() {
        var count = bookService.deleteAll();
        return format("%d книг удалено", count);
    }
}
