package org.example.service;

import org.example.dto.CalculationRequestDTO;
import org.example.dto.CalculationResponseDTO;
import org.example.model.Calculation;

import java.util.List;

/**
 * Service interface defining the calculator's core use cases.
 *
 * <p>Kept as an interface to support the Dependency Inversion Principle —
 * the controller depends on this abstraction, not the concrete implementation.</p>
 */
public interface CalculatorService {

    /**
     * Performs a calculation based on the given request and persists the result.
     *
     * @param request DTO containing operands and operator
     * @return DTO containing the result and expression, or an error message
     */
    CalculationResponseDTO calculate(CalculationRequestDTO request);

    /**
     * Returns all past calculations ordered by most recent first.
     *
     * @return list of persisted {@link Calculation} documents
     */
    List<Calculation> getHistory();
}
