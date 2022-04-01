package mainApp;

import com.google.gson.Gson;
import mainApp.dto.ProductDTO;
import mainApp.model.Product;
import mainApp.repository.ProductRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "/test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MainAppApplicationTests {

	@Autowired
	private MockMvc mainAppController;

	@Autowired
	private ProductRepository productRepository;

	private final Gson gson = new Gson();


	@Test
	void shouldFindProductByIdInDatabase() throws Exception {
		Product product = new Product();
		product.setId(1);
		product.setColor("blue");
		product.setDescription("the best tv");
		product.setLocation("Rome");
		product.setName("TV LED");
		product.setPlaceOfManufacture("Italy");
		product.setSize(30d);
		product.setWeight(15d);
		product.setType("LED");

		productRepository.save(product);

		MvcResult result = mainAppController.perform(get("/product/" + product.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn();

		Product existingProduct = gson.fromJson(result.getResponse().getContentAsString(), Product.class);
		assertThat(existingProduct.getId(), is(1));
	}

	@Test
	void shouldAddNewProduct() throws Exception {
		Product product = new Product();
		product.setColor("blue");
		product.setDescription("the best tv");
		product.setLocation("Rome");
		product.setName("TV LED");
		product.setPlaceOfManufacture("Italy");
		product.setSize(30d);
		product.setWeight(15d);
		product.setType("LED");

		MvcResult result = mainAppController.perform(post("/product/add")
						.contentType(MediaType.APPLICATION_JSON)
						.content(gson.toJson(product)))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn();

		Product newlyAddedProduct = gson.fromJson(result.getResponse().getContentAsString(), Product.class);
		assertThat(newlyAddedProduct.getName(), is("TV LED"));
		assertThat(newlyAddedProduct.getColor(), is("blue"));
	}

}
