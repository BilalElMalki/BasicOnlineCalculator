package org.example.dto;

/**
 * DTO returned to the UI after a calculation.
 * Contains the result and the human-readable expression string.
 */
public class CalculationResponseDTO {

    private double result;
    private String expression;
    private String error;

    public CalculationResponseDTO() {}

    public CalculationResponseDTO(double result, String expression) {
        this.result = result;
        this.expression = expression;
    }

    public static CalculationResponseDTO ofError(String error) {
        CalculationResponseDTO dto = new CalculationResponseDTO();
        dto.error = error;
        return dto;
    }

    public double getResult() { return result; }
    public void setResult(double result) { this.result = result; }

    public String getExpression() { return expression; }
    public void setExpression(String expression) { this.expression = expression; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public boolean hasError() { return error != null && !error.isBlank(); }
}
