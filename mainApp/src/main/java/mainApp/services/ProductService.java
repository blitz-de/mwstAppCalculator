package mainApp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mainApp.model.Product;
import mainApp.repository.ProductRepository;

/**
 * Will call products from repository
 * @author zacki
 *
 */
@Service
public class ProductService {

	@Autowired
    ProductRepository productRepository;
	
	public ProductService() {
		
	}
	
	/**
	 * Get Product by id from Repo
	 * @param id
	 * @return
	 */
	public Optional<Product> getProductById(Integer id) {
		return productRepository.findById(id);
	}
	
	/**
	 * Create a product to the database
	 */
	public Product postProduct(Product product) {
		return productRepository.save(product);
	}
	
	
}
