<>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>

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
	height: 1200px;
	padding:10px;
	
	
	
}



</style>
</head>

<body>

<div id="shareHolder">
Share <br /> <br />
    
   <a href="https://www.facebook.com" rel="external" title="Facebook page"> <img src="facebook.png" alt="Facebook logo" /> </a> <br /> <br />
   
   <a href="https://www.twitter.com" rel="external" title="Twitter page"> <img src="twitter.png" alt="Twitter logo" /> </a> <br /> <br>
  
    
   <a href="http://www.pinterest.com" rel="external" title="Pinterest page"> <img src="pinterest.png" alt="Pinterest logo" /> </a> <br />


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
         
          <li> <a href="citations.html" title="Citations page" > CITATIONS </a>
          </li>
          
        </ul>
          
          
  </nav> </div>



  <h1> TESTING THE SENTIMENT ANALYSIS APPLICATION </h1>
  
  
  <p>In order to test the accuracy of the Sentiment Analysis tool, it needed to be implemented on a test set of tweets. This test set consisted of a number of tweets hosted on the UCD server (click <a href="http://csserver.ucd.ie/~tosull/text.html" title="UCD tweet collection page"> here </a> to view the tweets). These tweets were saved to a .txt file and then analysed with the Sentiment Analysis application. The results of the analysis can be seen below.</p>
  <br /> <br />
  
  <img src="UCDtweets.png" align="middle"  alt="UCD Tweet Testing Results" width="450" height="350" />
  ]
  <br /> <br /> <br />
  
  <p>You can  upload your own .txt file containing tweets or sentences and click "GO!" to check the distribution of sentiment accross its content. For best results,  the .txt file should contain tweets/sentences that are are seperated by a line break. Furthermore, the more like tweets the sentences are, the more accurate the analysis will be.<br /> 
</p>

<br />
  
   <form action="analyseDocument.jsp"  method="post"  name="uploadFile" enctype="multipart/form-data" >
 
 <input   type="file"  name="documentSentiment" required>
 
   <input class="button" type="submit" name="Submit5" value="GO!">
   <br>
 </form>
  
  
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
