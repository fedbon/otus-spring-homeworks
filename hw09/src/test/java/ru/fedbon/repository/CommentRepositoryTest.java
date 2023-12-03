package ru.fedbon.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.fedbon.model.Book;
import ru.fedbon.model.Comment;
import ru.fedbon.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Тест BookCommentRepositoryJpa должен")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CommentRepositoryTest {

    private static final String NEW_COMMENT_TEXT = "Новый комментарий";

    private static final String UPDATED_COMMENT_TEXT = "Обновленный комментарий";

    private static final long TEST_BOOK_ID = 1;

    private static final long EXISTING_COMMENT_ID = 1;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("добавлять комментарий в БД")
    @Test
    void shouldInsertBookComment() {
        var expectedComment = new Comment();
        expectedComment.setText(NEW_COMMENT_TEXT);
        var testBook = testEntityManager.find(Book.class, TEST_BOOK_ID);
        expectedComment.setBook(testBook);

        commentRepository.save(expectedComment);
        assertThat(expectedComment.getId()).isGreaterThan(0);

        var actualComment = testEntityManager.find(Comment.class, expectedComment.getId());

        assertThat(actualComment)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedComment);

        assertThat(actualComment)
                .matches(comment -> comment.getId() == 4)
                .matches(comment -> comment.getText().equals(NEW_COMMENT_TEXT))
                .matches(comment -> comment.getBook() != null
                        && comment.getBook().getId() == TEST_BOOK_ID);
    }

    @DisplayName("изменять имеющийся в БД комментарий без отключения объекта комментария от контекста")
    @Test
    void shouldUpdateBookComment() {
        var comment = testEntityManager.find(Comment.class, EXISTING_COMMENT_ID);
        comment.setText(UPDATED_COMMENT_TEXT);
        testEntityManager.flush();
        var updatedComment = testEntityManager.find(Comment.class, EXISTING_COMMENT_ID);

        assertThat(updatedComment.getText()).isEqualTo(UPDATED_COMMENT_TEXT);
    }

    @Test
    @DisplayName("изменять имеющийся в БД комментарий при отключении объекта комментария от контекста")
    void shouldUpdateCommentWithDetaching() {
        var comment = testEntityManager.find(Comment.class, EXISTING_COMMENT_ID);
        testEntityManager.detach(comment);
        comment.setText(UPDATED_COMMENT_TEXT);
        commentRepository.save(comment);
        var updatedComment = testEntityManager.find(Comment.class, EXISTING_COMMENT_ID);

        assertThat(updatedComment.getText()).isEqualTo(UPDATED_COMMENT_TEXT);
    }

    @DisplayName("возвращать ожидаемый комментарий по идентификатору")
    @Test
    void shouldReturnExpectedBookCommentById() {
        var optionalActualComment = commentRepository.findById(EXISTING_COMMENT_ID);
        var expectedComment = testEntityManager.find(Comment.class, EXISTING_COMMENT_ID);

        assertThat(optionalActualComment)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("возвращать все комментарии для определенной книги")
    void shouldReturnAllCommentsForSomeBook() {
        var testBook = testEntityManager.find(Book.class, 2L);
        var comments = commentRepository.findAllByBook(testBook);

        assertThat(comments).isNotNull().hasSize(2)
                .allMatch(comment -> !comment.getText().isEmpty())
                .allMatch(comment -> comment.getBook() != null)
                .containsOnlyOnce(testEntityManager.find(Comment.class, 2L))
                .containsOnlyOnce(testEntityManager.find(Comment.class, 3L))
                .doesNotContain(testEntityManager.find(Comment.class, 1L));
    }

    @DisplayName("удалять заданный комментарий по его идентификатору")
    @Test
    void shouldDeleteBookCommentById() {
        var existingComment = testEntityManager.find(Genre.class, EXISTING_COMMENT_ID);
        assertThat(existingComment).isNotNull();

        commentRepository.deleteById(EXISTING_COMMENT_ID);

        var deletedComment = testEntityManager.find(Comment.class, EXISTING_COMMENT_ID);
        assertThat(deletedComment).isNull();
    }
}