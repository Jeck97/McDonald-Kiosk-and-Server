package model;

import java.io.Serializable;

/**
 * This class is the model of order item
 * 
 * @author zackt
 *
 */
public class OrderItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int index;
	private Product product;
	private int quantity;
	
	public OrderItem(int index, Product product, int quantity) 
	{
		this.index = index;
		this.product = product;
		this.quantity = quantity;
	}
	
	public int getIndex() 
	{
		return index;
	}

	public Product getProduct() 
	{
		return product;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public int getQuantity() 
	{
		return quantity;
	}

	/**
	 * This method calculate and return the total price
	 * 
	 * @return total price
	 */
	public double getTotalPriceOfItems() {
		return this.product.getPrice()* this.quantity; 
	}
}
