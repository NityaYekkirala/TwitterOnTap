<>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="SentimentApp.SentimentClassifier"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.apache.commons.fileupload.FileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="SentimentApp.RedirectSystemOut" %>
<%@ page import="SentimentApp.DocumentSentiment" %>
<%@ page import="SentimentApp.JFreeChart" %>
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
	height: 950px;
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
         
          <li> <a href="citations.html" title="Citations page"> CITATIONS </a>
          </li>
          
          </ul>
          
          
  </nav> </div>
  
  <p>
  
Thank you for using TwitterOnTap!. Please see below to check the  to check the distribution of sentiment accross the content of the .txt file you submitted. You can click <a href="index.html"   title="Home page" > here </a> to return to the homepage.
      
  </p>
  
  <% SentimentClassifier.location = getServletContext().getRealPath("/classifier.txt"); %>
  
  <%  String file = request.getParameter ("documentSentiment"); 
      DocumentSentiment example = new DocumentSentiment();  
		
  %> 
  
  <%

 boolean isMultipart = ServletFileUpload.isMultipartContent(request);
 String content="";

 if(isMultipart) 
     {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = upload.parseRequest(request);

        Iterator iter = items.iterator();
        while (iter.hasNext()) 
         {
            FileItem item = (FileItem) iter.next();
            if(!item.isFormField()) 
              {
                 BufferedInputStream buff=new BufferedInputStream(item.getInputStream());
                 byte []bytes=new byte[buff.available()];
                 buff.read(bytes,0,bytes.length);
                 content=new String(bytes);
              }
          }
     }
 final File file2 = new File(getServletContext().getRealPath(".") + "/mytext.txt");
 SentimentApp.RedirectSystemOut.writeTextToFile(content, file2);
 
out.println(example.returnDocumentSentiment(file2)); 

%>

<br> </br>

<%

int pos= example.toReturnPos;
int neg= example.toReturnNeg;
int neu= example.toReturnNeu;

org.jfree.chart.JFreeChart chart = JFreeChart.generateBarChart(pos, neg, neu);

try {
	 final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
	 
	 final File file3 = new File(request.getServletContext().getRealPath(".") + "/barchart.png");
			  ChartUtilities.saveChartAsPNG(
			   file3, chart, 600, 400, info);
			  } catch (Exception e) {
			  out.println(e);
			  }
%>
  
  
  <img src="barchart.png" width="500px" height="300px" />
  
  
  
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