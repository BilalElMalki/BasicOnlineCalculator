package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.CalculationRequestDTO;
import org.example.dto.CalculationResponseDTO;
import org.example.service.CalculatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * MVC Controller for the Calculator UI.
 *
 * <p>Thin controller — delegates all business logic to {@link CalculatorService}.
 * Handles GET (render form) and POST (process calculation) for the calculator page.</p>
 */
@Controller
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    /**
     * Renders the calculator home page with an empty form and calculation history.
     *
     * @param model Spring MVC model passed to the Thymeleaf template
     * @return the name of the Thymeleaf template to render
     */
    @GetMapping("/")
    public String showCalculator(Model model) {
        model.addAttribute("request", new CalculationRequestDTO());
        model.addAttribute("history", calculatorService.getHistory());
        return "calculator";
    }

    /**
     * Processes a calculation form submission and returns the result.
     *
     * @param request       the form-bound DTO with operands and operator
     * @param bindingResult validation errors, if any
     * @param model         Spring MVC model passed to the Thymeleaf template
     * @return the calculator template name (re-renders with result or errors)
     */
    @PostMapping("/calculate")
    public String calculate(@Valid @ModelAttribute("request") CalculationRequestDTO request,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("history", calculatorService.getHistory());
            return "calculator";
        }

        CalculationResponseDTO response = calculatorService.calculate(request);
        model.addAttribute("response", response);
        model.addAttribute("history", calculatorService.getHistory());
        return "calculator";
    }
}
