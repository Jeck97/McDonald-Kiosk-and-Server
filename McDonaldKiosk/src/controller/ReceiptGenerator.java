package controller;

import java.awt.print.PrinterException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.OrderItem;

/**
 * This class is to generate receipt for customer
 * 
 * @author lesli
 *
 */
public class ReceiptGenerator {
	
	DecimalFormat df2 = new DecimalFormat("#.00");
	
	private void write(String data) throws IOException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
			FileWriter fileWriter = new FileWriter(sdf.format(new Date()) +".txt", true);
			fileWriter.write(data);
			fileWriter.close();
	}
	
	public void printTextFile(Order order, String orderNumber, String dineInOrTakeAway, String date) throws PrinterException, IOException
	{
		write	   (			  "--------------Gerbang Alaf Restaurant Sdn Bhd-------------\n"
				+ 				  "                          McDonald                          \n");
		write("----------------------------------------------------------");
		write("\nYour Order number is: " + orderNumber + "("+ dineInOrTakeAway +")");
		write("\n----------------------------------------------------------\n");
		write("\n"+ date);
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
			String.valueOf(orderItem.getQuantity()).getChars(0, quantityLength, space, itemLength + 3);
			String combine = new String();
			for(char sp: space)
			{
				combine += sp;
			}
			write("\n" + combine + "RM " + df2.format(orderItem.getTotalPriceOfItems()));
		}
		write("\n----------------------------------------------------------");
		String combine = new String();
		char space[] = {'T','o','t','a','l',' ','P','r','i','c',
						'e',' ',' ',' ',' ',' ',' ',' ',' ',' ',
						' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
						' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',
						' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
		for(char sp: space)
			combine += sp;
		write("\n" + combine + "RM " + df2.format(order.getTotalPrice()) + "\n");
	}
	
}
