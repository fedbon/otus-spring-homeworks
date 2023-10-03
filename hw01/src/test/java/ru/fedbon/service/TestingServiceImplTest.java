package ru.fedbon.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.fedbon.dao.QuestionDao;
import ru.fedbon.domain.Answer;
import ru.fedbon.domain.Question;
import ru.fedbon.utils.Stringifier;
import java.util.Arrays;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class TestingServiceImplTest {

    @Mock
    private QuestionDao csvQuestionDao;

    @Mock
    private Stringifier<Question> stringifier;

    private TestingServiceImpl testingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testingService = new TestingServiceImpl(csvQuestionDao, stringifier);
    }

    @Test
    void testExecuteTesting() {
        // given
        var mockQuestions = Arrays.asList(
                new Question("Question 1", Arrays.asList(new Answer("Option 1", false),
                        new Answer("Option 2", true))),
                new Question("Question 2", Arrays.asList(new Answer("Option 1", true),
                        new Answer("Option 2", false)))
        );

        var mockStrings = Arrays.asList("Question 1\n Option 1\n Option 2", "Question 2\n Option 1\n Option 2");

        when(csvQuestionDao.getAll()).thenReturn(mockQuestions);
        when(stringifier.stringify(mockQuestions)).thenReturn(mockStrings);

        // when
        testingService.executeTesting();

        // then
        verify(csvQuestionDao, times(1)).getAll();
        verify(stringifier, times(1)).stringify(mockQuestions);
    }
}