package storageService.model;

import java.util.Date;

/**
 * ProductDeliveryInfos
 */
public class ProductDeliveryInfo {

    private Date deliveryTime;
    private Integer productAmount;
    private String location;

    public ProductDeliveryInfo(Date deliveryTime, Integer productAmount, String location) {
        this.deliveryTime = deliveryTime;
        this.productAmount = productAmount;
        this.location = location;
    }

    public ProductDeliveryInfo() {}

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public Integer getProductAmount() {
        return productAmount;
    }

    public String getLocation() {
        return location;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
