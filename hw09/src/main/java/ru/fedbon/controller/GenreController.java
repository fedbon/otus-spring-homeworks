package ru.fedbon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.fedbon.service.GenreService;



@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public String listAllGenresPage(Model model) {
        var genres = genreService.getAll(Sort.by(Sort.Direction.ASC,"id"));
        model.addAttribute("genres", genres);
        return "genres";
    }
}
