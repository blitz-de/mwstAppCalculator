package storageService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import storageService.model.Product;
import storageService.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void save(Product product){
        productRepository.save(product);
    }

    public List<Product> saveAll(List<Product> productList){
        return productRepository.saveAll(productList);
    }

    public Optional<Product> findById(Integer id){
        return productRepository.findById(id);
    }


}
