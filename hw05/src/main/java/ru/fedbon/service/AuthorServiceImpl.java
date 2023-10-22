package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.model.Author;
import ru.fedbon.repository.AuthorRepository;

import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public long getCount() {
        return authorRepository.count();
    }

    @Override
    public Author add(String authorName) {
        var author = new Author();
        author.setName(authorName);
        authorRepository.insert(author);
        return author;
    }

    @Override
    public void change(Author author) {
        authorRepository.findById(author.getId())
                .orElseThrow(() ->
                        new NotFoundException(format("Не найден автор с идентификатором %d", author.getId())));
        author.setName(author.getName());
        authorRepository.update(author);
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public long deleteAll() {
        return authorRepository.deleteAll();
    }

}
