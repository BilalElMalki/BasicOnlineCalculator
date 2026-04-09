# Calculator App

A web-based calculator built with Spring Boot that supports basic arithmetic operations and persists calculation history to MongoDB.

---

## User Guide

### Getting Started

1. **Start the application** — run the app via Maven or your IDE. It will be available at `http://localhost:8080`.
2. **Open your browser** and navigate to `http://localhost:8080`.

### Using the Calculator

The calculator works similarly to a standard mobile calculator:

| Action | How to do it |
|---|---|
| Enter a number | Click the digit buttons (0–9) |
| Use a decimal point | Click the `.` button |
| Toggle positive/negative | Click the `+/-` button |
| Convert to percentage | Click the `%` button |
| Select an operation | Click `+`, `-`, `×`, or `÷` |
| Get the result | Click `=` |
| Clear the display | Click `AC` (All Clear) |

### Calculation History

Every calculation you perform is automatically saved and displayed in the history panel below the calculator. The history is sorted from most recent to oldest and persists across page refreshes.

### Error Handling

- **Division by zero** — displays a clear error message instead of crashing.
- **Invalid input** — the form validates required fields before submitting.
- Errors appear as toast notifications so they do not interrupt your workflow.

---

## Tech Stack

### Core

| Technology | Version | Purpose |
|---|---|---|
| Java | 21 | Programming language |
| Spring Boot | 3.2.4 | Application framework |
| Apache Maven | 3.x | Build and dependency management |

### Web Layer

| Technology | Purpose |
|---|---|
| Spring MVC | HTTP request handling and routing |
| Thymeleaf | Server-side HTML templating |
| HTML5 / CSS3 / JavaScript | Client-side UI and calculator state management |

### Data Layer

| Technology | Purpose |
|---|---|
| MongoDB | NoSQL database for persisting calculation history |
| Spring Data MongoDB | Repository abstraction over MongoDB |
| Flapdoodle Embedded MongoDB | In-memory MongoDB for development and tests (no local install required) |

### Validation & Testing

| Technology | Purpose |
|---|---|
| Jakarta Bean Validation | Request DTO validation (`@NotNull`, `@NotBlank`) |
| JUnit 5 | Unit and integration test framework |
| Mockito | Mocking dependencies in unit tests |
| Spring MockMvc | HTTP-level integration testing |

---

## Architecture & Design Patterns

### Layered MVC Architecture

The application follows a strict three-layer architecture:

```
HTTP Request
     │
     ▼
 Controller          (handles HTTP, delegates to service)
     │
     ▼
  Service            (business logic, strategy selection)
     │
     ▼
 Repository          (data access abstraction)
     │
     ▼
  MongoDB            (persistent storage)
     │
     ▼
 Thymeleaf View      (renders the response)
```

### Design Patterns

#### Strategy Pattern
Each arithmetic operation is encapsulated in its own class implementing the `OperationStrategy` interface:

```
OperationStrategy (interface)
  ├── AdditionStrategy       (+)
  ├── SubtractionStrategy    (-)
  ├── MultiplicationStrategy (*)
  └── DivisionStrategy       (÷)
```

At startup, the service builds a `Map<String, OperationStrategy>` keyed by operator symbol. When a calculation is requested, the correct strategy is looked up and executed. Adding a new operation requires only creating a new strategy class — no existing code changes needed (**Open/Closed Principle**).

#### Repository Pattern
`CalculationRepository` extends `MongoRepository`, abstracting all database access behind an interface. The service layer has no knowledge of MongoDB internals, making the persistence layer easy to swap or test.

#### DTO Pattern
- `CalculationRequestDTO` — captures and validates form input from the UI.
- `CalculationResponseDTO` — carries the result or error back to the view.

DTOs decouple the HTTP/view layer from the internal domain model (`Calculation`), keeping each layer's concerns separate.

#### Thin Controller
The controller handles only HTTP concerns (routing, model binding, redirects). All business logic lives in the service layer, keeping the controller simple and testable.

### Best Practices Applied

- **Constructor-based Dependency Injection** — all dependencies injected via constructor, not field injection, making components easier to test and reason about.
- **Interface-driven design** — `CalculatorService` is an interface; the controller depends on the abstraction, not the implementation.
- **Separation of concerns** — each layer has a single, well-defined responsibility.
- **Validation at the boundary** — input is validated as soon as it enters the system (controller layer), before reaching the service.
- **Comprehensive test coverage** — unit tests for strategies and service logic, integration tests for the full HTTP stack with embedded MongoDB.

---

## Project Structure

```
src/
├── main/
│   ├── java/org/example/
│   │   ├── CalculatorApplication.java          # Entry point
│   │   ├── controller/
│   │   │   └── CalculatorController.java       # HTTP layer
│   │   ├── service/
│   │   │   ├── CalculatorService.java          # Service interface
│   │   │   └── CalculatorServiceImpl.java      # Business logic
│   │   ├── repository/
│   │   │   └── CalculationRepository.java      # MongoDB repository
│   │   ├── model/
│   │   │   └── Calculation.java                # MongoDB document
│   │   ├── dto/
│   │   │   ├── CalculationRequestDTO.java      # Input DTO
│   │   │   └── CalculationResponseDTO.java     # Output DTO
│   │   └── strategy/
│   │       ├── OperationStrategy.java          # Strategy interface
│   │       ├── AdditionStrategy.java
│   │       ├── SubtractionStrategy.java
│   │       ├── MultiplicationStrategy.java
│   │       └── DivisionStrategy.java
│   └── resources/
│       ├── application.properties              # App configuration
│       └── templates/
│           └── calculator.html                 # Thymeleaf UI template
└── test/
    └── java/org/example/
        ├── CalculatorServiceImplTest.java       # Service unit tests
        ├── OperationStrategyTest.java           # Strategy unit tests
        └── CalculatorControllerIntegrationTest.java  # Integration tests
```
