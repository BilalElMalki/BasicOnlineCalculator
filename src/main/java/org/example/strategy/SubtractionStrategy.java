package org.example.strategy;

import org.springframework.stereotype.Component;

/**
 * Strategy implementation for the subtraction operator (-).
 */
@Component
public class SubtractionStrategy implements OperationStrategy {

    @Override
    public double execute(double a, double b) {
        return a - b;
    }

    @Override
    public String getSymbol() {
        return "-";
    }
}
