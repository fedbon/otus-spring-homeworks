package ru.fedbon.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.fedbon.dao.QuestionDao;
import ru.fedbon.domain.Answer;
import ru.fedbon.domain.Question;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDaoMock;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Корректно возвращает список всех вопросов")
    void testGetAllQuestions() {
        // given
        var expectedQuestions = Arrays.asList(
                new Question("Question 1", Arrays.asList(new Answer("Option 1", true),
                        new Answer("Option 2", false))),
                new Question("Question 2", Arrays.asList(new Answer("Option 1", true),
                        new Answer("Option 2", false)))
        );

        // when
        when(questionDaoMock.findAll()).thenReturn(expectedQuestions);

        var resultQuestions = questionService.getAllQuestions();

        // then
        assertEquals(resultQuestions, expectedQuestions);
        verify(questionDaoMock, times(1)).findAll();
    }

    @Test
    @DisplayName("Возвращает пустой список, если вопросов нет")
    void testGetAllQuestionsEmptyList() {
        // given
        when(questionDaoMock.findAll()).thenReturn(List.of());

        // when
        var resultQuestions = questionService.getAllQuestions();

        // then
        assertEquals(0, resultQuestions.size());
        verify(questionDaoMock, times(1)).findAll();
    }

    @Test
    @DisplayName("Корректно обрабатывает ситуацию, когда вопросы содержат пустые варианты ответов")
    void testGetAllQuestionsWithEmptyAnswers() {
        // given
        var questionsWithEmptyAnswers = Arrays.asList(
                new Question("Question 1", List.of()),
                new Question("Question 2", List.of())
        );
        when(questionDaoMock.findAll()).thenReturn(questionsWithEmptyAnswers);

        // when
        var resultQuestions = questionService.getAllQuestions();

        // then
        assertEquals(2, resultQuestions.size());
        verify(questionDaoMock, times(1)).findAll();
    }
}

