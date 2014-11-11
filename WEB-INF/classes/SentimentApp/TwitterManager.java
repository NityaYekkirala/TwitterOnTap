/* This is the core class of the web application. It deals with grabbing the tweets from Twitter using the Twitter4j API
 * and then performing sentiment analysis on them using a Sentiment Classifier object. Furthermore, the tweets themselves
 * get temporarily stored in the mySQL database for each instance of the class (i.e. each instance of a search). For each search, the overall 
 * polarity of the tweets (i.e. the amount of positive, negative and neutral tweets) gets counted in order to be returned to the user via 
 * the web-based interface.  */
package SentimentApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import SentimentApp.RedirectSystemOut;
import SentimentApp.SentimentClassifier;
import SentimentApp.SentimentPercentageCalculator;

public class TwitterManager {
	

	public static void main (String args[]) throws ClassNotFoundException, IOException, SQLException   { 
			
	}
	
	
	static double LIMIT = 100; // number of tweets to be grabbed from Twitter for each instance of a search
	//these counters count the number of pos, neg and neu tweets per 100 grabbed
	static double positiveCounter =0; 
	static double negativeCounter=0;
	static double neutralCounter=0;
	// these two variables will be available to the jsp page to display results
	public static double toReturnPos = 0; 
	public static double toReturnNeg = 0;
    // these two variables are from the twitter4j API 
	ConfigurationBuilder cb;
	Twitter twitter;
	
	public TwitterManager () throws IOException { /*create a TwitterManager object that creates 
	                                               * a connection between Twitter4j API and Twitter API */ 
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("Zl1CreX4IdOo8L9LeIy3v8AVG");
        cb.setOAuthConsumerSecret("tLQAGmWf4jf62LPSn4GrpDc9HTE46U0smSdAdL5FvXmw1glhKR");
        cb.setOAuthAccessToken("2742435007-u5gOESzggSW4Gd1GdhIqGkLQOrW8P9jou1OHHBm");
        cb.setOAuthAccessTokenSecret("xDjMOVCQzZsfDDt4zrg7GvvAhBusqaoXHrPWxZ4aGEnXz"); 
        
        twitter = new TwitterFactory(cb.build()).getInstance();
	}
	
	// this method grabs 100 tweets from Twitter that match the keyword parameter provided by the user and analyzes their sentiment
	public  String performQuery(String keyword) throws ClassNotFoundException, IOException, SQLException  {
		SentimentClassifier sentimentClassifier = new SentimentClassifier(); // used to classify the polarity of each tweet
		// a Twitter4j Query object finds tweets according to the keyword provided and the necessary filter parameters
		Query query = new Query(keyword + " -filter:retweets -filter:links -filter:replies -filter:images");
		query.setCount(100);
		query.setLang("en");
        Connection connection = JDBC.databaseConnect(); // establishes a connection to the mySQL database
        JDBC.clearTables(connection); // clears the tables that temporarily store the positive and negative tweets for each instance of a search
        
		
		
		
		
		try {
			int count=0;
			// this is a Twitter4j object which holds the result of each instance of the Query object
			QueryResult result;
			
			do {
				
				result = twitter.search(query);
				ArrayList ts = (ArrayList) result.getTweets(); // an array to hold the 100 QueryResult objects
				
				for (int i = 0; i < ts.size() && count < LIMIT; i++) {
					count++;
					// The Status object holds the tweet and its pertinent information. A new one is created for each QueryResult objected stored in the array
					Status tweet = (Status) ts.get(i);
					
					// all of these variables store information about each Status object (i.e. each tweet). For example, the content of the tweet, the username,etc...
					String uncleanText = tweet.getText();
					String text = SentimentPercentageCalculator.cleanText(uncleanText); // gets rid of unwanted characters, e.g. " ' ", etc...
					
					String uncleanName = tweet.getUser().getScreenName();
					String name = SentimentPercentageCalculator.cleanText(uncleanName); // gets rid of unwanted characters, e.g. " ' ", etc...
					
					long userId = tweet.getId();
					
					String uncleanProfileLocation = tweet.getUser().getLocation();
					String profileLocation = SentimentPercentageCalculator.cleanText(uncleanProfileLocation); // gets rid of unwanted characters, e.g. " ' ", etc...
					
					// classifies the polarity of the tweet. Returns 'pos', 'neg', neu' for positive, negative, and neutral tweets respectively
					String uncleanSentiment = sentimentClassifier.classify(tweet.getText());
					String sentiment = SentimentPercentageCalculator.cleanText(uncleanSentiment); // gets rid of unwanted characters, e.g. " ' ", etc...
					
					String uncleanImageURL = tweet.getUser().getProfileImageURL();
					String imageURL = SentimentPercentageCalculator.cleanText(uncleanImageURL); // gets rid of unwanted characters, e.g. " ' ", etc...
					
					
					
					// these Strings hold the SQL statements needed to send tweets to the necessary table in the databse, depending on their respective polarities
					String SQLpos = "INSERT INTO positivetweets " + "(tweetcontent, username, tweetID, location, sentiment, userpicURL) VALUES (" + "'" + text + "'" + "," + "'" + 
			        		name + "'" + "," + "'" + userId + "'" + "," + "'" + profileLocation + "'" + "," + "'" + sentiment + "'" + "," + "'" + imageURL + "'" + ")" ;
					String SQLneg = "INSERT INTO negativetweets " + "(tweetcontent, username, tweetID, location, sentiment, userpicURL) VALUES (" + "'" + text + "'" + "," + "'" + 
			        		name + "'" + "," + "'" + userId + "'" + "," + "'" + profileLocation + "'" + "," + "'" + sentiment + "'" + "," + "'" + imageURL + "'" + ")" ;
					
					// a simple pattern matching algorithm checks to see which sentiment category the tweet belongs to. 1 for a match, 0 for no match
					int isPos = SentimentPercentageCalculator.bruteForceSearch (sentiment, "pos");
					int isNeg = SentimentPercentageCalculator.bruteForceSearch (sentiment, "neg");
					int isNeu = SentimentPercentageCalculator.bruteForceSearch (sentiment, "neu");
					
                     if (isPos == 1 ) {
                    	 
                    	 JDBC.sendToDatabase(SQLpos, connection);
                    	  positiveCounter++;
                 		
					}
                     
                     
					
                     if (isNeg == 1) {
                    	 
                    	 JDBC.sendToDatabase(SQLneg, connection);
                    	 negativeCounter++;
	
                    }

					
					if (isNeu == 1) {
					 
						neutralCounter++;
						
					}
					
		}
				
				
				} while ((query = result.nextQuery()) != null && count < LIMIT);
			}
		catch (TwitterException twitterException) {
			System.out.println("Couldn't connect to Twitter: " + twitterException);
		}
		// these String objects hold the results of each query and will be returned and displayed on the web-interface via jsp
		String positive = ("Total Positive Sentiment: " + SentimentPercentageCalculator.calculatePercentage(positiveCounter, (LIMIT-neutralCounter)) + "%");
		String negative = ("Total Negative Sentiment: " + SentimentPercentageCalculator.calculatePercentage(negativeCounter, (LIMIT-neutralCounter)) + "%");
		
		// these public static variable are made available so they can be used by the jsp page as input parameters for the creation of A JFreeChart Pie Chart
		 toReturnPos = SentimentPercentageCalculator.calculatePercentage(positiveCounter, (LIMIT-neutralCounter));
		 toReturnNeg = SentimentPercentageCalculator.calculatePercentage(negativeCounter, (LIMIT-neutralCounter));
		
		 // reset counters in advance of next instance of a search being executed by a user
		positiveCounter=0;
		negativeCounter=0;
		neutralCounter=0;
		
		// the code below sends meta data about each instance of a search to the database for future reference
		String keywordLower = keyword.toLowerCase();// all keywords should be sent to the database in lower case letters
		long currentTime = System.currentTimeMillis();
		Date date = new Date(currentTime);
		String theDate = SentimentPercentageCalculator.dateFormat(date);
		
		String SQLtweetStats = "INSERT INTO tweetstats " + "(twittertopic, sentiment, timeanddate) VALUES (" + "'" + keywordLower + "'" + "," + "'" + toReturnPos + "'" 
				                + "," + "'" + theDate  + "'" +")";
		
		JDBC.sendToDatabase(SQLtweetStats, connection);
		
		connection.close(); // close database connection at end of session
		return positive + "<br>" + negative + "<br>";
		
		
	}

}

