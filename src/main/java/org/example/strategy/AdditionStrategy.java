package org.example.strategy;

import org.springframework.stereotype.Component;

/**
 * Strategy implementation for the addition operator (+).
 * Registered as a Spring bean so it can be auto-discovered and injected.
 */
@Component
public class AdditionStrategy implements OperationStrategy {

    @Override
    public double execute(double a, double b) {
        return a + b;
    }

    @Override
    public String getSymbol() {
        return "+";
    }
}
