package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.McDonaldKioskApplication;
import controller.Menu;
import controller.Order;
import controller.ReceiptGenerator;
import model.CartTableModel;
import model.OrderItem;
import model.Product;

/**
 *  This class represent the window for the client side McDonald Kiosk Application.
 *  It display the menu and allow user to make their order
 *  
 * @author zackt
 *
 */
public class McDonaldKioskFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	//Private attribute for frame size
	private int width = 1200;
	private int height = 800;	

	//Private global panel that can be reused
	private JPanel pnlCenterSelection;

	//Private frame component for confirmPanel
	JLabel lblCalculatedPrice;

	//Private frame components for pnlCenterSelection
	private JButton btnBurger = new JButton("Burger");
	private JButton btnDrink = new JButton("Drink");
	private JButton btnSide = new JButton("Side");
	private JButton btnDessert = new JButton("Dessert");
	private JButton btnCart = new JButton("Cart");
	private JButton btnConfirm = new JButton("Confirm");
	private JButton btnTakeAway = new JButton();

	//Private frame component for pnlCart
	private JScrollPane tableCartContainer;
	private JTable tableCart;
	private JButton btnReset = new JButton("Reset");

	//Private frame component for pnlQuantity
	private JTextField txtQuantity;

	private Menu menu = new Menu();
	private Order order = new Order();

	/**
	 * The constructor organize the GUI component for the window
	 * 
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public McDonaldKioskFrame() throws ClassNotFoundException, InstantiationException,
	IllegalAccessException, UnsupportedLookAndFeelException {

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		//Default frame setting
		this.setLayout(new BorderLayout());
		this.setTitle("McDonalds Kiosk");
		this.setSize(new Dimension(width, height));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		//Center the frame on the screen
		this.setLocationRelativeTo(null);

		//Organize components
		this.loadComponent();
	}

	/**
	 * This method create and arrange the Swing component 
	 * 
	 * @param font
	 * 
	 * @return Swing components organized in panel
	 */
	private JPanel getTitlePanel(Font font) {

		//New panel for title
		JPanel panel = new JPanel();

		//set background of the panel
		panel.setBackground(Color.RED);

		//initialize component
		JLabel lblTitle = new JLabel("McDonalds");

		//setting for the label
		lblTitle.setFont(this.getFontStyle(50));
		lblTitle.setForeground(Color.YELLOW);

		//setting for button Take Away
		btnTakeAway.setText("Take Away");
		btnTakeAway.setFont(getFontStyle(50));
		btnTakeAway.addActionListener(this);

		//add component into the panel
		panel.add(lblTitle);
		panel.add(btnTakeAway);
		return panel;

	}


	/**
	 * This method create and arrange the Swing component
	 * 
	 * @param font
	 * 
	 * @return Swing component organized in panel
	 */
	private JPanel getCatogeryPanel(Font font) {

		//New panel for categoryPanel
		JPanel panel = new JPanel();

		//Default setting for categoryPanel
		panel.setBackground(Color.GRAY);
		panel.setLayout(new GridLayout(6, 1, 10, 10));

		//Setting for every button and add component into the panel
		btnBurger.setFont(font);
		btnBurger.addActionListener(this);
		panel.add(btnBurger);

		btnDrink.setFont(font);
		btnDrink.addActionListener(this);
		panel.add(btnDrink);

		btnSide.setFont(font);
		btnSide.addActionListener(this);
		panel.add(btnSide);

		btnDessert.setFont(font);
		btnDessert.addActionListener(this);
		panel.add(btnDessert);

		btnCart.setFont(font);
		btnCart.addActionListener(this);
		panel.add(btnCart);

		return panel;

	}


	/**
	 * This method create and arrange the Swing component
	 * 
	 * @param font
	 * 
	 * @return Swing component organized in panel
	 */
	private JPanel getConfirmPanel(Font font) {

		//New panel for confirmPanel
		JPanel panel = new JPanel();

		//Default setting for confirmPanel
		panel.setBackground(Color.BLACK);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		//Initialize component
		JLabel lblTotalPrice = new JLabel("Total Price: ");

		//Setting for component
		lblTotalPrice.setFont(font);
		lblTotalPrice.setForeground(Color.WHITE);

		//Add component into the panel
		panel.add(lblTotalPrice);

		//Initialize component
		lblCalculatedPrice = new JLabel("RM 0.00");

		//Setting for component
		lblCalculatedPrice.setFont(font);
		lblCalculatedPrice.setForeground(Color.WHITE);

		//Add component into the panel
		panel.add(lblCalculatedPrice);

		//Add action listener for Confirm button
		btnConfirm.addActionListener(this);
		btnConfirm.setFont(font);

		//Add component into the panel
		panel.add(btnConfirm);

		return panel;

	}

	/**
	 * This method create and arrange the Swing component
	 * 
	 * @param font
	 * @param category
	 * 
	 * @return Swing component organized in panel
	 */
	private JPanel getSelectionPanel(Font font, String category) {

		//New panel for selectionPanel
		JPanel panel = new JPanel();

		//Default setting for selectionPanel
		panel.setBackground(Color.WHITE);
		panel.setLayout(new GridLayout(3, 3));

		//Retrieve menu and generate button
		ArrayList<Product> menuList = menu.getMenu();
		int menuSize = menuList.size();

		for (int index = 0; index < menuSize; index ++) {

			String menuType = menuList.get(index).getType();
			if (menuType.equals(category)) {

				JButton button = buttonGenerator(menuList.get(index));
				panel.add(button);

			}

		}

		return panel;

	}

	/**
	 * This method create and arrange the Swing component 
	 * 
	 * @param font
	 * 
	 * @return Swing component organized in panel
	 */
	private JPanel getCartPanel(Font font) {

		//New panel for cartPanel
		JPanel panel = new JPanel();

		//Default setting for cartPanel
		panel.setBackground(Color.WHITE);
		panel.setLayout(new BorderLayout());

		//Initialize a new panelButton for button
		JPanel panelButton = new JPanel();

		//Setting for panelButton
		panelButton.setLayout(new FlowLayout(FlowLayout.RIGHT));

		//Add action listener to each button
		btnReset.addActionListener(this);

		btnReset.setFont(font);

		//Add component into the panelButton
		panelButton.add(btnReset);

		//Initialize a JTable for cart
		tableCart = new JTable();

		//Setting for tableCart
		tableCart.setFont(this.getFontStyle(25));
		tableCart.setRowHeight(tableCart.getRowHeight()+20);

		//Set the model of the table cart
		tableCart.setModel(new CartTableModel(order));

		//Not allow the table to be resize or drag across column
		tableCart.getTableHeader().setResizingAllowed(false);
		tableCart.getTableHeader().setReorderingAllowed(false);
		tableCart.getTableHeader().setFont(this.getFontStyle(25));

		//Initialize a scrollpane for cart
		tableCartContainer = new JScrollPane(tableCart);

		//Add component into the panel
		panel.add(tableCartContainer, BorderLayout.CENTER);
		panel.add(panelButton, BorderLayout.SOUTH);

		return panel;

	}

	/**
	 * This method create and arrange the Swing component 
	 * 
	 * @return Swing component organized in panel
	 */
	private JPanel getQuantityPanel() {


		//Initialize component for the JOptionDialog
		JButton minusButton = new JButton("-");

		//Setting for the component
		minusButton.setFont(this.getFontStyle(22));

		//Initialize component for the JOptionDialog
		JButton addButton = new JButton("+");

		//Setting for the component
		addButton.setFont(this.getFontStyle(22));

		//New panel to add component
		JPanel panel = new JPanel();

		//Add component into the panel
		panel.add(minusButton);
		txtQuantity = new JTextField(5);
		txtQuantity.setText("1");

		//Set the text field become not editable
		txtQuantity.setEditable(false);

		//Setting for the text field
		txtQuantity.setHorizontalAlignment(JTextField.CENTER);
		txtQuantity.setFont(this.getFontStyle(25));

		//Add component to the panel
		panel.add(txtQuantity);
		panel.add(addButton);

		//Add function to each button 
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numberOfNumber = Integer.parseInt(txtQuantity.getText());
				int finalNumber = numberOfNumber+1;
				txtQuantity.setText(String.valueOf(finalNumber));
			}
		});

		minusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numberOfNumber = Integer.parseInt(txtQuantity.getText());
				if (numberOfNumber >1)
				{
					int finalNumber = numberOfNumber-1;
					txtQuantity.setText(String.valueOf(finalNumber));
				}
			}
		});

		return panel;
	}


	/**
	 * This method create button that represent the item
	 *  selection for customer
	 *  
	 * @param menu
	 * @param buttonName
	 * 
	 * @return button with corresponding item name,price and icon
	 */
	private JButton buttonGenerator(Product product) {

		//Initialize component
		String productName = product.getName();
		String iconPath = product.getType() + "\\" + productName + ".jpg";
		String itemNameAndPrice = productName + " RM " + String.format("%.2f",
				product.getPrice());

		Icon itemIcon = new ImageIcon(iconPath);
		JButton button = new JButton(itemNameAndPrice, itemIcon);

		//Setting for button
		button.setName(productName);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.addActionListener(this);

		return button;

	}

	/**
	 * This method arrange the Swing components on the frame
	 */
	private void loadComponent() {

		//Get font
		Font font = this.getFontStyle(50);

		//Get the titlePanel and add to frame
		JPanel topPanel = this.getTitlePanel(font);
		this.add(topPanel, BorderLayout.NORTH);

		//Get categoryPanel and add to frame
		JPanel leftPanel = this.getCatogeryPanel(font);
		this.add(leftPanel, BorderLayout.WEST);

		//Get confirmPanel and add to frame
		JPanel bottomPanel = this.getConfirmPanel(font);
		this.add(bottomPanel, BorderLayout.SOUTH);

		//Get pnlCenterSelection and add to frame
		pnlCenterSelection = this.getSelectionPanel(font, "Burger");
		this.add(pnlCenterSelection);
		
	}

	
	/**
	 * This method define a font to a generic style
	 * 
	 * @return font object
	 */
	private Font getFontStyle(int fontSize) {
		
		//initialize the font 
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, fontSize);

		return font;

	}

	
	/**
	 * This actionPerformed is to know which button is being clicked
	 * 
	 * @param e
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		Font font = this.getFontStyle(50);

		//When the take away button is click
		if(source == btnTakeAway)
		{
			if(btnTakeAway.getText()=="Take Away")
			{

				btnTakeAway.setText("Dine-In");

			} else {

				btnTakeAway.setText("Take Away");

			}

		}
		
		//When button Burger is click
		else if (source == btnBurger)
		{
			this.remove(pnlCenterSelection);
			pnlCenterSelection = this.getSelectionPanel(font,"Burger");
			this.add(pnlCenterSelection, BorderLayout.CENTER);
			this.repaint();
			this.revalidate();
		}

		//When button Drink is click
		else if (source == btnDrink)
		{
			this.remove(pnlCenterSelection);
			pnlCenterSelection = this.getSelectionPanel(font,"Drink");
			this.add(pnlCenterSelection, BorderLayout.CENTER);
			this.repaint();
			this.revalidate();
		}

		//When button Side is click
		else if (source == btnSide)
		{
			this.remove(pnlCenterSelection);
			pnlCenterSelection = this.getSelectionPanel(font,"Side");
			this.add(pnlCenterSelection, BorderLayout.CENTER);
			this.repaint();
			this.revalidate();
		}

		//When button Dessert is click
		else if(source == btnDessert)
		{
			this.remove(pnlCenterSelection);
			pnlCenterSelection = this.getSelectionPanel(font,"Dessert");
			this.add(pnlCenterSelection, BorderLayout.CENTER);
			this.repaint();
			this.revalidate();
		}

		//When button Cart is click
		else if(source == btnCart)
		{
			this.remove(pnlCenterSelection);
			pnlCenterSelection = this.getCartPanel(font);
			this.add(pnlCenterSelection, BorderLayout.CENTER);
			this.repaint();
			this.revalidate();
		}

		else if(source == btnReset)
		{
			//check whether the order list is empty
			if(order.isEmpty())
			{
				JOptionPane.showMessageDialog(this, "Your cart is empty.");
			}
			else 
			{
				//A option dialog will display and ask for decision
				int result = JOptionPane.showConfirmDialog(this, "Are you sure "
						+ "want to reset your cart?", "McDonald Kiosk",
						JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) 
				{
					order.clearOrderList();
					tableCart.setModel(new CartTableModel(order));
					lblCalculatedPrice.setText("RM 0.00");
				}

			}



		}
		
		//When button confirm is click, it also manage the connection
		else if (source == btnConfirm)
		{
			//check whether the order list is empty
			if(order.isEmpty())
			{
				//display message
				JOptionPane.showMessageDialog(this, "You haven't choose any item yet.");
			}
			else
			{
				//display option dialog and ask for decision
				int result = JOptionPane.showConfirmDialog(this, "Are you sure "
						+ "want to submit your order?", "McDonald Kiosk",
						JOptionPane.YES_NO_OPTION);

				//if the user choose yes
				if (result == JOptionPane.YES_OPTION) 
				{
					String orderNumber = null;
					Date date = new Date();
					
					//intialize the date format
					SimpleDateFormat sdf = new SimpleDateFormat("EEE yyyy MMM dd HH:mm");
					
					try 
					{
						// Connect to the server @ localhost, port 4228
						Socket socket = new Socket(InetAddress.getLocalHost(), 4228);

						//Receive order number from server
						DataInputStream inputStream = 
								new DataInputStream(socket.getInputStream());

						orderNumber = inputStream.readUTF();

						DataOutputStream outputStream = 
								new DataOutputStream(socket.getOutputStream());

						// Collect all the order in a string
						String responseToServer = "";


						for(int index = 0; index < order.getOrderList().size(); index++)
						{
							OrderItem orderItem = order.getOrderList().get(index);

							responseToServer += orderItem.getProduct().getName() + ":" + 
									orderItem.getQuantity() + ":"+
									orderItem.getProduct().getPrice() + ";";
						}

						responseToServer += btnTakeAway.getText() + ";" 
								+ lblCalculatedPrice.getText() + ";";
						outputStream.writeUTF(responseToServer);

						order.setDate(sdf.format(date));
						order.setDineInOrTakeAway(btnTakeAway.getText().toString());

						//Send the order object to server
						ObjectOutputStream objectOutputStream = 
								new ObjectOutputStream(socket.getOutputStream());

						//write the order to server
						objectOutputStream.writeObject(order);

						//close all the socket
						inputStream.close();
						outputStream.close();
						objectOutputStream.close();
						socket.close();

					} 
					catch (IOException e1) 
					{
						e1.printStackTrace();
					}


					try {

						//Generate receipt
						ReceiptGenerator receiptGenerator = new ReceiptGenerator();
						receiptGenerator.printTextFile(order, orderNumber,
								btnTakeAway.getText().toString(), sdf.format(date));

					} catch (PrinterException | IOException e2) {

						e2.printStackTrace();

					}


					try {

						JOptionPane.showMessageDialog(this, new ReceiptPanel(order,
								orderNumber, btnTakeAway.getText(), sdf.format(date)) , "Receipt", JOptionPane.PLAIN_MESSAGE, null);
					} catch (HeadlessException | IOException e1) {

						e1.printStackTrace();

					}

					//Dispose the previous frame
					this.dispose();

					try {

						//Call back the main function to open a new frame
						McDonaldKioskApplication.main(null);

					} catch (UnknownHostException | ClassNotFoundException | 
							InstantiationException | IllegalAccessException | 
							UnsupportedLookAndFeelException e1) {

						e1.printStackTrace();

					} 
				}
			}
		}

		//When a item is selected
		else
		{
			//The source is based on the button you click
			JButton sourceButton = (JButton)source;

			ArrayList<Product> menuList = menu.getMenu();
			int menuSize = menuList.size();

			for (int index = 0; index < menuSize; index++) {

				if (menuList.get(index).getName().equals(sourceButton.getName())) {

					Product selectedProduct = menuList.get(index);

					//Generate a JOptionDialog
					int result = JOptionPane.showOptionDialog(this, this.getQuantityPanel(),
							selectedProduct.getName(),
							JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
							null, new Object[] { "Confirm", "Cancel" }, null);

					int quantity = Integer.parseInt(txtQuantity.getText());

					//If the button confirm is press 
					if (result == JOptionPane.YES_OPTION) {

						//Check if the cart vector is empty
						if (order.isEmpty()) {

							order.addOrderItem(order.getOrderList().size() + 1,
									selectedProduct, quantity);
							order.setTotalPrice(calculateTotalCost());

						}
						else {

							//Loop and check if the item is already added into the cart vector
							boolean change = false;

							for (int counter = 0; counter < order.getOrderList().size(); counter++)
							{
								OrderItem orderItem = order.getOrderList().get(counter);
								if (orderItem.getProduct().getName().equals(selectedProduct.getName()))
								{
									orderItem.setQuantity(orderItem.getQuantity() + quantity);
									order.setTotalPrice(calculateTotalCost());
									change = true;
									break;
								}
							}
							if (!change) {

								order.addOrderItem(order.getOrderList().size() + 1,
										selectedProduct, quantity);
								order.setTotalPrice(calculateTotalCost());

							}
						}

						//Change the price to 2 decimal
						lblCalculatedPrice.setText("RM " + String.format("%.2f", 
								calculateTotalCost()));
					}

					break;
				}

			}

		}
	}

	/**
	 * This method calculate the total cost of the whole cart
	 * 
	 * @return totalCost
	 */
	private	double calculateTotalCost()
	{

		double totalCost = 0;

		//Calculate the total price by looping the cart
		for (int index = 0; index < order.getOrderList().size(); index++) {

			totalCost += order.getOrderList().get(index).getTotalPriceOfItems();

		}

		return totalCost;

	}
}
