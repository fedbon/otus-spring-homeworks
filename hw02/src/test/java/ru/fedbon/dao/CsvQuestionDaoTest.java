package ru.fedbon.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CsvQuestionDaoTest {

    @Test
    @DisplayName("Корректно парсит данные из CSV файла")
    void testFindAll() {
        // when
        var questions = new CsvQuestionDao("test.questions.csv").findAll();

        // then
        assertEquals(2, questions.size());

        assertEquals("Question 1", questions.get(0).getText());
        assertEquals(2, questions.get(0).getOptions().size());
        assertEquals("Option 1", questions.get(0).getOptions().get(0).getText());
        assertTrue(questions.get(0).getOptions().get(0).isCorrect());
        assertEquals("Option 2", questions.get(0).getOptions().get(1).getText());
        assertFalse(questions.get(0).getOptions().get(1).isCorrect());

        assertEquals("Question 2", questions.get(1).getText());
        assertEquals(2, questions.get(1).getOptions().size());
        assertEquals("Option 1", questions.get(1).getOptions().get(0).getText());
        assertTrue(questions.get(1).getOptions().get(0).isCorrect());
        assertEquals("Option 2", questions.get(1).getOptions().get(1).getText());
        assertFalse(questions.get(1).getOptions().get(1).isCorrect());
    }
}




