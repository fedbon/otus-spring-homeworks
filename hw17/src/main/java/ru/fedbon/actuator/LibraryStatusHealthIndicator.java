package ru.fedbon.actuator;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.fedbon.service.BookService;


@Component
@AllArgsConstructor
public class LibraryStatusHealthIndicator implements HealthIndicator {

    private final BookService bookService;

    @Override
    public Health health() {
        try {
            var books = bookService.getAll(Sort.unsorted());
            boolean isEmpty = books.isEmpty();
            if (isEmpty) {
                return Health
                        .status(Status.DOWN)
                        .withDetail("message", "В библиотеке нет книг, проверьте, все ли в порядке!")
                        .build();
            } else {
                return Health
                        .status(Status.UP)
                        .withDetail("message", "Все в порядке")
                        .build();
            }
        } catch (Exception e) {
            return Health
                    .status(Status.DOWN)
                    .withDetail("message", "Ошибка при получении данных из базы данных: " + e.getMessage())
                    .build();
        }
    }
}
