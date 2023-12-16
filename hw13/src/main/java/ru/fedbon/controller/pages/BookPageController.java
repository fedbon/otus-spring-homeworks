package ru.fedbon.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookPageController {

    @GetMapping(value = "/books")
    public String listAllBooksPage() {
        return "books";
    }

    @GetMapping(value = "/books-by-author")
    public String listBooksByAuthorPage() {
        return "books-by-author";
    }

    @GetMapping(value = "/books-by-genre")
    public String listBooksByGenrePage() {
        return "books-by-genre";
    }

    @GetMapping(value = "/edit")
    public String listUpdateBookPage() {
        return "edit";
    }
}
