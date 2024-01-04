package ru.fedbon.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorPageController {

    @GetMapping(value = "/authors")
    public String listAllAuthorsPage() {
        return "authors";
    }
}
