package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedbon.exception.NotFoundException;
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
    public long getCount() {
        return authorRepository.count();
    }

    @Transactional
    @Override
    public void add(String name) {
        var author = new Author();
        author.setName(name);
        authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author change(Author author) {
        authorRepository.findById(author.getId())
                .orElseThrow(() ->
                        new NotFoundException(format("Не найден автор с идентификатором %d", author.getId())));
        author.setName(author.getName());
        authorRepository.update(author);
        return author;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author getById(long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Не найден автор с идентификатором %d", id)));
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    @Override
    public long deleteAll() {
        return authorRepository.deleteAll();
    }

}
