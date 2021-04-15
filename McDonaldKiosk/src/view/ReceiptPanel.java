package view;

import java.awt.Font;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.Order;
import model.OrderItem;

public class ReceiptPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * This constructor add the text area to the panel
	 * 
	 * @param order
	 * @param orderNumber
	 * @param dineInOrTakeAway
	 * @param date
	 * 
	 * @throws IOException
	 */
	public ReceiptPanel(Order order, String orderNumber, String dineInOrTakeAway, 
			String date) throws IOException 
	{
		this.setSize(300, 200);
		this.add(generateTextArea(order, orderNumber, dineInOrTakeAway, date));
		this.setVisible(true);
	}
	
	
	/**
	 * This method generate the text area and return the whole order list to the panel
	 * 
	 * @param order
	 * @param orderNumber
	 * @param dineInOrTakeAway
	 * @param date
	 * @return
	 * @throws IOException
	 */
	private JTextArea generateTextArea(Order order, String orderNumber, 
			String dineInOrTakeAway, String date) throws IOException
	{
		JTextArea txtArea = new JTextArea();
		
		//setting for the text Area
		txtArea.setFont(new Font("Consolas", Font.BOLD, 37));
		txtArea.setText("----------------------------------------------------------");
		txtArea.setText(txtArea.getText() + "\nYour Order number is: " 
		+ orderNumber + "("+ dineInOrTakeAway +")");
		txtArea.setText(txtArea.getText() + "\n----------------------------------------------------------\n");
		txtArea.setText(txtArea.getText() + "\n"+ date);
		
		//Retrieve the order from the order list
		for(int index = 0; index < order.getOrderList().size(); index++)
		{
			
			OrderItem orderItem = order.getOrderList().get(index);
			int itemLength = String.valueOf(orderItem.getProduct().getName()).length();
			int quantityLength = String.valueOf(orderItem.getQuantity()).length();
			
			char space[] = {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
							' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
							' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
							' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
							' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',};
			
			orderItem.getProduct().getName().getChars(0, itemLength, space, 0);
			
			String x = "x";
			x.getChars(0, 1, space, itemLength + 1);
			
			String.valueOf(orderItem.getQuantity()).getChars(0, quantityLength,
					space, itemLength + 3);
			String combine = new String();
			
			for(char sp: space)
			{
				combine += sp;
			}
			
			txtArea.setText(txtArea.getText()+"\n" + combine + "RM " 
			+ String.format("%.2f" , orderItem.getTotalPriceOfItems()));
			
		}
		
		txtArea.setText(txtArea.getText()+"\n----------------------------------------------------------");
		String combine = new String();
		
		char space[] = {'T','o','t','a','l',' ','P','r','i','c',
						'e',' ',' ',' ',' ',' ',' ',' ',' ',' ',
						' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
						' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
						' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
		
		for(char sp: space)
			combine += sp;
		
		txtArea.setText(txtArea.getText()+"\n" + combine + "RM " 
		+ String.format("%.2f" , order.getTotalPrice()) + "\n");
		
		return txtArea;
	}
	
}
