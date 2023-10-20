package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fedbon.exception.AuthorNotFoundException;
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
        authorRepository.insert(author);
        return author;
    }

    @Override
    public void changeAuthor(long id, String authorName) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(format("Не найден автор с идентификатором %d", id)));
        author.setName(authorName);
        authorRepository.update(author);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public void deleteAuthorById(long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public long deleteAllAuthors() {
        return authorRepository.deleteAll();
    }

}
