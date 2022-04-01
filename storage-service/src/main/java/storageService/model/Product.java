package storageService.model;

import com.opencsv.bean.CsvBindByPosition;

import javax.persistence.*;


@Entity
@Table(name = "product")
public class Product {

    @Id
    @CsvBindByPosition(position = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;
    @CsvBindByPosition(position = 1)
    @Column(name = "name")
    String name;
    @CsvBindByPosition(position = 2)
    @Column(name = "price")
    Double price;
    @CsvBindByPosition(position = 3)
    @Column(name = "location")
    String location;
    @CsvBindByPosition(position = 4)
    @Column(name = "description")
    String description;
    @Column(name = "color")
    @CsvBindByPosition(position = 5)
    String color;
    @Column(name = "size")
    @CsvBindByPosition(position = 6)
    Double size;
    @Column(name = "weight")
    @CsvBindByPosition(position = 7)
    Double weight;
    @Column(name = "type")
    @CsvBindByPosition(position = 8)
    String type;
    @Column(name = "place_of_manufacture")
    @CsvBindByPosition(position = 9)
    String placeOfManufacture;

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlaceOfManufacture() {
        return placeOfManufacture;
    }

    public void setPlaceOfManufacture(String placeOfManufacture) {
        this.placeOfManufacture = placeOfManufacture;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
