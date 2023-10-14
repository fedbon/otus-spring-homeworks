package ru.fedbon.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.fedbon.dao.QuestionDao;
import ru.fedbon.domain.Answer;
import ru.fedbon.domain.Question;
import ru.fedbon.service.impl.QuestionServiceImpl;
import ru.fedbon.stringifier.QuestionStringifierImpl;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = QuestionServiceImpl.class)
class QuestionServiceImplTest {

    @MockBean
    private QuestionDao questionDao;

    @Autowired
    private QuestionServiceImpl questionService;

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

        given(questionDao.findAll()).willReturn(expectedQuestions);

        // then
        assertThat(questionService.getAllQuestions())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedQuestions);
    }

    @Test
    @DisplayName("Возвращает пустой список, если вопросов нет")
    void testGetAllQuestionsEmptyList() {
        // given
        when(questionDao.findAll()).thenReturn(List.of());

        // when
        var resultQuestions = questionService.getAllQuestions();

        // then
        assertEquals(0, resultQuestions.size());
        verify(questionDao, times(1)).findAll();
    }

    @Test
    @DisplayName("Корректно обрабатывает ситуацию, когда вопросы содержат пустые варианты ответов")
    void testGetAllQuestionsWithEmptyAnswers() {
        // given
        var questionsWithEmptyAnswers = Arrays.asList(
                new Question("Question 1", List.of()),
                new Question("Question 2", List.of())
        );
        when(questionDao.findAll()).thenReturn(questionsWithEmptyAnswers);

        // when
        var resultQuestions = questionService.getAllQuestions();

        // then
        assertEquals(2, resultQuestions.size());
        verify(questionDao, times(1)).findAll();
    }
}

