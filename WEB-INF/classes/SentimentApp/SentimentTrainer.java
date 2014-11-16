/* This class trains an LM classifier using the Sanders Training tweet corpus. It contains a method called 
 * trainModel() which performs this task. The method trains the classifier on the the Training directory provided 
 * (which contains three csv files containing positive, negative and neutral annotated tweets respectively) and
 * create a .txt file ("classifeier.txt") in response which is the used to create a SentimentClassifer object 
 * (see sentimentClassifier.java) which is then used to classify the polarity of objects in the web application.
 * The class was implemented locally during creation of the application and the resultant 'classifier.txt" was 
 * then placed in the context path of the web application to be used by the SentimentClassifer class. In other 
 * words, once the web application is deployed to the live server, this class and the training tweet corpus are no 
 * longer actively used as resources by the application.
 *   
 */
package SentimentApp;

import java.io.File;
import java.io.IOException;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.LMClassifier;
import com.aliasi.corpus.ObjectHandler;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Compilable;
import com.aliasi.util.Files;


public class SentimentTrainer 
{

    public static void main(String[] args) throws ClassNotFoundException, IOException  {
    	
            trainModel();
        }
        
    public static void trainModel()throws IOException, ClassNotFoundException 
    {
    	// these methods send the collection of positive, negative and neutral training tweets to the necessary directories
    	CSVreader.CSVrowsToPosFile("*****");
    	CSVreader.CSVrowsToNegFile("*****");
    	CSVreader.CSVrowsToNeuFile("*****");
		
        // create a file object which will store the files containing the positive, negative and neutral training tweets respectively
        File trainDirectory; 
        String[] categories; // create an array which will store the 3 categories; positive, negative and neutral
        // create an LMClassifier object
        LMClassifier classifier;
        // this assigns the file object to the previously created directory which contains three sub-folders; 'pos', 'neg' and 'neutral'
        trainDirectory = new File("*****");
        categories = trainDirectory.list(); // the array now holds the three folders (it also includes an invisible .DS_Store file
        int nGram = 7; //the nGram level
        classifier = DynamicLMClassifier.createNGramProcess(categories,nGram);
        // int i != 0 because we want to skip the invisible .DS_Store file
        for (int i = 1; i < categories.length; ++i) {
        	
        String category = categories[i];// assign the first category, e.g. the 'pos' category
       // create a classification object which will be one of 3 categories (positive, negative, or neutral) 
        Classification classification = new Classification(category);
        File file = new File(trainDirectory, categories[i]); 
        // creates an array of all the .txt files which make up the respective tweets for a given category
        File[] trainFiles = file.listFiles();
        
        for (int j = 0; j < trainFiles.length; ++j) {
        	
        File trainFile = trainFiles[j];
        String review = Files.readFromFile(trainFile, "ISO-8859-1"); // use the required encoding
        Classified classified = new Classified(review, classification);
        ((ObjectHandler) classifier).handle(classified); 
        }
        
        }
        // this compiles the classifier file ('classifier.txt') and sends it to the required local location
        AbstractExternalizable.compileTo((Compilable) classifier, new File("*****"));
        }


}