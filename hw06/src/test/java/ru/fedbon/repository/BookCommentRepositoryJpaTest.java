package ru.fedbon.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.fedbon.model.Book;
import ru.fedbon.model.BookComment;
import ru.fedbon.model.Genre;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DisplayName("Тест BookCommentRepositoryJpa должен")
@DataJpaTest
@Import(value = {BookCommentRepositoryJpa.class})
class BookCommentRepositoryJpaTest {

    private static final String NEW_COMMENT_TEXT = "Новый комментарий";

    private static final String UPDATED_COMMENT_TEXT = "Обновленный комментарий";

    private static final long TEST_BOOK_ID = 1;

    private static final long EXISTING_COMMENT_ID = 1;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookCommentRepositoryJpa bookCommentRepositoryJpa;

    @DisplayName("добавлять комментарий в БД")
    @Test
    void shouldInsertBookComment() {
        var expectedComment = new BookComment();
        expectedComment.setText(NEW_COMMENT_TEXT);
        var testBook = testEntityManager.find(Book.class, TEST_BOOK_ID);
        expectedComment.setBook(testBook);

        bookCommentRepositoryJpa.save(expectedComment);
        assertThat(expectedComment.getId()).isGreaterThan(0);

        var actualComment = testEntityManager.find(BookComment.class, expectedComment.getId());

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
        var comment = testEntityManager.find(BookComment.class, EXISTING_COMMENT_ID);
        comment.setText(UPDATED_COMMENT_TEXT);
        testEntityManager.flush();
        var updatedComment = testEntityManager.find(BookComment.class, EXISTING_COMMENT_ID);

        assertThat(updatedComment.getText()).isEqualTo(UPDATED_COMMENT_TEXT);
    }

    @DisplayName("возвращать ожидаемый комментарий по идентификатору")
    @Test
    void shouldReturnExpectedBookCommentById() {
        var optionalActualComment = bookCommentRepositoryJpa.findById(EXISTING_COMMENT_ID);
        var expectedComment = testEntityManager.find(BookComment.class, EXISTING_COMMENT_ID);

        assertThat(optionalActualComment)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedComment);
    }

    @DisplayName("удалять заданный комментарий по его идентификатору")
    @Test
    void shouldDeleteBookCommentById() {
        var existingComment = testEntityManager.find(Genre.class, EXISTING_COMMENT_ID);
        assertThat(existingComment).isNotNull();

        bookCommentRepositoryJpa.deleteById(EXISTING_COMMENT_ID);

        var deletedComment = testEntityManager.find(BookComment.class, EXISTING_COMMENT_ID);
        assertThat(deletedComment).isNull();
    }
}