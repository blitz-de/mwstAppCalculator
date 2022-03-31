package calculatorApp.controller;

import calculatorApp.service.CalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    @Autowired CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService){
        this.calculatorService = calculatorService;
    }

    /**
     * localhost:8082/api/products/1 -> returns it's Mehrwertssteuer
     * @param id
     * @return mehrwertsteur of the product's netto price
     */
    @GetMapping("/product/{id}")
    @Operation(summary = "Calculate VAT for a specific product ")
    @ApiResponse(description = "VAT amount")
    public ResponseEntity<Double> getMehrwertsteuerByProductId(
            @Parameter(description = "Id of the product") @PathVariable Integer id
    ) {
        Double vat = calculatorService.calculatorMwst(id);
        if (vat != null) {
            return ResponseEntity.ok(vat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/vat")
    @Operation(summary = "Calculate VAT based on a price ")
    @ApiResponse(description = "VAT amount")
    public ResponseEntity<Double> getMehrwertsteuer(
            @Parameter(description = "Price based on which VAT will be calculated") @RequestParam Double price
    ) {
        return ResponseEntity.ok(calculatorService.calculatorMwst(price));
    }

}

