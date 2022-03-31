package storageService.service;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import storageService.model.Product;
import storageService.model.ProductDeliveryInfo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDeliveryInfoService productDeliveryInfoService;

    public List<Product> saveAll(MultipartFile file) {
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            List<Product> products = new CsvToBeanBuilder<Product>(reader)
                    .withType(Product.class)
                    .build()
                    .parse();
            return productService.saveAll(products);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error parsing csv");
        }
    }

    public List<ProductDeliveryInfo> saveAllDeliveryInfo(MultipartFile file) {
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            List<ProductDeliveryInfo> deliveryInfoList = new CsvToBeanBuilder<ProductDeliveryInfo>(reader)
                    .withType(ProductDeliveryInfo.class)
                    .build()
                    .parse();

            List<ProductDeliveryInfo> deliveryInfoListToSave = new ArrayList<>();

            deliveryInfoList.forEach(
                    productDeliveryInfo -> {
                        Optional<Product> productOpt = productService.findById(productDeliveryInfo.getProductId());
                        if (productOpt.isPresent()) {
                            deliveryInfoListToSave.add(productDeliveryInfo);
                        }
                    }
            );
            return productDeliveryInfoService.saveAll(deliveryInfoListToSave);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error parsing csv");
        }
    }

    public List<ProductDeliveryInfo> saveAllDeliveryInfo(List<ProductDeliveryInfo> productDeliveryInfoList) {
        List<ProductDeliveryInfo> deliveryInfoListToSave = new ArrayList<>();

        productDeliveryInfoList.forEach(
                productDeliveryInfo -> {
                    Optional<Product> productOpt = productService.findById(productDeliveryInfo.getProductId());
                    if (productOpt.isPresent()) {
                        deliveryInfoListToSave.add(productDeliveryInfo);
                    }
                }
        );
        return productDeliveryInfoService.saveAll(deliveryInfoListToSave);
    }

    public ProductDeliveryInfo saveDeliveryInfo(ProductDeliveryInfo productDeliveryInfo, Integer id){
        if(productService.findById(id).isPresent()){
            return productDeliveryInfoService.save(productDeliveryInfo);
        } else {
            return null;
        }
    }

    public List<ProductDeliveryInfo> findAllDeliveryInfo(){
        return productDeliveryInfoService.findAll();
    }

    public ProductDeliveryInfo findByProductId(Integer id){
        return productDeliveryInfoService.findByProductId(id).orElse(null);
    }
}
