package ru.edu.module13.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.edu.module13.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @Test
    @DisplayName("Test for GET-endpoint delete user")
    public void deleteUserPage() throws Exception {
        mockMvc.perform(get("/admin/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("deleteUser"));
    }

    @Test
    @DisplayName("Test for GET-endpoint change user")
    public void changeUserPage() throws Exception {
        mockMvc.perform(get("/admin/change"))
                .andExpect(status().isOk())
                .andExpect(view().name("changeUser"));
    }


    @Test
    @DisplayName("Test for POST-endpoint deleteing user")
    public void deleteUsers() throws Exception {
        mockMvc.perform(post("/admin/delete")
                        .queryParam("name", "vasya")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @Test
    @DisplayName("Test for POST-endpoint changing user data")
    public void changeUsers() throws Exception {

        mockMvc.perform(post("/admin/change")
                        .queryParam("oldName", "vasya")
                        .queryParam("newName", "vasya")
                        .queryParam("newAge", "27")
                )
                .andExpect(status().isOk());
    }

}
