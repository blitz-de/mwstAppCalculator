package storageService.model;

import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.*;

@Entity
@Table(name = "delivery_info")
public class ProductDeliveryInfo {

    @Id
    @Column(name = "id")
    @CsvBindByPosition(position = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "productId")
    @CsvBindByPosition(position = 1)
    private Integer productId;
    @Column(name = "deliveryTime")
    @CsvBindByPosition(position = 2)
    private Integer deliveryTime;
    @Column(name = "productAmount")
    @CsvBindByPosition(position = 3)
    private Integer productAmount;
    @Column(name = "location")
    @CsvBindByPosition(position = 4)
    private String location;

    public ProductDeliveryInfo(Integer deliveryTime, Integer productAmount, String location) {
        this.deliveryTime = deliveryTime;
        this.productAmount = productAmount;
        this.location = location;
    }

    public ProductDeliveryInfo() {
    }

    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
