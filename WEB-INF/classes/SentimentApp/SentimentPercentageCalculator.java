/* This class contains a number of methods used when, given an instance of the TwitterManager class (i.e. the retrieval of 100 tweets from Twitter
 * which match a keyword provided by the user), the percentage of positive and negative tweets returned. Methods from this class are mostly 
 * implemented inside the TwitterManager class  */
package SentimentApp;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SentimentPercentageCalculator {
	
	SentimentPercentageCalculator() {
		
	}
	// simple method to calculate a percentage given a total variable and another variable
	public static double calculatePercentage (double score, double total) {
		
		double result = (score* 100) / total;
		
		double adjustedResult = round(result);
		
		return adjustedResult;
		
	}
	
	// rounds of a doulbe variable to decimal places
	public static double round (double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
		
	/* simple pattern matching algorithm to see if a an input matches a desired 
	 * String (i.e. does the SentimentClassifier return 'pos', 'neg' or 'neu' */
public static int bruteForceSearch (String T, String S) {
		
		for (int x=0; x < T.length(); x++) {
			if (S.charAt(x) == T.charAt(x)) {
				
			}
			
			else {
				return -1;
			}
			
		}
			
			return 1;
			
		}

// removes certain characters from a string
public static String cleanText (String text) {
	
	String original = text;
	String firstClean = original.replace("'", ""); 
	String secondClean = firstClean.replace("','","");
	return secondClean;
	
	
}

// changes the format of a date. used to change dates before they are sent to the database
public static String dateFormat (Date date) {
	Date date1 = date;
	DateFormat df = new SimpleDateFormat("yyyy/MM/dd");  
	String text = df.format(date1);  
	
	return text;
	
}




}
