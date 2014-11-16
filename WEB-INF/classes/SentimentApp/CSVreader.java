/* This class implements the CSVreader API which enables Java programs to manipulate CSV files. For this web application,
 * it was primarily used to send annotated tweets to certain directories in order to create the LM classifier and so was used in classes that, 
 * by in large, were not implemented when the application was deployed on a live server */
package SentimentApp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import SentimentApp.RedirectSystemOut;
import au.com.bytecode.opencsv.CSVReader;

public class CSVreader {
	
	
	public static void main(String [] args) throws IOException {

	
}
	/* this method sends positively annotated tweets to the necessary directory (within the "Training Directory" directory on the local machine)
	 * the location of the CSV file to read from is supplied as a parameter for this method */
	public static void CSVrowsToPosFile(String csvLocation) throws IOException {
		// clears the training directory before each instance of a classifier being trained
		RedirectSystemOut.clearDirectory("*****");
		String posFilename = csvLocation;
		CSVReader csvReader = new CSVReader(new FileReader(posFilename)); // creates a CSVReader object which will allow manipulation of a csv file
		String [] row =null;
		int counter=1;
		// sends each positive tweet to the "pos" file of the "Training Directory" directory and names it "positiveTweet1, "positiveTweet2". etc...
		while((row = csvReader.readNext()) != null) {
		    RedirectSystemOut.appendTextToFile(row[0], "*****" + counter + ".txt");
		    counter++;
		    
		}
		csvReader.close();
		
	}
	// this method sends negatively annotated tweets to the necessary directory (within the "Training Directory" directory on the local machine)
	public static void CSVrowsToNegFile(String csvLocation) throws IOException {
		RedirectSystemOut.clearDirectory("*****");
		String posFilename = csvLocation;
		CSVReader csvReader = new CSVReader(new FileReader(posFilename));
		String [] row =null;
		int counter=1;
		while((row = csvReader.readNext()) != null) {
		    RedirectSystemOut.appendTextToFile(row[0], "*****" + counter + ".txt");
		    counter++;
		    
		}
		csvReader.close();
	}
	// this method sends neutrally annotated tweets to the necessary directory (within the "Training Directory" directory on the local machine)
	public static void CSVrowsToNeuFile(String csvLocation) throws IOException {
		RedirectSystemOut.clearDirectory("*****");
		String posFilename = csvLocation;
		CSVReader csvReader = new CSVReader(new FileReader(posFilename));
		String [] row =null;
		int counter=1;
		while((row = csvReader.readNext()) != null) {
		    RedirectSystemOut.appendTextToFile(row[0], "*****" + counter + ".txt");
		    counter++;
		    
		}
		csvReader.close();
	}
	
	// this method returns a specific row from a CSV file as a String
	public static String getTweetsFromCSV (String csvLocation, int whichRow) throws IOException {
		
		String filename = csvLocation;
		CSVReader csvReader = new CSVReader(new FileReader(filename)); // create a CSVReader object
		String [] row = null;
		
		row = csvReader.readNext(); 
		
		int x = whichRow; // sets the number of rows to iterate through before returning the desired row
	    for (int i=0; i< x; i++) {
		row = csvReader.readNext();
		if (row == null) {
			continue;
		}
		}
		
		return row[0]; // return the desired row
		
	}
	
}


