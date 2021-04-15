package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import model.Product;

/**
 * This class manage the menu 
 * @author zackt
 *
 */
public class Menu {

	private ArrayList<Product> menu = new ArrayList<>();
	
	/**
	 * This constructor retrieve menu detail from menu text file
	 * 
	 */
	public Menu() {
		
		try {
			
			File menuFile = new File("Mc Donald's Menu.txt");
			FileReader menuReader = new FileReader(menuFile);
			
			@SuppressWarnings("resource")
			BufferedReader menuBufferReader = new BufferedReader(menuReader);
			
			String productDetails;
			
			while ((productDetails = menuBufferReader.readLine()) != null) {
				
				this.menu.add(generateProduct(productDetails));
				
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}

		
	}
	
	
	/**
	 * This method generate the product 
	 * 
	 * @param productDetails
	 * 
	 * @return product object
	 */
	public Product generateProduct(String productDetails) {
		
		StringTokenizer tokenizer = new StringTokenizer(productDetails, ";");

		Product product = new Product();
		
		product.setName(tokenizer.nextToken());
		product.setPrice(Double.parseDouble(tokenizer.nextToken()));
		product.setType(tokenizer.nextToken());
		
		return product;
		
	}
	
	
	/**
	 * This method return the menu list
	 * 
	 * @return menu collection
	 */
	public ArrayList<Product> getMenu() {
		
		return new ArrayList<Product>(menu);
		
	}
	
}
