package ru.fedbon.stringifier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.fedbon.domain.Answer;
import ru.fedbon.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = QuestionStringifierImpl.class)
class QuestionStringifierImplTest {

    @Autowired
    private QuestionStringifier questionStringifier;

    @Test
    @DisplayName("Корректно конвертирует вопрос в требуемом строковом представлении")
    void shouldReturnFormattedQuestionString() {
        // given
        var correctAnswer = new Answer("Option 1", true);
        var incorrectAnswer1 = new Answer("Option 2", false);
        var incorrectAnswer2 = new Answer("Option 3", false);

        var question = new Question("Question 1?",
                List.of(correctAnswer, incorrectAnswer1, incorrectAnswer2));

        // when
        var formattedQuestion = questionStringifier.stringify(question);

        // then
        var expectedOutput = "Question 1?\n" +
                "   1. Option 1\n" +
                "   2. Option 2\n" +
                "   3. Option 3\n";

        assertEquals(expectedOutput, formattedQuestion);
    }
}

