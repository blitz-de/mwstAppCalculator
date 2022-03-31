package calculatorApp.service;

//import calculatorApp.Repository.CalculatorRepository;
import calculatorApp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CalculatorService {

    @Value("${service.main-app}")
    private String mainAppPath;

    @Autowired
    private RestTemplate restTemplate;

    public CalculatorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Double calculatorMwst(Integer id){
        Double vat = null;

        ResponseEntity<Product> responseEntity = restTemplate.getForEntity(mainAppPath + "/product/" + id, Product.class);

        if(responseEntity.getBody() != null){
            Product product = responseEntity.getBody();
            vat = calculatorMwst(product.getPrice());
        }

        return vat;
    }

    public Double calculatorMwst(Double nettoPrice){
        return nettoPrice * 0.19;
    }
}
