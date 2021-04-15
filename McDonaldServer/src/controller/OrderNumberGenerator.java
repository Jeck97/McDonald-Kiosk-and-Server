package controller;

/**
 * This class generates order number.
 * 
 * @author zackt
 *
 */
public class OrderNumberGenerator {

	private static int orderNumber = 0;
	/**
	 * This method generates current order number.
	 * 
	 * @return order number
	 */
	
	public String getOrderNumber() {
		
		return String.valueOf(++orderNumber);
		
	}
	
}
