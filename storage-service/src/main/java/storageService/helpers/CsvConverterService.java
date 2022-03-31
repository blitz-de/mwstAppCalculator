package storageService.helpers;

import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import storageService.model.Product;
import storageService.service.ProductService;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class CsvConverterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvConverterService.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.main-app}")
    private String mainAppPath;

    public void process() throws IOException {
        ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(mainAppPath + "/product/export/", byte[].class);

        if (responseEntity.getBody() != null) {
            Path tempImportFile = Files.createTempFile("import", ".csv");
            Files.write(tempImportFile, responseEntity.getBody());

            FileReader reader = new FileReader(tempImportFile.toFile());

            List<Product> products = new CsvToBeanBuilder<Product>(reader)
                    .withType(Product.class)
                    .build()
                    .parse();
            reader.close();

            productService.saveAll(products);

            Files.delete(tempImportFile);
        }


    }
}
