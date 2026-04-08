package org.example.strategy;

import org.springframework.stereotype.Component;

/**
 * Strategy implementation for the division operator (/).
 * Throws {@link ArithmeticException} on division by zero.
 */
@Component
public class DivisionStrategy implements OperationStrategy {

    @Override
    public double execute(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a / b;
    }

    @Override
    public String getSymbol() {
        return "/";
    }
}
