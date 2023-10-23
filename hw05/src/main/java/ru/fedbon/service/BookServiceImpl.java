package ru.fedbon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fedbon.dto.BookDto;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.mapper.BookMapper;
import ru.fedbon.model.Book;
import ru.fedbon.repository.AuthorRepository;
import ru.fedbon.repository.BookRepository;
import ru.fedbon.repository.GenreRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;


    @Override
    public long getCount() {
        return bookRepository.count();
    }

    @Override
    public BookDto add(BookDto bookDto) {
        var genre = genreRepository.findById(bookDto.getGenreId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Не найден жанр с идентификатором %d", bookDto.getGenreId())
                ));

        var author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Не найден автор с идентификатором %d", bookDto.getAuthorId())
                ));

        var book = BookMapper.mapDtoToBook(bookDto, genre, author);
        bookRepository.insert(book);

        return bookDto;
    }

    public void change(BookDto bookDto) {
        var genre = genreRepository.findById(bookDto.getGenreId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Не найден жанр с идентификатором %d", bookDto.getGenreId())
                ));

        var author = authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Не найден автор с идентификатором %d", bookDto.getAuthorId())
                ));

        bookRepository.findById(bookDto.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Не найдена книга с идентификатором %d", bookDto.getId())
                ));

        var book = BookMapper.mapDtoToBook(bookDto, genre, author);
        bookRepository.update(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getAllByGenreName(String genreName) {
        return bookRepository.findAllByGenre(genreName);
    }

    @Override
    public List<Book> getAllByAuthorName(String authorName) {
        return bookRepository.findAllByAuthor(authorName);
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public long deleteAll() {
        return bookRepository.deleteAll();
    }
}
