package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fedbon.model.Author;
import ru.fedbon.repository.AuthorRepository;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public long getAuthorsCount() {
        return authorRepository.count();
    }

    @Override
    public Author addAuthor(String authorName) {
        var author = new Author();
        author.setName(authorName);
        return authorRepository.insert(author);
    }

    @Override
    public Author changeAuthor(long id, String authorName) {
        var author = getAuthorById(id);
        author.setName(authorName);
        return authorRepository.update(author);
    }

    @Override
    public Author getAuthorById(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден автор с идентификатором %d", id)));
    }

    @Override
    public Author getAuthorByName(String authorName) {
        return authorRepository.findByName(authorName)
                .orElseThrow(() -> new IllegalArgumentException(format("Не найден автор с именем %s", authorName)));
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author deleteAuthorById(long id) {
        var author = getAuthorById(id);
        authorRepository.deleteById(id);
        return author;
    }

    @Override
    public long deleteAllAuthors() {
        return authorRepository.deleteAll();
    }

}
