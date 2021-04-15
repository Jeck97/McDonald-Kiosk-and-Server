package model;

import javax.swing.table.DefaultTableModel;

import controller.Order;

/**
 * This class is the model for cart table
 * 
 * @author zackt
 *
 */
public class CartTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * This method retrieve the data from the vector
	 * 
	 * @param carts
	 */
	public CartTableModel(Order order) {
		
		//Initialize the size of the row
		int rowSize = order.getOrderList().size();
		String[][] data = new String[rowSize][5];
		
		//Retrieve data by looping through the cart vector
		for (int i = 0; i < rowSize; i++)
		{
			OrderItem orderItem = order.getOrderList().get(i);
				data[i][0] = String.valueOf(orderItem.getIndex());
				data[i][1] = String.valueOf(orderItem.getProduct().getName());
				data[i][2] = String.valueOf(orderItem.getQuantity());
				data[i][3] = String.format("%.2f", orderItem.getProduct().getPrice());
				data[i][4] = String.format("%.2f", orderItem.getTotalPriceOfItems());
		}
		
		//Categorize column based on different category
		setDataVector(data, new String[] {"Index", "Item Name", "Quantity", 
				"Unit Price (RM)", "Total Price (RM)"});
		
	}

	/**
	 * This method is only allow the second column which is price to be editable
	 * 
	 * @param row, column
	 * 
	 * @return false boolean
	 */
	@Override	
	public boolean isCellEditable(int row, int column) {
		
		if (column == 2)
			return true;
		
		return false;
		
	}
	
}
