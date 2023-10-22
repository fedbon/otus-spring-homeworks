package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.exception.AuthorNotFoundException;
import ru.fedbon.model.Author;
import ru.fedbon.repository.AuthorRepository;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @Override
    public long getAuthorsCount() {
        return authorRepository.count();
    }

    @Transactional
    @Override
    public void addAuthor(String authorName) {
        var author = new Author();
        author.setName(authorName);
        authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author changeAuthor(long id, String authorName) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(format("Не найден автор с идентификатором %d", id)));
        author.setName(authorName);
        authorRepository.update(author);
        return author;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(format("Не найден автор с идентификатором %d", id)));
    }

    @Transactional
    @Override
    public void deleteAuthorById(long id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    @Override
    public long deleteAllAuthors() {
        return authorRepository.deleteAll();
    }

}
