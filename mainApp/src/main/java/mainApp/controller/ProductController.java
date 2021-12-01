package mainApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mainApp.model.Product;
import mainApp.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired ProductService productService;
	public ProductController() {
		
	}
	/**
	 * Getting productBy id using the url
	 * for example: localhost:9000/product/1
	 * @param id
	 * @return 
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Optional> getProduct(@PathVariable("id") Integer id) {
		Optional<Product> product = productService.getProductById(id);

		if (!product.isEmpty()) return new ResponseEntity<>(
				product, HttpStatus.ACCEPTED);
		System.out.println("Product########: " + product);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Product> createProduct(
			@RequestBody Product product){
		// ToDO: product must have properties
		productService.postProduct(product);
		
		return new ResponseEntity<>(product,
				HttpStatus.CREATED);
	}
	
}
