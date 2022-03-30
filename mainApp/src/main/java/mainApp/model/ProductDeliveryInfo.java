package mainApp.model;

public class ProductDeliveryInfo {

    private Integer id;
    private Integer productId;
    private Integer deliveryTime;
    private Integer productAmount;
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
