package ru.fedbon.controller.pages;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("Тест BookPageController должен")
@WebMvcTest(controllers = {BookPageController.class})
class BookPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("отображать страницу со списком всех книг")
    @Test
    void shouldDisplayListAllBooksPage() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books"));
    }

    @DisplayName("отображать страницу со списком всех книг одного автора")
    @Test
    void shouldDisplayListBooksByAuthorPage() throws Exception {
        mockMvc.perform(get("/books-by-author"))
                .andExpect(status().isOk())
                .andExpect(view().name("books-by-author"));
    }

    @DisplayName("отображать страницу со списком всех книг одного жанра")
    @Test
    void shouldDisplayListBooksByGenrePage() throws Exception {
        mockMvc.perform(get("/books-by-genre"))
                .andExpect(status().isOk())
                .andExpect(view().name("books-by-genre"));
    }

    @DisplayName("отображать страницу с просмотра и редактирования информации о книге")
    @Test
    void shouldDisplaySpecificBookPage() throws Exception {
        mockMvc.perform(get("/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }
}