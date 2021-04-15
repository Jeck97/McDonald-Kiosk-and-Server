package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class generate a text file
 * 
 * @author zackt
 *
 */
public class OutputTextFile {

	//initialize the date format
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
	
	/**
	 * This method write the order list into the text file
	 * @param data
	 * @throws IOException
	 */
	public void write(String data) throws IOException
	{
			FileWriter fileWriter = new FileWriter(sdf.format(new Date()) +".txt", true);
			fileWriter.write(data);
			fileWriter.close();
	} 
}
