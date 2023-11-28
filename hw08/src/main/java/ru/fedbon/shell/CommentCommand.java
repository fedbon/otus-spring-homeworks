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

    @ShellMethod(key = {"new-comment-by-book-id"},
            value = "Добавляет новый комментарий к книге по ее идентификатору в БД: " +
                    "укажите идентификатор книги, укажите комментарий")
    public String handleAdd(String id, String text) {
        var bookCommentDto = new CommentDto();
        bookCommentDto.setText(text);
        bookCommentDto.setBookId(id);

        return format("Добавлен новый комментарий: %s",
                stringifier.stringify(commentService.create(bookCommentDto)));
    }

    @ShellMethod(key = {"change-comment-by-id"},
            value = "Изменяет существующий комментарий к книге по идентификатору в БД: " +
                    "укажите идентификатор комментария, укажите обновленный комментарий")
    public String handleChange(String id, String text) {
        var bookComment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Не найден комментарий с идентификатором %s", id)));

        var bookCommentDto = new CommentDto();
        bookCommentDto.setId(id);
        bookCommentDto.setText(text);
        bookCommentDto.setBookId(bookComment.getBook().getId());
        return format("Комментарий к книге изменен: %s",
                stringifier.stringify(commentService.update(bookCommentDto)));
    }

    @ShellMethod(key = {"comment-by-id"},
            value = "Ищет комментарий к книге в БД по его идентификатору: укажите идентификатор комментария")
    public String handleGetById(String id) {
        var bookComment = commentService.getById(id);
        return format("Комментарий найден: %s", stringifier.stringify(bookComment));
    }

    @ShellMethod(key = {"comments-by-book-id"},
            value = "Выводит список всех комментариев к книге по ее идентификатору в БД")
    public String handleGetAllByBookId(String id) {
        var bookComments = commentService.getAllByBookId(id);
        return bookComments.stream().map(stringifier::stringify).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = {"delete-comment-by-book-id"},
            value = "Удаляет комментарий из БД по его идентификатору: укажите идентификатор комментария")
    public String handleDeleteById(String id) {
        commentService.deleteById(id);
        return format("Комментарий с id=%s удален", id);
    }
}
