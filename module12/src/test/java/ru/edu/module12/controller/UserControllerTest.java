package ru.edu.module12.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.edu.module12.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = UsersController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @Test
    @DisplayName("Test for GET-endpoint getting all users from db")
    public void allUsersPage() throws Exception {
        mockMvc.perform(get("/user/getAll"))
                .andExpect(status().isOk())
                .andExpect(view().name("allUsers"));
    }

    @Test
    @DisplayName("Test for GET-endpoint add user")
    public void addUserPage() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addUser"));
    }

    @Test
    @DisplayName("Test for POST-endpoint of getting users")
    public void getAllUsersPage() throws Exception {
        mockMvc.perform(post("/user/getAll")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("showAllUsers"));
    }

    @Test
    @DisplayName("Test for POST-endpoint adding user")
    public void addUser() throws Exception {
        mockMvc.perform(post("/user/add")
                        .queryParam("name", "vasya")
                        .queryParam("age", "25")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("showAddUsers"));
    }

    @Test
    @DisplayName("Test for POST-endpoint adding user error param")
    public void addUserError() throws Exception {
        mockMvc.perform(post("/user/add")
                        .queryParam("name", "vasya")
                        .queryParam("age", "five")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @Test
    @DisplayName("Test for POST-endpoint getting all users")
    public void getAllUsers() throws Exception {
        mockMvc.perform(post("/user/getAll")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("showAllUsers"));
    }

}
