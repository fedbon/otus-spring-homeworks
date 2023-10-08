package ru.fedbon.dao;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.fedbon.domain.Answer;
import ru.fedbon.domain.Question;
import ru.fedbon.exception.AppException;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CsvQuestionDao implements QuestionDao {

    private static final String IS_TRUE_FLAG = "true";

    private final String path;

    public CsvQuestionDao(@Value("${csv.resource.name}") String path) {
        this.path = path;
    }

    @Override
    public List<Question> findAll() {
        try (var reader = new CSVReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().
                getResourceAsStream(path), "File not found")))) {
            var content = reader.readAll();
            var questions = new ArrayList<Question>();
            for (var row : content) {
                var answers = new ArrayList<Answer>();
                for (int i = 1; i < row.length; i = i + 2) {
                    answers.add(new Answer(row[i], row[i + 1].equalsIgnoreCase(IS_TRUE_FLAG)));
                }
                questions.add(new Question(row[0], answers));
            }
            return questions;
        } catch (Exception e) {
            throw new AppException(e);
        }
    }
}
