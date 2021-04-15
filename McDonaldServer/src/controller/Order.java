package controller;

import java.io.Serializable;
import java.util.ArrayList;

import model.OrderItem;
import model.Product;
/**
 * This class manage the operation for order list
 * 
 * @author zackt
 *
 */
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<OrderItem> orderList = new ArrayList<>();
	private double totalPrice;
	private String date;
	private String dineInOrTakeAway;
	
	public double getTotalPrice() {
		return totalPrice;
	}

	private void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	// Get order list
	public ArrayList<OrderItem> getOrderList()
	{
		return new ArrayList<OrderItem>(orderList);
	}

	// Add an order list
	public void addOrderList(ArrayList<OrderItem> orderList) 
	{
		this.orderList = orderList;
	}
	
	// Add order items into list
	public void addOrderItem(int index, Product product, int quantity, double totalPrice)
	{
		this.orderList.add(new OrderItem(index, product, quantity));
		setTotalPrice(totalPrice);
	}
	
	// Clear order
	public void clearOrderList()
	{
		this.orderList.clear();
	}
	
	public boolean isEmpty() 
	{
		return this.orderList.isEmpty();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDineInOrTakeAway() {
		return dineInOrTakeAway;
	}

	public void setDineInOrTakeAway(String dineInOrTakeAway) {
		this.dineInOrTakeAway = dineInOrTakeAway;
	}
}
