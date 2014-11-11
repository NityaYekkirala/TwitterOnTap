/* This class analyzes the distribution of sentiment across sentences/items in a .txt document. Like the TwitterManager class, it creates
 * a SentimentClassifier object which analyzes each sentence/item in a document sequentially. It contains a method called returnDocumentSentiment() 
 * which takes in a .txt document as a parameter and counts how many positive, negative and neutral tweets are in that document. It then uses a
 * BufferedReader object to iterate through each separate item in the file */
package SentimentApp;

import java.io.BufferedReader;
import java.io.*;
import java.io.FileReader;
import java.io.IOException;

public class DocumentSentiment {
	
	
	
	public static void main (String [] args) throws ClassNotFoundException, IOException {
		
		
		
	}
	
	// counts the number of positive, negative, and neutral tweets in the file respectively
	static int posCount = 0;
	static int negCount = 0;
	static int neuCount = 0;
	// public variables which can be accessed by the jsp page to use as parameters for a JFreeChart Bar Chart via the web-interface
	public static int toReturnPos = 0;
	public static int toReturnNeg = 0;
	public static int toReturnNeu=0;

	public String returnDocumentSentiment (File textLocation) throws IOException, ClassNotFoundException {
		
		
		toReturnPos=0;
		toReturnNeg=0;
		toReturnNeu=0;
		
		SentimentClassifier sentimentClassifier = new SentimentClassifier();
		// create a BufferedReader object used to iterate through the lines in the .txt file
		BufferedReader br = new BufferedReader(new FileReader(textLocation));
		String line;
		
          int positivecounter = 0;
          int negativecounter = 0;
          int neutralcounter = 0;
		
		// iterates through the file
		while ((line = br.readLine()) != null) {
		  
		   if (line.isEmpty()) {
			   continue;
		   }
		   // classify the polarity of a given sentence in the .txt file
		   String sentiment = sentimentClassifier.classify(line);
		   
		   // a simple pattern matching algorithm checks to see which sentiment category the tweet belongs to. 1 for a match, 0 for no match
		    int isPos = SentimentPercentageCalculator.bruteForceSearch (sentiment, "pos");
			int isNeg = SentimentPercentageCalculator.bruteForceSearch (sentiment, "neg");
			int isNeu = SentimentPercentageCalculator.bruteForceSearch (sentiment, "neu");
		   
			if (isPos == 1) {
				posCount++;
			}
			
			else if (isNeg == 1) {
        	   negCount++;
			}

			else if (isNeu == 1) {
        	   neuCount++;
            }
           
		   }
		
		br.close(); // close the BufferedReader object
		
		
		toReturnPos =posCount;
		toReturnNeg=negCount;
		toReturnNeu=neuCount;
		
		// use these variables to return results to user
		positivecounter= posCount;
		negativecounter= negCount;
		neutralcounter= neuCount;
		
		// reset counters to 0 in advance of next document supplied by the user
		 posCount=0;
		 negCount=0;
		 neuCount=0;
		 
		
		// return the String displaying results for the user
		return "There were " + positivecounter  + " positive sentiment items." + "<br>" + "There were " + negativecounter + " negative sentiment items." 
		+ " <br>" + "There were " + neutralcounter + " neutral sentiment items."  ;
		
	}
}
