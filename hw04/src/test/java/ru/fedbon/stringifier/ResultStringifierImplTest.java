package ru.fedbon.stringifier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.fedbon.domain.Result;
import ru.fedbon.domain.User;
import ru.fedbon.service.LocalizationMessageService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ResultStringifierImpl.class)
class ResultStringifierImplTest {

    @Autowired
    private ResultStringifier resultStringifier;

    @MockBean
    private LocalizationMessageService messageService;

    @Test
    @DisplayName("Корректно конвертирует результат теста в требуемом строковом представлении")
    void shouldReturnFormattedResultStringWhenPassed() {
        // given
        var result = new Result(new User("Max", "Payne"));
        result.setCorrectAnswers(5);
        result.setTotal(10);
        int scoreToPass = 5;

        // when
        when(messageService.getLocalizedMessage("test.result.pattern"))
                .thenReturn("Dear %s %s,\nTotal questions amount: %d. Right answers: %d, must be at least %d. %s");

        when(messageService.getLocalizedMessage("message.test.passed"))
                .thenReturn("\nTest passed!\nCongratulations!");

        // when
        var formattedResult = resultStringifier.stringify(result, scoreToPass);

        // then
        var expectedOutput = "Dear Max Payne,\nTotal questions amount: 10. Right answers: 5, must be at least 5. " +
                "\nTest passed!\nCongratulations!";
        assertEquals(expectedOutput, formattedResult);
    }
}
