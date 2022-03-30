package mainApp.services;

import mainApp.model.Product;
import mainApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Will call products from repository
 *
 * @author zacki
 */
@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	public Optional<Product> findById(Integer id) {
		return productRepository.findById(id);
	}

	public Product save(Product product) {
		return productRepository.save(product);
	}

	public List<Product> findAll(){
		return productRepository.findAll();
	}


}
