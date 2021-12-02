package calculatorApp.service;

//import calculatorApp.Repository.CalculatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

//    @Autowired CalculatorRepository calculatorRepo;

    public Double calculatorMwst(Double nettoPrice){
        Double mehrwertsteuer = nettoPrice * 0.19;

        return mehrwertsteuer;
    }
}
