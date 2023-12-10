package ru.fedbon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.fedbon.service.AuthorService;


@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public String listAllAuthorsPage(Model model) {
        var authors = authorService.getAll(Sort.by(Sort.Direction.ASC,"id"));
        model.addAttribute("authors", authors);
        return "authors";
    }
}
