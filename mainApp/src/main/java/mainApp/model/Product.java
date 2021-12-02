package mainApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Integer id;
	String name;
	Double price;
	String location;
	String description;
	
	public Product() {}
	public Product(Integer id, String name,
				   Double price, String location, String description) {
		this.id = id;
		this.price = price;
		this.name = name;
		this.location = location;
		this.description = description;
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
	
	
}
