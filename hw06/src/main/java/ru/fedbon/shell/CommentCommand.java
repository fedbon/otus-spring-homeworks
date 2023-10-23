package ru.fedbon.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.fedbon.dto.CommentDto;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.repository.CommentRepository;
import ru.fedbon.service.CommentService;
import ru.fedbon.stringifier.CommentStringifier;

import java.util.stream.Collectors;

import static java.lang.String.format;


@ShellComponent
@RequiredArgsConstructor
public class CommentCommand {

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    private final CommentStringifier stringifier;

    @ShellMethod(key = {"add-new-book-comment-by-book-id", "new-book-comment-by-book-id"},
            value = "Добавляет новый комментарий к книге по ее идентификатору в БД: " +
                    "укажите идентификатор книги, укажите комментарий")
    public String handleAdd(long id, String text) {
        var bookCommentDto = new CommentDto();
        bookCommentDto.setText(text);
        bookCommentDto.setBookId(id);

        return format("Добавлен новый комментарий: %s",
                stringifier.stringify(commentService.add(bookCommentDto)));
    }

    @ShellMethod(key = {"change-book-comment-by-id"},
            value = "Изменяет существующий комментарий к книге по идентификатору в БД: " +
                    "укажите идентификатор комментария, укажите обновленный комментарий")
    public String handleChange(long id, String text) {
        var bookComment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Не найден комментарий с идентификатором %d", id)));

        var bookCommentDto = new CommentDto();
        bookCommentDto.setId(id);
        bookCommentDto.setText(text);
        bookCommentDto.setBookId(bookComment.getBook().getId());
        return format("Комментарий к книге изменен: %s",
                stringifier.stringify(commentService.change(bookCommentDto)));
    }

    @ShellMethod(key = {"get-book-comment-by-id", "book-comment-by-id"},
            value = "Ищет комментарий к книге в БД по его идентификатору: укажите идентификатор комментария")
    public String handleGetById(long id) {
        var bookComment = commentService.getById(id);
        return format("Комментарий найден: %s", stringifier.stringify(bookComment));
    }

    @ShellMethod(key = {"get-all-book-comments-by-book-id", "book-comments-by-book-id"},
            value = "Выводит список всех комментариев к книге по ее идентификатору в БД")
    public String handleGetAllByBookId(long id) {
        var bookComments = commentService.getAllByBookId(id);
        return bookComments.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"delete-book-comment-by-book-id", "delete-book-comment-by-book-id"},
            value = "Удаляет комментарий из БД по его идентификатору: укажите идентификатор комментария")
    public String handleDeleteById(long id) {
        commentService.deleteById(id);
        return format("Комментарий с id=%d удален", id);
    }
}
