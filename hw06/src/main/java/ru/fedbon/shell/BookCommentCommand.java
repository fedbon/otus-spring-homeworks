package ru.fedbon.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.fedbon.service.BookCommentService;
import ru.fedbon.stringifier.BookCommentStringifier;

import java.util.stream.Collectors;

import static java.lang.String.format;


@ShellComponent
@RequiredArgsConstructor
public class BookCommentCommand {

    private final BookCommentService bookCommentService;

    private final BookCommentStringifier stringifier;


    @ShellMethod(key = {"add-new-book-comment-by-book-id", "new-book-comment-by-book-id"},
            value = "Добавляет новый комментарий к книге по ее идентификатору в БД: " +
                    "укажите идентификатор книги, укажите комментарий")
    public String handleAddBookComment(long id, String text) {
        var bookComment = bookCommentService.addBookComment(id, text);
        return format("Добавлен новый комментарий: %s", stringifier.stringify(bookComment));
    }

    @ShellMethod(key = {"change-book-comment-by-book-id"},
            value = "Изменяет существующий комментарий к книге по ее идентификатору в БД: " +
                    "укажите идентификатор книги, укажите комментарий")
    public String handleChangeGenre(long id, String text) {
        bookCommentService.changeBookComment(id, text);
        return format("Комментарий к книге c id=%d изменен на: %s", id, text);
    }

    @ShellMethod(key = {"get-book-comment-by-id", "book-comment-by-id"},
            value = "Ищет комментарий к книге в БД по его идентификатору: укажите идентификатор комментария")
    public String handleGetBookCommentById(long id) {
        var bookComment = bookCommentService.getBookCommentById(id);
        return format("Комментарий найден: %s", stringifier.stringify(bookComment));
    }

    @ShellMethod(key = {"get-all-book-comments-by-book-id", "book-comments-by-book-id"},
            value = "Выводит список всех комментариев к книге по ее идентификатору в БД")
    public String handleGetAllCommentsByBookId(long id) {
        var bookComments = bookCommentService.getAllCommentsByBookId(id);
        return bookComments.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"delete-book-comment-by-book-id", "delete-book-comment-by-book-id"},
            value = "Удаляет комментарий из БД по его идентификатору: укажите идентификатор комментария")
    public String handleDeleteBookCommentById(long id) {
        bookCommentService.deleteBookCommentById(id);
        return format("Комментарий с id=%d удален", id);
    }
}
