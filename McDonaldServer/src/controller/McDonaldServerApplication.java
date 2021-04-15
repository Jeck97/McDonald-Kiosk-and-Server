package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import controller.Order;
import model.OrderItem;
import view.McDonaldServerFrame;

/**
 * This class launch the McDonald Server.
 * The server generates order number
 * Each connected client will received order number from the server.
 * 
 * @author zackt
 *
 */

public class McDonaldServerApplication {

	/**
	 * Main entry point to the server side application
	 * 
	 * @param args
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, 
	ClassNotFoundException {

		// Launch the server frame
		McDonaldServerFrame serverFrame = new McDonaldServerFrame();
		serverFrame.setVisible(true);

		// Binding to a port or any other port no you are fancy of
		int portNo = 4228;
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(portNo);

		OrderNumberGenerator orderNumberGenerator = new OrderNumberGenerator();

		// Counter to keep track the number of requested connection
		int totalRequest = 0;

		// Server needs to be alive forever
		while (true) {

			// Message to indicate server is alive
			serverFrame.updateServerStatus(false);

			// Accept client request for connection
			Socket clientSocket = serverSocket.accept();
			serverFrame.updateServerStatus(true);

			// Generate current date
			String orderNumber = orderNumberGenerator.getOrderNumber();

			// Create stream to write data on the network
			DataOutputStream outputStream = 
					new DataOutputStream(clientSocket.getOutputStream());

			// Send current date back to the client
			outputStream.writeUTF(orderNumber);

			//Read from network
			DataInputStream inputStream = 
					new DataInputStream(clientSocket.getInputStream());

			//Display the message receive
			String messageKiosk = inputStream.readUTF();
			serverFrame.updateRequestStatus(messageKiosk);


			//Update the request status
			serverFrame.updateRequestStatus(
					"Data sent to the client: " + orderNumber);
			serverFrame.updateRequestStatus("Accepted connection to from the "
					+ "client. Total request = " + ++totalRequest );

			ObjectInputStream objectInputStream =
					new ObjectInputStream(clientSocket.getInputStream());

			Order order = (Order) objectInputStream.readObject();


			OutputTextFile outputTextFile = new OutputTextFile();
			for(int index = 0; index < order.getOrderList().size(); index++)
			{
				OrderItem orderItem = order.getOrderList().get(index);
				outputTextFile.write(orderItem.getProduct().getName() + "_" +
						orderItem.getQuantity() + "_" +
						orderItem.getTotalPriceOfItems() + ";" );

			}
			outputTextFile.write(order.getDate() + ";"); 
			outputTextFile.write("\n");

			//close the socket
			outputStream.close();
			inputStream.close();
			objectInputStream.close();
			clientSocket.close();

		}

	}

}
