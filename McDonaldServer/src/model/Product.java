package model;

import java.io.Serializable;

/**
 * This class is the model of product
 * 
 * @author zackt
 *
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private double price;
	private String type;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
