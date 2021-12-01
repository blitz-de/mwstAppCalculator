package calculatorApp.controller;

//import calculatorApp.model.Calculator;
import calculatorApp.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

    @Autowired CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService){
        this.calculatorService = calculatorService;
    }

    @GetMapping("/{id}")
    public Long getMehrwertsteuerByProductId(@PathVariable Long id){

        // ToDo: get Product price from mainApp microservice

        // mwst = productsId.price * 0.19 -> calculatorService.calculateMwst(nettoPrice);
        Long mwst = calculatorService.calculatorMwst(0L);

        return mwst;
    }

}
