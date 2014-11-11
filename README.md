TwitterOnTap
============

TwitterOnTap is a web-based java tool which grabs tweets from Twitter according to a search word and analyses 
their sentiment. The application also includes a number of additional features.

SYSTEM ARCHITECTURE
Primarily,  the web application consisted of two major elements; the Java classes which represented the 
functionality of the web application and the web resources which represented the user-interface. In order 
to create a functional web application, these two elements need to be integrated together.

JAVA CLASSES
The Java component of the web application consisted of a number of Java classes divided logically to represent 
the various functionality elements of the application. Furthermore, there were a number of additional classes 
that contained utility methods for performing various tasks across all of the classes. Below is an overview of all 
classes implemented in the web application and how they link together with each other to create the functionality 
of the web application.

SentimentTrainer.java
This class trains a LM Classifier using the Sanders Training tweet corpus. It contains a method called 
trainModel() which performs this task. The method trains the classifier and creates a .txt file (classifier.txt) 
which is then used to create a SentimentClassifier object (see SentimentClassifier.java) which actually classifies 
the polarity of items in the web application. This class was implemented locally during creation of the SA tool and 
the resultant classifier.txt was placed in the context path of the web application to be used by the 
SentimentClassifier class. In other words, once the web application is deployed to the server, this class and the 
training  tweet corpus are no longer used as resources by the application. See below for an extract of code from 
this class:

        

SentimentClassifier.java
This class creates a classifier (initialises a LM classifier object field) which can then be applied to 
new items to ascertain their sentiment polarity. It utilises the classifier.txt file (which was trained with 
the SentimentTrainer class) and contains the method classify() which takes a a String (e.g. a tweet) as an 
input parameter an analyses its sentiment polarity using the LM classifier field. See below for the code 
contained in the classify() method:



TwitterManager.java
This is the core class of the web application. It deals with grabbing the tweets from Twitter using the 
Twitter4j API and then performing SA on them using a Sentiment Classifier object. Furthermore, the tweets 
themselves  get temporarily stored in the mySQL database for each instance of the class (i.e. each instance 
of a search). For each search, the overall  polarity of the tweets (i.e. the percentage of positive and 
negative tweets) gets counted in order to be returned to the user via  the web-interface. The class also contains the performQuery() method. This method takes in the user’s keyword (or multiple keywords)
as a parameter and uses it to  get the appropriate tweets from the Twitter API and analyse their sentiment. 
It also sends tweets to the appropriate database tables.  


        
SentenceSentiment.java
This class analyses one sentence (a String object) and returns its polarity (i.e. positive, negative, or neutral). Like the TwitterManager class, it uses a SentimentClassifier object to analyse each sentence that it receives. It contains a method called analyseSentence() which takes the sentence String provided by the user as a parameter and creates the SentimentClassifier object to analyse the sentence. See below for the code contained in this method: 
public static String analyseSentence(String Sentence) throws ClassNotFoundException, IOException {
		SentimentClassifier sentimentClassifier = new SentimentClassifier();
		
DocumentSentiment.java
This class analyses the distribution of sentiment across sentences/items in a .txt document. Like the 
TwitterManager class, it creates a SentimentClassifier object which analyses each sentence/item in a 
document sequentially. It contains a method called returnDocumentSentiment() which takes in a .txt 
 document as a parameter and counts how many positive, negative and neutral tweets are in that document. 
 It then uses a BufferedReader object to iterate through each separate item in the file. 

JDBC.java
This class implements the Java Database Connectivity features of Java. It is used to establish a connection 
between a mySQL database and to manipulate that database in certain ways. It is primarily implemented inside 
the TwitterManager class but also implemented in and called upon by certain JSP pages which make up the 
web-interface. 

JFreeChart.java
This class implements the JFreeChart API and contains 3 methods which create a pie chart, bar chart, and time 
series chart respectively.  It is used to display certain results to users in the web-interface and, accordingly, 
these methods are called by the JSP pages that need to display the results. 

RedirectSystemOut.java
This class was predominately used in the development stage of the application. It contains methods which can clear
folders from directories, clear contents from files, or append text (as Strings) to a file. It was used to test the
TwitterManager class before database functionality was integrated  into it. Its use is limited in the live version
of the web application. 

SentimentPercentageCalculator.java
This class contains a number of methods used when, given an instance of the TwitterManager class (i.e. the 
retrieval of 100 tweets from Twitter which match a keyword provided by the user), counting the instances of 
certain sentiment polarities (i.e. the percentage of positive and negative tweets returned). A small number of 
other utility functions are also included in this class. Methods from this class are mostly implemented inside 
the TwitterManager class. 

WEB RESOURCES
The web interface consisted of a number of resources (i.e. JSP, HTML, and CSS pages as well as a number of images).
JSP pages were used when elements of the Java classes needed to be incorporated into the web interface to 
integrate various functionality elements of the web application. Predominately, this was achieved by using 
Scriptlets in the JSP pages to invoke methods or access variables from the Java classes or retrieve Form values 
from other pages. For example, analyseTwitterTopic.jsp was the page which hosted the Twitter SA tool and 
Scriptlets were used to retrieve a Form from the homepage (index.html) and use this Form value as a parameter for
the  performQuery() method (from the TwitterManager class) which was then invoked by a Scriptlet on the page. 
The principles employed to allow the analyseTwitterTopic.jsp page to integrate elements of the java classes into 
the web application were also used in the other JSP pages which were used as resources in the web-interface. 
