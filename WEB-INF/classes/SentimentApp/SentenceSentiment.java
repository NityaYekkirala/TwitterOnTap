/* This class analyzes one sentence (a String object) a returns its polarity (i.e. positive, negative, or neutral.
 * Like the TwitterManager class, it uses a SentimentClassifier object to analyze each sentence that it receives. It contains
 * a method called analyseSentence() which takes the sentence String provided by the user a s a parameter and creates
 *  the SentimentClassifier object to analyze the sentence */
package SentimentApp;

import java.io.IOException;
import java.util.Scanner;

public class SentenceSentiment {
	
	SentenceSentiment() {
		
	}
	
	public static void main (String [] args) throws IOException, ClassNotFoundException {
		
		
	}
	
	public static String analyseSentence(String Sentence) throws ClassNotFoundException, IOException {
		SentimentClassifier sentimentClassifier = new SentimentClassifier();
		// stores the sentiment polarity of the classified sentence
		String sentiment = sentimentClassifier.classify(Sentence);
		
		// a simple pattern matching algorithm checks to see which sentiment category the tweet belongs to. 1 for a match, 0 for no match
		int isPos = SentimentPercentageCalculator.bruteForceSearch (sentiment, "pos");
		int isNeg = SentimentPercentageCalculator.bruteForceSearch (sentiment, "neg");
		int isNeu = SentimentPercentageCalculator.bruteForceSearch (sentiment, "neu");
		// returns the necessary String result to be displayed via the web-interface
		if (isPos == 1) {
		 String pos = "positive";
		 return pos;
			
		}
		
		else if (isNeg == 1) {
			 String neg = "negative";
			 return neg;
				
			}
		
		else {
			 String neu = "neutral";
			 return neu;
				
			}
		
		
	}
}