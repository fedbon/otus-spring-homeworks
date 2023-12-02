package ru.fedbon.mongock.testchangelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Comment;
import ru.fedbon.model.Genre;

import java.util.List;


@ChangeLog(order = "001")
public class TestDatabaseChangelog {

    private final Author author1 = new Author("1", "Автор_01");

    private final Author author2 = new Author("2", "Автор_02");

    private final Author author3 = new Author("3", "Автор_03");

    private final Genre genre1 = new Genre("1", "Жанр_01");

    private final Genre genre2 = new Genre("2", "Жанр_02");

    private final Genre genre3 = new Genre("3", "Жанр_03");

    private final Book book1 = new Book("1", "Книга_01", genre1, author1);

    private final Book book2 = new Book("2", "Книга_02", genre2, author2);

    private final Book book3 = new Book("3", "Книга_03", genre3, author3);

    private final Book book4 = new Book("4", "Книга_04", genre2, author3);

    private final Comment comment1 = new Comment("1", "Комментарий 1 к Книге_01", book1);

    private final Comment comment2 = new Comment("2", "Комментарий 1 к Книге_02", book2);

    private final Comment comment3 = new Comment("3", "Комментарий 1 к Книге_03", book3);

    private final Comment comment4 = new Comment("4", "Комментарий 2 к Книге_03", book3);

    private final Comment comment5 = new Comment("5", "Комментарий 1 к Книге_04", book4);

    @ChangeSet(order = "000", id = "dropDB", author = "fedbon", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "insertAuthors", author = "fedbon")
    public void insertAuthors(MongockTemplate mongockTemplate) {
        mongockTemplate.insertAll(List.of(author1, author2, author3));
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "fedbon")
    public void insertGenres(MongockTemplate mongockTemplate) {
        mongockTemplate.insertAll(List.of(genre1, genre2, genre3));
    }

    @ChangeSet(order = "003", id = "insertBook", author = "fedbon")
    public void insertBooks(MongockTemplate mongockTemplate) {
        mongockTemplate.insertAll(List.of(book1, book2, book3, book4));
    }

    @ChangeSet(order = "004", id = "insertComment", author = "fedbon")
    public void insertComments(MongockTemplate mongockTemplate) {
        mongockTemplate.insertAll(List.of(comment1, comment2, comment3, comment4, comment5));
    }
}
