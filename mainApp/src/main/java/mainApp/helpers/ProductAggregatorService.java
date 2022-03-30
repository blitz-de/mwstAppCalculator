package mainApp.helpers;

import mainApp.dto.ProductDTO;
import mainApp.model.Product;
import mainApp.model.ProductDeliveryInfo;
import mainApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductAggregatorService {

    @Value("${service.storage}")
    private String storagePath;

    @Value("${service.calculator}")
    private String calculatorPath;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductService productService;

    public List<ProductDTO> findAll() {
        List<ProductDTO> productDTOList = new ArrayList<>();

        ResponseEntity<ProductDeliveryInfo[]> responseEntity = restTemplate.getForEntity(storagePath + "/product/details/", ProductDeliveryInfo[].class);
        // Task: Provide all product informations -> stored in Main-Service: except
        List<ProductDeliveryInfo> productDeliveryInfoList = new ArrayList<>();

        if (responseEntity.getBody() != null) {
            productDeliveryInfoList = List.of(responseEntity.getBody());
        }

        // Task: Provide List of products
        List<Product> productList = productService.findAll();

        for (Product product : productList) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setColor(product.getColor());
            productDTO.setDescription(product.getDescription());
            productDTO.setLocation(product.getLocation());
            productDTO.setPrice(product.getPrice());
            productDTO.setName(product.getName());
            productDTO.setSize(product.getSize());
            productDTO.setWeight(product.getWeight());
            productDTO.setPlaceOfManufacture(product.getPlaceOfManufacture());
            productDTO.setType(product.getType());

            for (ProductDeliveryInfo productDeliveryInfo : productDeliveryInfoList) {
                if (Objects.equals(productDeliveryInfo.getProductId(), product.getId())) {
                    productDTO.setLocationInfo(productDeliveryInfo.getLocation());
                    productDTO.setProductAmount(productDeliveryInfo.getProductAmount());
                    productDTO.setDeliveryTime(productDeliveryInfo.getDeliveryTime());
                    Instant estimatedDelivery = Instant.now().plus(productDeliveryInfo.getDeliveryTime(), ChronoUnit.DAYS);
                    productDTO.setEstimatedDelivery(estimatedDelivery);
                }
            }
            // fetch vat price to put in the product table
            ResponseEntity<Double> vatResponse = restTemplate.getForEntity(calculatorPath + "/vat?price=" + product.getPrice(), Double.class);
            productDTO.setVat(vatResponse.getBody());

            productDTOList.add(productDTO);
        }

        return productDTOList;
    }

    public ProductDTO findById(Integer id) {
        Optional<Product> productOpt = productService.findById(id);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            ProductDTO productDTO = new ProductDTO();
            ResponseEntity<ProductDeliveryInfo> responseEntity = restTemplate.getForEntity(storagePath + "/product/" + id + "/details/", ProductDeliveryInfo.class);
            ProductDeliveryInfo productDeliveryInfo = new ProductDeliveryInfo();

            if (responseEntity.getBody() != null) {
                productDeliveryInfo = responseEntity.getBody();
            }

            productDTO.setId(product.getId());
            productDTO.setColor(product.getColor());
            productDTO.setDescription(product.getDescription());
            productDTO.setLocation(product.getLocation());
            productDTO.setPrice(product.getPrice());
            productDTO.setName(product.getName());
            productDTO.setSize(product.getSize());
            productDTO.setPlaceOfManufacture(product.getPlaceOfManufacture());
            productDTO.setType(product.getType());
            productDTO.setWeight(product.getWeight());
            productDTO.setLocationInfo(productDeliveryInfo.getLocation());
            productDTO.setProductAmount(productDeliveryInfo.getProductAmount());
            productDTO.setDeliveryTime(productDeliveryInfo.getDeliveryTime());
            Instant estimatedDelivery = Instant.now().plus(productDeliveryInfo.getDeliveryTime(), ChronoUnit.DAYS);
            productDTO.setEstimatedDelivery(estimatedDelivery);

            ResponseEntity<Double> vatResponse = restTemplate.getForEntity(calculatorPath + "/vat?price=" + product.getPrice(), Double.class);
            productDTO.setVat(vatResponse.getBody());

            return productDTO;
        }

        return null;
    }

    public List<ProductDeliveryInfo> importProductDeliveryInfoCsv(MultipartFile multipartFile) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", multipartFile.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<ProductDeliveryInfo[]> responseEntity = restTemplate
                .postForEntity(storagePath + "/product/details/csv", requestEntity, ProductDeliveryInfo[].class);

        if (responseEntity.getBody() != null) {
            return List.of(responseEntity.getBody());
        }
        return null;
    }

    public List<ProductDeliveryInfo> importDeliveryInfoList(List<ProductDeliveryInfo> productDeliveryInfoList) {
        ResponseEntity<ProductDeliveryInfo[]> responseEntity = restTemplate
                .postForEntity(storagePath + "/product/details/", productDeliveryInfoList, ProductDeliveryInfo[].class);

        if (responseEntity.getBody() != null) {
            return List.of(responseEntity.getBody());
        }
        return null;
    }

    public ProductDeliveryInfo importDeliveryInfo(ProductDeliveryInfo productDeliveryInfo, Integer id) {
        ResponseEntity<ProductDeliveryInfo> responseEntity = restTemplate
                .postForEntity(storagePath + "/product/" + id + "/details/", productDeliveryInfo, ProductDeliveryInfo.class);

        if (responseEntity.getBody() != null) {
            return responseEntity.getBody();
        }
        return null;
    }
}
