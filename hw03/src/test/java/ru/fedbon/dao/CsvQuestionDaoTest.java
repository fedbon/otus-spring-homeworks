package ru.fedbon.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import ru.fedbon.config.LocaleProvider;
import ru.fedbon.config.LocalizedResourceNameProvider;
import ru.fedbon.config.ScoreToPassProvider;
import ru.fedbon.domain.Answer;
import ru.fedbon.domain.Question;
import ru.fedbon.service.impl.QuestionServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = CsvQuestionDao.class)
class CsvQuestionDaoTest {

    private static final String CSV_RESOURCE_NAME = "test-questions.csv";

    @MockBean
    private LocalizedResourceNameProvider resourceNameProvider;

    @MockBean
    private LocaleProvider localeProvider;

    @MockBean
    private ScoreToPassProvider propertiesProvider;

    @Autowired
    private CsvQuestionDao csvQuestionDao;

    @Test
    @DisplayName("Корректно парсит данные из CSV файла")
    void testFindAll() {
        // given
        var expectedQuestions = Arrays.asList(
                new Question("Question 1", Arrays.asList(new Answer("Option 1", true),
                        new Answer("Option 2", false))),
                new Question("Question 2", Arrays.asList(new Answer("Option 1", true),
                        new Answer("Option 2", false)))
        );

        given(resourceNameProvider.getLocalizedResourceName()).willReturn(CSV_RESOURCE_NAME);

        // then
        assertThat(csvQuestionDao.findAll())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedQuestions);
    }
}






