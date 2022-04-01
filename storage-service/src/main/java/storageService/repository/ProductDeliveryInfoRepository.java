package storageService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import storageService.model.ProductDeliveryInfo;

import java.util.Optional;

public interface ProductDeliveryInfoRepository extends JpaRepository<ProductDeliveryInfo, Integer> {

    Optional<ProductDeliveryInfo> findByProductId(Integer id);
}
