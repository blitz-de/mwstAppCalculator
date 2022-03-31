package calculatorApp;

import calculatorApp.model.Product;
import calculatorApp.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class CalculatorApplicationTests {

	private final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
	@InjectMocks
	private CalculatorService calculatorService;

	@Test
	public void shouldCalculateVATforExistingProduct() {
		Integer id = 1;
		Product product = new Product();
		product.setId(id);
		product.setPrice(130d);

		ResponseEntity<Product> myEntity = new ResponseEntity<>(product, HttpStatus.OK);

		when(restTemplate.getForEntity(
				ArgumentMatchers.anyString(),
				ArgumentMatchers.<Class<Product>>any())
		).thenReturn(myEntity);

		Double vat = calculatorService.calculatorMwst(id);
		assertThat(vat, is(24.7));
	}

	@Test
	public void shouldCalculateVATforPrice() {
		Double vat = calculatorService.calculatorMwst(130d);
		assertThat(vat, is(24.7));
	}


}
