package ru.fedbon.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import ru.fedbon.domain.Answer;
import ru.fedbon.domain.Question;
import ru.fedbon.exception.AppException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final String path;

    @Override
    public List<Question> getAll() {
        var inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + path);
        }
        try (var reader1 = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             var reader = new CSVReader(reader1)) {

            List<List<String>> data = reader.readAll().stream().map(Arrays::asList).toList();
            return data.stream().map(this::createQuestion).toList();

        } catch (IOException | CsvException e) {
            throw new AppException(e);
        }
    }

    private Question createQuestion(List<String> list) {
        if (list.size() < 3) {
            throw new InvalidParameterException("Not enough strings to create instance of Question");
        }
        var answer = new Answer(list.get(1), true);
        var options = new ArrayList<>(list.subList(2, list.size()).stream().
                map(str -> new Answer(str, false))
                .toList());
        options.add(answer);
        Collections.shuffle(options);
        return new Question(list.get(0), options);
    }
}
