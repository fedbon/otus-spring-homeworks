package ru.fedbon.dao;

import ru.fedbon.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
