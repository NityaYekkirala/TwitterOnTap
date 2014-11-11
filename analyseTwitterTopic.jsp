<>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="SentimentApp.SentimentClassifier"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import= "SentimentApp.TwitterManager" %>
<%@ page import= "SentimentApp.CSVreader" %> 
<%@ page import="SentimentApp.JFreeChart" %>
<%@ page import="SentimentApp.JDBC" %>
<%@ page import="java.io.*" %>
<%@ page import="org.jfree.chart.ChartUtilities" %>
<%@ page import="org.jfree.chart.*" %>
<%@ page import="org.jfree.data.general.*" %>
<%@ page import="org.jfree.chart.ChartRenderingInfo" %>
<%@ page import="org.jfree.chart.entity.StandardEntityCollection" %>
<%@ page trimDirectiveWhitespaces="true" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Twitter Sentiment Analysis</title>
<link rel="stylesheet" href="main.css" />

<style>  

#wrapper {
	background-color:#FFF;
	border-color:7534AA;
	border-style:groove;
	width:850px;
	margin:0 auto;
	text-align:left;
	height: 2000px;
	padding:10px;
	
	
	
}

</style>
</head>

<body>

<div id="shareHolder">
Share <br /> <br />
    
   <a href="https://www.facebook.com/twitterontap?ref=hl" rel="external" title="Facebook page"> <img src="facebook.png" alt="Facebook logo" /> </a> <br /> <br />
   
   <a href="https://twitter.com/jrcody38" rel="external" title="Twitter page"> <img src="twitter.png" alt="Twitter logo" /> </a> <br /> <br>
  
    
   <a href="http://www.pinterest.com/jonathancody38/twitterontap/" rel="external" title=""> <img src="pinterest.png" alt="Pinterest logo" /> </a> <br />


</div>

<div id="wrapper" >

<div id="header1"></div>


<div id="navbar">

<nav>
       <ul>
          <li> <a href= "index.html" title="Home page"> HOME </a>
         </li> 
         
          <li> <a href="about.html" title="About page" >ABOUT </a>
         </li>
          
          <li> <a href= "testing.jsp" title="Testing page" > TESTING </a> 
          </li>
         
          <li> <a href="citations.html" title="Citations page"> CITATIONS </a>
          </li>
          
           </ul>
          
          
  </nav> </div>
  
  <p>
  
Thank you for using TwitterOnTap! Please see below for a break down of the current Twitter sentiment towards your choosen topic. You can click <a href="index.html"   title="Home page" > here </a> to return to the homepage.
      
  </p>
  
<% SentimentClassifier.location = getServletContext().getRealPath("/classifier.txt"); %>  

<%  String query = request.getParameter ("twitterSearchTopic"); %>

Topic: <%= request.getParameter ("twitterSearchTopic")  %>

<% TwitterManager example = new TwitterManager(); %>
<br> <br>
  
  <% out.println(example.performQuery(query)); %> <br> </br>
  
  <% double pos = example.toReturnPos;
     double neg = example.toReturnNeg; 
     
     org.jfree.chart.JFreeChart chart = JFreeChart.generatePieChartTwoValues("Positive", pos, "Negative", neg);

     try {
     	 final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
     	 
     	 final File file1 = new File(getServletContext().getRealPath(".") + "/piechart.png");
     			  ChartUtilities.saveChartAsPNG(
     			   file1, chart, 600, 400, info);
     			  } catch (Exception e) {
     			  out.println(e);
     			  }
  %> 
  
  <img src="piechart.png" width="500px" height="300px" /> <br></br>
  
  <p> Here are some examples of the current tweets relating to this topic</p>
  
  <div id="posHolder"> <h2> POSITIVE TWEETS </h2>   
  
  
  
  <% Connection connection = JDBC.databaseConnect(); 
     String posSQL = "SELECT * FROM positivetweets";
     String negSQL = "SELECT * FROM negativetweets";
     
  %>
  <%  for (int a =1; a<8; a++) { %> 
                                 <br> <img src="<%= SentimentApp.JDBC.getFromDatabase(posSQL, "userpicURL", a, connection) %>" alt="User Profile Image" height="50px" width="50px" />
                                 <br> <% out.println("USERNAME: " + SentimentApp.JDBC.getFromDatabase(posSQL, "username", a, connection)); %>
                                 <br> <% out.println(SentimentApp.JDBC.getFromDatabase(posSQL, "tweetcontent", a, connection)); %>
                                 <br> <% out.println("LOCATION: " + SentimentApp.JDBC.getFromDatabase(posSQL, "location", a, connection)); %>
                                 <br></br>
                                 <% } %>
 </div>

<div id="negHolder"> <h2> NEGATIVE TWEETS </h2> 
<%  for (int b =1; b<8; b++) { %>
                                 <br> <img src="<%= SentimentApp.JDBC.getFromDatabase(negSQL, "userpicURL", b, connection) %>" alt="User Profile Image" height="50px" width="50px" />
                                 <br> <% out.println("USERNAME: " + SentimentApp.JDBC.getFromDatabase(negSQL, "username", b, connection)); %>
                                 <br> <% out.println(SentimentApp.JDBC.getFromDatabase(negSQL, "tweetcontent", b, connection)); %>
                                 <br> <% out.println("LOCATION: " + SentimentApp.JDBC.getFromDatabase(negSQL, "location", b, connection)); %>
                                 <br></br>
                                 <% } %>
                                 <% connection.close(); %>

 </div>

</div>
  
  <br> <br>
  
  
  <div id="footer">

 Created by Jonathan Cody as part of a UCD Software Engineering project (2014) <br /> <br /> <br />

<a href="http://www.ucd.ie/research/" title="UCD Research page" > <img src="UCD.png" hspace="20" align="left" alt="UCD logo"  width="150" height="50"/> </a>

<a href="https://twitter.com" title="Twitter homepage" > <img src="twitter2.png" hspace="20" align="right" alt="Twitter logo" width="150" height="50"/></a>

 <a href="index.html" title="Home page">  Home </a>  | <a href="about.html" title="About page" > About </a> | <a href="testing.jsp" title="Testing page" >Testing </a> | <a href="citations.html" title="Citations page" > Citations </a> <br /> 


</div>

</body>
</html>