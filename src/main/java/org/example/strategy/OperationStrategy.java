package org.example.strategy;

/**
 * Strategy interface for calculator operations.
 *
 * <p>Each arithmetic operator (+, -, *, /) implements this interface,
 * following the Strategy Pattern and Open/Closed Principle — new operations
 * can be added without modifying existing code.</p>
 */
public interface OperationStrategy {

    /**
     * Executes the arithmetic operation on two operands.
     *
     * @param a the left-hand operand
     * @param b the right-hand operand
     * @return the result of the operation
     */
    double execute(double a, double b);

    /**
     * Returns the operator symbol (e.g. "+", "-", "*", "/").
     *
     * @return the operator symbol as a string
     */
    String getSymbol();
}
