package controller;

import java.net.UnknownHostException;

import javax.swing.UnsupportedLookAndFeelException;

import view.McDonaldKioskFrame;

/**
 * This class launch the frame 
 * 
 * @author zackt
 *
 */

public class McDonaldKioskApplication {
	public static void main(String[] args) 
			throws UnknownHostException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		// Launch client-side frame
		McDonaldKioskFrame mcDonaldKioskFrame = new McDonaldKioskFrame();
		mcDonaldKioskFrame.setVisible(true);
		
	}

}
