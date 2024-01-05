package ru.fedbon.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.fedbon.client.dto.BookCreateDto;
import ru.fedbon.client.dto.BookDto;
import ru.fedbon.client.dto.BookUpdateDto;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;
import java.util.List;



@Slf4j
@Component
@RequiredArgsConstructor
public class BookServiceClient {

    private final RestTemplate restTemplate;

    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

    public BookDto create(BookCreateDto bookCreateDto) {
        log.debug("Sending request to create a book");

        try {
            ResponseEntity<BookDto> responseEntity = circuitBreakerFactory.create("createBook")
                    .run(() -> restTemplate.postForEntity("http://bookstore-service/api/books",
                                    bookCreateDto, BookDto.class),
                            throwable -> new ResponseEntity<>(fallbackCreate(), HttpStatus.OK));

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                log.debug("Fallback triggered: Received non-success status code. Returning fallback data.");
                return fallbackCreate();
            }
        } catch (RestClientException ex) {
            log.debug("Fallback triggered: Exception occurred while calling the service. Returning fallback data.", ex);
            return fallbackCreate();
        }
    }

    public BookDto update(BookUpdateDto bookUpdateDto) {
        log.debug("Sending request to update a book");

        try {
            ResponseEntity<BookDto> responseEntity = circuitBreakerFactory.create("updateBook")
                    .run(() -> restTemplate.exchange("http://bookstore-service/api/books/{id}", HttpMethod.PUT,
                                    new HttpEntity<>(bookUpdateDto), BookDto.class, bookUpdateDto.getId()),
                            throwable -> new ResponseEntity<>(fallbackUpdate(), HttpStatus.OK));

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                log.debug("Fallback triggered: Received non-success status code. Returning fallback data.");
                return fallbackUpdate();
            }
        } catch (RestClientException ex) {
            log.debug("Fallback triggered: Exception occurred while calling the service. Returning fallback data.", ex);
            return fallbackUpdate();
        }
    }

    public List<BookDto> getAll(Sort sort) {
        log.debug("Sending request to retrieve all books");

        try {
            ParameterizedTypeReference<List<BookDto>> responseType =
                    new ParameterizedTypeReference<>() {
                    };

            ResponseEntity<List<BookDto>> responseEntity = circuitBreakerFactory.create("getAllBooks")
                    .run(() -> restTemplate.exchange("http://bookstore-service/api/books", HttpMethod.GET,
                                    null, responseType),
                            throwable -> new ResponseEntity<>(fallbackGetAll(), HttpStatus.OK));

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                log.debug("Fallback triggered: Received non-success status code. Returning fallback data.");
                return fallbackGetAll();
            }
        } catch (RestClientException ex) {
            log.debug("Fallback triggered: Exception occurred while calling the service. Returning fallback data.", ex);
            return fallbackGetAll();
        }
    }

    public BookDto getById(long id) {
        log.debug("Sending request to retrieve a book by ID: {}", id);

        try {
            ResponseEntity<BookDto> responseEntity = circuitBreakerFactory.create("getBookById")
                    .run(() -> restTemplate.getForEntity("http://bookstore-service/api/books/{id}", BookDto.class, id),
                            throwable -> new ResponseEntity<>(fallbackGetById(id), HttpStatus.OK));

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                log.debug("Fallback triggered: Received non-success status code. Returning fallback data.");
                return fallbackGetById(id);
            }
        } catch (RestClientException ex) {
            log.debug("Fallback triggered: Exception occurred while calling the service. Returning fallback data.", ex);
            return fallbackGetById(id);
        }
    }

    public List<BookDto> getAllByGenreId(long genreId) {
        log.debug("Sending request to retrieve all books by Genre ID: {}", genreId);

        try {
            ParameterizedTypeReference<List<BookDto>> responseType =
                    new ParameterizedTypeReference<>() {
                    };

            ResponseEntity<List<BookDto>> responseEntity = circuitBreakerFactory.create("getAllByGenreId")
                    .run(() -> restTemplate.exchange("http://bookstore-service/api/books?genreId={genreId}",
                                    HttpMethod.GET, null, responseType, genreId),
                            throwable -> new ResponseEntity<>(fallbackGetAllByGenreId(genreId), HttpStatus.OK));

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                log.debug("Fallback triggered: Received non-success status code. Returning fallback data.");
                return fallbackGetAllByGenreId(genreId);
            }
        } catch (RestClientException ex) {
            log.debug("Fallback triggered: Exception occurred while calling the service. Returning fallback data.", ex);
            return fallbackGetAllByGenreId(genreId);
        }
    }

    public List<BookDto> getAllByAuthorId(long authorId) {
        log.debug("Sending request to retrieve all books by Author ID: {}", authorId);

        try {
            ParameterizedTypeReference<List<BookDto>> responseType =
                    new ParameterizedTypeReference<>() {
                    };

            ResponseEntity<List<BookDto>> responseEntity = circuitBreakerFactory.create("getAllByAuthorId")
                    .run(() -> restTemplate.exchange("http://bookstore-service/api/books?authorId={authorId}",
                                    HttpMethod.GET, null, responseType, authorId),
                            throwable -> new ResponseEntity<>(fallbackGetAllByAuthorId(authorId), HttpStatus.OK));

            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                log.debug("Fallback triggered: Received non-success status code. Returning fallback data.");
                return fallbackGetAllByAuthorId(authorId);
            }
        } catch (RestClientException ex) {
            log.debug("Fallback triggered: Exception occurred while calling the service. Returning fallback data.", ex);
            return fallbackGetAllByAuthorId(authorId);
        }
    }

    public void deleteById(long id) {
        log.debug("Sending request to delete a book by ID: {}", id);

        try {
            circuitBreakerFactory.create("deleteBookById")
                    .run(() -> {
                        restTemplate.delete("http://bookstore-service/api/books/{id}", id);
                        return null;
                    });
        } catch (RestClientException ex) {
            log.debug("Fallback triggered: Exception occurred while calling the service.", ex);
            fallbackDeleteById(id);
        }
    }

    public void deleteAll() {
        log.debug("Sending request to delete all books");

        try {
            circuitBreakerFactory.create("deleteAllBooks")
                    .run(() -> {
                        restTemplate.delete("http://bookstore-service/api/books");
                        return null;
                    });
        } catch (RestClientException ex) {
            log.debug("Fallback triggered: Exception occurred while calling the service.", ex);
            fallbackDeleteAll();
        }
    }

    public BookDto fallbackCreate() {
        log.debug("Fallback triggered: Unable to create a book. Returning a default book.");
        return new BookDto();
    }

    public BookDto fallbackUpdate() {
        log.debug("Fallback triggered: Unable to update the book. Returning a default book.");
        return new BookDto();
    }

    public List<BookDto> fallbackGetAll() {
        log.debug("Fallback triggered: Unable to retrieve all books. Returning an empty list.");
        return Collections.emptyList();
    }

    public BookDto fallbackGetById(long id) {
        log.debug("Fallback triggered: Unable to retrieve the book with ID {}. Returning a default book.", id);
        return new BookDto();
    }

    public List<BookDto> fallbackGetAllByGenreId(long genreId) {
        log.debug("Fallback triggered: Unable to retrieve books for Genre ID {}. Returning an empty list.", genreId);
        return Collections.emptyList();
    }

    public List<BookDto> fallbackGetAllByAuthorId(long authorId) {
        log.debug("Fallback triggered: Unable to retrieve books for Author ID {}. Returning an empty list.", authorId);
        return Collections.emptyList();
    }

    public void fallbackDeleteById(long id) {
        log.debug("Fallback triggered: Unable to delete the book with ID {}.", id);
    }

    public void fallbackDeleteAll() {
        log.debug("Fallback triggered: Unable to delete all books.");
    }
}
