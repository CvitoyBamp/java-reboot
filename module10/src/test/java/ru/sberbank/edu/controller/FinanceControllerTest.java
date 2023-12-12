package ru.sberbank.edu.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest
public class FinanceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Test for GET-endpoint")
    void testMainPage() throws Exception {
        mockMvc.perform(get("/finance"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"));
    }

    @Test
    @DisplayName("Test for POST-endpoint with correct params")
    void testFinancialPage() throws Exception {
        mockMvc.perform(post("/finance")
                        .queryParam("amount", "60000")
                        .queryParam("proc", "5")
                        .queryParam("year", "2")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("finance"));
    }

    @Test
    @DisplayName("Test for POST-endpoint with sum < 50000")
    void testFinancialPageLowerSum() throws Exception {
        mockMvc.perform(post("/finance")
                        .queryParam("amount", "40000")
                        .queryParam("proc", "5")
                        .queryParam("year", "2")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("errorMoney"));
    }

    @Test
    @DisplayName("Test for POST-endpoint with incorrect values (String not Int)")
    void testFinancialPageIncorrectValues() throws Exception {
        mockMvc.perform(post("/finance")
                        .queryParam("amount", "50000")
                        .queryParam("proc", "five")
                        .queryParam("year", "2")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("errorCustom"));
    }

}
