package mainApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mainApp.model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

	
}
