package ru.fedbon.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.fedbon.domain.Question;
import java.security.InvalidParameterException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CsvQuestionDaoTest {

    private CsvQuestionDao csvQuestionDao;

    @Mock
    private ClassLoader classLoader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        csvQuestionDao = new CsvQuestionDao("questions.csv");
    }

    @Test
    void testGetAll() {
        // given
        var inputStream = getClass().getResourceAsStream("/questions.csv");
        when(classLoader.getResourceAsStream("questions.csv")).thenReturn(inputStream);
        csvQuestionDao = new CsvQuestionDao("questions.csv");

        // when
        List<Question> questions = csvQuestionDao.getAll();

        // then
        assertNotNull(questions);
        assertFalse(questions.isEmpty());

        for (Question question : questions) {
            assertNotNull(question.getText());
            assertNotNull(question.getOptions());
            assertFalse(question.getOptions().isEmpty());
        }
    }
}
