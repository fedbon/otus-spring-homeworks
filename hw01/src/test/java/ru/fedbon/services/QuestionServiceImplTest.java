package ru.fedbon.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.fedbon.services.api.Parser;

import java.security.InvalidParameterException;
import java.util.List;

class QuestionServiceImplTest {
    private final Parser mock=Mockito.mock(Parser.class);

    @Test
    void getAllQuestions() {
        Mockito.when(mock.parse()).thenReturn(List.of(List.of("Question", "Option1", "Option2"),List.of("Question2", "Option1", "Option2")));
        var result = new QuestionServiceImpl(mock).getAllQuestions();
        var question = result.get(0);
        Assertions.assertEquals("Question",question.getQuestion());
        Assertions.assertEquals(2,question.getOptions().size());
        var question2 = result.get(1);
        Assertions.assertEquals("Question2",question2.getQuestion());
        Assertions.assertEquals(2,question2.getOptions().size());
    }
    @Test
    void getAllQuestionsWithInvalidStringsCount(){
        Mockito.when(mock.parse()).thenReturn(List.of(List.of("Question", "Option")));
        Assertions.assertThrows(InvalidParameterException.class,()->new QuestionServiceImpl(mock).getAllQuestions());

        Mockito.when(mock.parse()).thenReturn(List.of(List.of("Question")));
        Assertions.assertThrows(InvalidParameterException.class,()->new QuestionServiceImpl(mock).getAllQuestions());

        Mockito.when(mock.parse()).thenReturn(List.of(List.of()));
        Assertions.assertThrows(InvalidParameterException.class,()->new QuestionServiceImpl(mock).getAllQuestions());
    }
}