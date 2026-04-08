package org.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * MongoDB document representing a single calculator operation and its result.
 * Stored in the "calculations" collection for history tracking.
 */
@Document(collection = "calculations")
public class Calculation {

    @Id
    private String id;

    private double operand1;
    private double operand2;
    private String operator;
    private double result;
    private LocalDateTime timestamp;

    public Calculation() {}

    public Calculation(double operand1, String operator, double operand2, double result) {
        this.operand1 = operand1;
        this.operator = operator;
        this.operand2 = operand2;
        this.result = result;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public double getOperand1() { return operand1; }
    public void setOperand1(double operand1) { this.operand1 = operand1; }

    public double getOperand2() { return operand2; }
    public void setOperand2(double operand2) { this.operand2 = operand2; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public double getResult() { return result; }
    public void setResult(double result) { this.result = result; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
