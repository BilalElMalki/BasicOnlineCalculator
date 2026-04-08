package org.example.service;

import org.example.dto.CalculationRequestDTO;
import org.example.dto.CalculationResponseDTO;
import org.example.model.Calculation;
import org.example.repository.CalculationRepository;
import org.example.strategy.OperationStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service implementation that resolves the appropriate {@link OperationStrategy}
 * for a given operator symbol and delegates computation to it.
 *
 * <p>All {@link OperationStrategy} beans are auto-collected by Spring at startup
 * and indexed by their symbol. Adding a new operator only requires creating a
 * new {@code @Component} that implements {@link OperationStrategy}.</p>
 */
@Service
public class CalculatorServiceImpl implements CalculatorService {

    private final Map<String, OperationStrategy> strategies;
    private final CalculationRepository repository;

    /**
     * Injects all {@link OperationStrategy} implementations registered as Spring beans
     * and builds a lookup map keyed by operator symbol.
     *
     * @param strategyList all strategy beans collected by Spring DI
     * @param repository   MongoDB repository for persisting history
     */
    public CalculatorServiceImpl(List<OperationStrategy> strategyList, CalculationRepository repository) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(OperationStrategy::getSymbol, Function.identity()));
        this.repository = repository;
    }

    @Override
    public CalculationResponseDTO calculate(CalculationRequestDTO request) {
        OperationStrategy strategy = strategies.get(request.getOperator());

        if (strategy == null) {
            return CalculationResponseDTO.ofError("Unknown operator: " + request.getOperator());
        }

        try {
            double result = strategy.execute(request.getOperand1(), request.getOperand2());
            String expression = request.getOperand1() + " " + request.getOperator()
                    + " " + request.getOperand2() + " = " + result;

            repository.save(new Calculation(request.getOperand1(), request.getOperator(),
                    request.getOperand2(), result));

            return new CalculationResponseDTO(result, expression);
        } catch (ArithmeticException e) {
            return CalculationResponseDTO.ofError(e.getMessage());
        }
    }

    @Override
    public List<Calculation> getHistory() {
        return repository.findAllByOrderByTimestampDesc();
    }
}
