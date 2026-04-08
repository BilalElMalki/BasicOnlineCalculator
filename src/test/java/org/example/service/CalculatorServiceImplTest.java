package org.example.service;

import org.example.dto.CalculationRequestDTO;
import org.example.dto.CalculationResponseDTO;
import org.example.model.Calculation;
import org.example.repository.CalculationRepository;
import org.example.strategy.AdditionStrategy;
import org.example.strategy.DivisionStrategy;
import org.example.strategy.MultiplicationStrategy;
import org.example.strategy.SubtractionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link CalculatorServiceImpl}.
 * Uses Mockito to mock the repository — no database needed.
 */
@ExtendWith(MockitoExtension.class)
class CalculatorServiceImplTest {

    @Mock
    private CalculationRepository repository;

    private CalculatorServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new CalculatorServiceImpl(
                List.of(new AdditionStrategy(), new SubtractionStrategy(),
                        new MultiplicationStrategy(), new DivisionStrategy()),
                repository
        );
    }

    @Test
    @DisplayName("calculate: 6 + 4 returns result 10 and saves to repository")
    void calculate_addition_savesAndReturnsResult() {
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        CalculationResponseDTO response = service.calculate(new CalculationRequestDTO(6.0, "+", 4.0));

        assertFalse(response.hasError());
        assertEquals(10.0, response.getResult());
        assertTrue(response.getExpression().contains("10.0"));
        verify(repository, times(1)).save(any(Calculation.class));
    }

    @Test
    @DisplayName("calculate: 9 - 3 returns result 6")
    void calculate_subtraction() {
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        CalculationResponseDTO response = service.calculate(new CalculationRequestDTO(9.0, "-", 3.0));
        assertEquals(6.0, response.getResult());
    }

    @Test
    @DisplayName("calculate: 5 * 3 returns result 15")
    void calculate_multiplication() {
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        CalculationResponseDTO response = service.calculate(new CalculationRequestDTO(5.0, "*", 3.0));
        assertEquals(15.0, response.getResult());
    }

    @Test
    @DisplayName("calculate: 10 / 2 returns result 5")
    void calculate_division() {
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        CalculationResponseDTO response = service.calculate(new CalculationRequestDTO(10.0, "/", 2.0));
        assertEquals(5.0, response.getResult());
    }

    @Test
    @DisplayName("calculate: division by zero returns error, does not save")
    void calculate_divisionByZero_returnsError() {
        CalculationResponseDTO response = service.calculate(new CalculationRequestDTO(5.0, "/", 0.0));
        assertTrue(response.hasError());
        assertEquals("Division by zero is not allowed.", response.getError());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("calculate: unknown operator returns error, does not save")
    void calculate_unknownOperator_returnsError() {
        CalculationResponseDTO response = service.calculate(new CalculationRequestDTO(5.0, "%", 2.0));
        assertTrue(response.hasError());
        assertTrue(response.getError().contains("Unknown operator"));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("getHistory: delegates to repository")
    void getHistory_delegatesToRepository() {
        when(repository.findAllByOrderByTimestampDesc()).thenReturn(List.of());
        List<Calculation> history = service.getHistory();
        assertNotNull(history);
        verify(repository).findAllByOrderByTimestampDesc();
    }
}
