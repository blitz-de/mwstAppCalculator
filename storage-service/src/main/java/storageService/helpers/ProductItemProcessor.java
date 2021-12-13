package storageService.helpers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import storageService.model.Product;

public class ProductItemProcessor implements ItemProcessor<Product, Product> {

    private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);

    @Override
    public Product process(Product product) throws Exception {
        final Integer id = product.getId();
        final String description = product.getDescription().toUpperCase();
        final String location = product.getLocation().toUpperCase();
        final Double price = product.getPrice();
        final String name = product.getName().toUpperCase();

        final Product transformedProduct = new Product(id, name, price, location,
                description);
        log.info("Converting (" + product + ") into (" + transformedProduct + ")");
        return transformedProduct;
    }

}
