/* This class creates an LM classifier object which can then be applied to new items to ascertain their polarity. 
 * It utilizes the "classifer.txt" file (which was trained with the SentimentTrainer class) and contains 
 * the method classify() which takes a a String (e.g. a tweet) as an input parameter an analyzes its 
 * sentiment polarity (i.e. assigns the input the category it is best suited to) using to the LM classifier field   */
package SentimentApp;

import java.io.File;
import java.io.IOException;

import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;

public class SentimentClassifier {
	String[] categories;
	LMClassifier classifier;
	/* this location will refer to the path of context path of the "classifier.txt" file with the web application. 
	 * Its value is assigned by JSP pages  within the application using the getServletContext().getRealPath()
	 * methods. this is done so the "classifier.txt" file can be found on different servers that the web application
	 *  is deployed to 
	 */
	public static String location;
	
	public SentimentClassifier () throws IOException, ClassNotFoundException {
		
		
		
		try { 
			// the LM classifier is created using the classifer.txt as its training resource (see SentimentTrainer.java)
			classifier = (LMClassifier) AbstractExternalizable.readObject(new File(location));
			categories = classifier.categories(); // categories of the classifier are assigned (i.e. 'pos', neg', 'neu'
			
		}
		
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// this method is called to actually classify a given item using the LM classifier created above
	public String classify (String text) {
		ConditionalClassification classification = classifier.classify(text);
		return classification.bestCategory(); // returns the category which best suits the input string
	}

}
