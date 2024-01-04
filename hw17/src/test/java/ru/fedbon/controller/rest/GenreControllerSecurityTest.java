package ru.fedbon.controller.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.fedbon.security.SecurityConfig;
import ru.fedbon.security.UserDetailsServiceImpl;
import ru.fedbon.service.GenreService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Тест GenreControllerSecurityTest проверяет, что ")
@WebMvcTest(controllers = {GenreController.class})
@Import(SecurityConfig.class)
class GenreControllerSecurityTest {

    @MockBean
    private GenreService genreService;

    @MockBean
    private UserDetailsServiceImpl userService;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("доступ к странице всех жанров для авторизованного пользователя (ROLE_USER) должен вернуть код состояния 200")
    @Test
    @WithMockUser(roles = "USER")
    void testGetAllGenresAuthorizedAsUser() throws Exception {
        mockMvc.perform(get("/api/genres"))
                .andExpect(status().isOk());
    }

    @DisplayName("доступ к странице всех жанров для авторизованного пользователя с ролью ADMIN должен вернуть код состояния 200")
    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllGenresAuthorizedAsAdmin() throws Exception {
        mockMvc.perform(get("/api/genres"))
                .andExpect(status().isOk());
    }
}