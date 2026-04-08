package org.example.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for all {@link OperationStrategy} implementations.
 * No Spring context needed — pure logic tests.
 */
class OperationStrategyTest {

    // ---- Addition ----

    @Test
    @DisplayName("Addition: 3 + 2 = 5")
    void addition_positiveNumbers() {
        assertEquals(5.0, new AdditionStrategy().execute(3, 2));
    }

    @Test
    @DisplayName("Addition: -1 + -1 = -2")
    void addition_negativeNumbers() {
        assertEquals(-2.0, new AdditionStrategy().execute(-1, -1));
    }

    @Test
    @DisplayName("Addition: 1.5 + 2.5 = 4.0")
    void addition_decimals() {
        assertEquals(4.0, new AdditionStrategy().execute(1.5, 2.5));
    }

    @Test
    @DisplayName("Addition symbol is '+'")
    void addition_symbol() {
        assertEquals("+", new AdditionStrategy().getSymbol());
    }

    // ---- Subtraction ----

    @Test
    @DisplayName("Subtraction: 10 - 3 = 7")
    void subtraction_positiveResult() {
        assertEquals(7.0, new SubtractionStrategy().execute(10, 3));
    }

    @Test
    @DisplayName("Subtraction: 3 - 10 = -7")
    void subtraction_negativeResult() {
        assertEquals(-7.0, new SubtractionStrategy().execute(3, 10));
    }

    @Test
    @DisplayName("Subtraction: x - 0 = x")
    void subtraction_byZero() {
        assertEquals(5.0, new SubtractionStrategy().execute(5, 0));
    }

    @Test
    @DisplayName("Subtraction symbol is '-'")
    void subtraction_symbol() {
        assertEquals("-", new SubtractionStrategy().getSymbol());
    }

    // ---- Multiplication ----

    @Test
    @DisplayName("Multiplication: 4 * 3 = 12")
    void multiplication_basic() {
        assertEquals(12.0, new MultiplicationStrategy().execute(4, 3));
    }

    @Test
    @DisplayName("Multiplication: x * 0 = 0")
    void multiplication_byZero() {
        assertEquals(0.0, new MultiplicationStrategy().execute(5, 0));
    }

    @Test
    @DisplayName("Multiplication: x * 1 = x")
    void multiplication_byOne() {
        assertEquals(7.0, new MultiplicationStrategy().execute(7, 1));
    }

    @Test
    @DisplayName("Multiplication: negative * negative = positive")
    void multiplication_negativeNumbers() {
        assertEquals(6.0, new MultiplicationStrategy().execute(-2, -3));
    }

    @Test
    @DisplayName("Multiplication symbol is '*'")
    void multiplication_symbol() {
        assertEquals("*", new MultiplicationStrategy().getSymbol());
    }

    // ---- Division ----

    @Test
    @DisplayName("Division: 10 / 2 = 5")
    void division_basic() {
        assertEquals(5.0, new DivisionStrategy().execute(10, 2));
    }

    @Test
    @DisplayName("Division: 7 / 1 = 7")
    void division_byOne() {
        assertEquals(7.0, new DivisionStrategy().execute(7, 1));
    }

    @Test
    @DisplayName("Division: 1 / 4 = 0.25")
    void division_decimal() {
        assertEquals(0.25, new DivisionStrategy().execute(1, 4));
    }

    @Test
    @DisplayName("Division by zero throws ArithmeticException")
    void division_byZero_throwsException() {
        ArithmeticException ex = assertThrows(ArithmeticException.class,
                () -> new DivisionStrategy().execute(5, 0));
        assertEquals("Division by zero is not allowed.", ex.getMessage());
    }

    @Test
    @DisplayName("Division symbol is '/'")
    void division_symbol() {
        assertEquals("/", new DivisionStrategy().getSymbol());
    }
}
