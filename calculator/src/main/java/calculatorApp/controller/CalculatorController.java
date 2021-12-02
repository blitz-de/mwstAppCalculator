package calculatorApp.controller;

//import calculatorApp.model.Calculator;
import calculatorApp.model.Product;
import calculatorApp.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;

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
    public ResponseEntity<Double>
        getMehrwertsteuerByProductId(@PathVariable Integer id){

        // ToDo: get Product price from mainApp microservice
        RestTemplate restTemplate = new RestTemplate();

        // Info: mwst = productsId.price * 0.19 -> calculatorService.calculateMwst(nettoPrice);

        Product product =
                restTemplate.getForObject("http://localhost:8765/api/products/"+id, Product.class);

        // ToDo: deserialize product

        Double mwst = calculatorService.calculatorMwst(product.getPrice());
        //Extras: Call the service first: RestTemplate.getForEntity(url, responseType, uriVariables)

        return new ResponseEntity<Double>(mwst, HttpStatus.ACCEPTED);
    }

}
