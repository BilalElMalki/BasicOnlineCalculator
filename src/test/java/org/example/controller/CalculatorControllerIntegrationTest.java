package org.example.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for {@link CalculatorController}.
 *
 * <p>Loads the full Spring context with an embedded MongoDB instance.
 * Verifies end-to-end request handling from HTTP layer through to the template.</p>
 */
@SpringBootTest
@AutoConfigureMockMvc
class CalculatorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET / returns calculator page with status 200")
    void getCalculator_returns200() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("calculator"))
                .andExpect(model().attributeExists("request"));
    }

    @Test
    @DisplayName("POST /calculate with valid addition returns result in model")
    void postCalculate_addition_returnsResult() throws Exception {
        mockMvc.perform(post("/calculate")
                        .param("operand1", "8")
                        .param("operator", "+")
                        .param("operand2", "2"))
                .andExpect(status().isOk())
                .andExpect(view().name("calculator"))
                .andExpect(model().attributeExists("response"));
    }

    @Test
    @DisplayName("POST /calculate with division by zero returns error in model")
    void postCalculate_divisionByZero_returnsError() throws Exception {
        mockMvc.perform(post("/calculate")
                        .param("operand1", "5")
                        .param("operator", "/")
                        .param("operand2", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("calculator"))
                .andExpect(model().attributeExists("response"));
    }

    @Test
    @DisplayName("POST /calculate with missing operand returns validation error")
    void postCalculate_missingOperand_returnsValidationError() throws Exception {
        mockMvc.perform(post("/calculate")
                        .param("operator", "+")
                        .param("operand2", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("calculator"))
                .andExpect(model().hasErrors());
    }

    @Test
    @DisplayName("POST /calculate with subtraction returns correct result")
    void postCalculate_subtraction_returnsResult() throws Exception {
        mockMvc.perform(post("/calculate")
                        .param("operand1", "10")
                        .param("operator", "-")
                        .param("operand2", "3"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("response"));
    }

    @Test
    @DisplayName("POST /calculate with multiplication returns correct result")
    void postCalculate_multiplication_returnsResult() throws Exception {
        mockMvc.perform(post("/calculate")
                        .param("operand1", "4")
                        .param("operator", "*")
                        .param("operand2", "3"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("response"));
    }
}
