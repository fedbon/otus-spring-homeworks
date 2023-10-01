package ru.fedbon.services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import ru.fedbon.services.api.Parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CsvParser implements Parser {
    private final String path;

    @Override
    public List<List<String>> parse() {
        var inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
        try {
            return this.parse(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private List<List<String>> parse(InputStream inputStream) throws FileNotFoundException {
        InputStreamReader reader1 = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        try (var reader = new CSVReader(reader1)) {
            return reader.readAll().stream().map(Arrays::asList).collect(Collectors.toList());
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
