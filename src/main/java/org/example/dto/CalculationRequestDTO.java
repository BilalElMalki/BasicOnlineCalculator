package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for incoming calculation requests from the UI.
 * Decouples the API/form layer from the internal domain model.
 */
public class CalculationRequestDTO {

    @NotNull(message = "First operand is required")
    private Double operand1;

    @NotBlank(message = "Operator is required")
    private String operator;

    @NotNull(message = "Second operand is required")
    private Double operand2;

    public CalculationRequestDTO() {}

    public CalculationRequestDTO(Double operand1, String operator, Double operand2) {
        this.operand1 = operand1;
        this.operator = operator;
        this.operand2 = operand2;
    }

    public Double getOperand1() { return operand1; }
    public void setOperand1(Double operand1) { this.operand1 = operand1; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public Double getOperand2() { return operand2; }
    public void setOperand2(Double operand2) { this.operand2 = operand2; }
}
