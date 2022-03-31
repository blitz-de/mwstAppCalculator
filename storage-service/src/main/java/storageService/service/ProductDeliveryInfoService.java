package storageService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import storageService.model.ProductDeliveryInfo;
import storageService.repository.ProductDeliveryInfoRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductDeliveryInfoService {

    @Autowired
    private ProductDeliveryInfoRepository productDeliveryInfoRepository;

    public ProductDeliveryInfo save(ProductDeliveryInfo productDeliveryInfo){
        return productDeliveryInfoRepository.save(productDeliveryInfo);
    }

    public List<ProductDeliveryInfo> saveAll(List<ProductDeliveryInfo> productDeliveryInfoList){
        return productDeliveryInfoRepository.saveAll(productDeliveryInfoList);
    }

    public List<ProductDeliveryInfo> findAll(){
        return productDeliveryInfoRepository.findAll();
    }

    public Optional<ProductDeliveryInfo> findByProductId(Integer id){
        return productDeliveryInfoRepository.findByProductId(id);
    }
}
