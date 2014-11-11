/*this class implements the JFreeChart API and contains 3 methods which create a pie chart, bar chart, and time series chart respectively.
 * It is used to display certain result to user in the web-interface and, accordingly, these methods are called by the JSP pages that need
 * to display the results */
package SentimentApp;


import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JFreeChart {
	
	JFreeChart(){
		
	}
	

		
	
	// creates a pie chart given the values of % positive and % negative supplied by jsp page
public static org.jfree.chart.JFreeChart generatePieChartTwoValues (String name1, double value1, String name2, double value2) {
		
		DefaultPieDataset pieDataset = new DefaultPieDataset();
        // assign the values
		pieDataset.setValue(name1, value1);
        pieDataset.setValue(name2, value2);
        
        // create the pie chart
        org.jfree.chart.JFreeChart chart = ChartFactory.createPieChart3D("Percentage Sentiment Breakdown",pieDataset,true,true,false );
        PiePlot plot = (PiePlot) chart.getPlot();
        // set the colours
        plot.setSectionPaint(name1, Color.green);
        plot.setSectionPaint(name2, Color.red);
   

        return chart;
}
		
	
//creates a pie chart given the values which represent the number of positive, negative, and neutral items in a .txt document supplied by jsp page
public static org.jfree.chart.JFreeChart generateBarChart (int pos, int neg, int neu) {
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
   
	// assign the values
	dataset.addValue(pos, "series1", "Positive");
    dataset.addValue(neg, "series2", "Negative");
    dataset.addValue(neu, "series3", "Neutral");
   
    // create the bar chart
    org.jfree.chart.JFreeChart chart = ChartFactory.createBarChart("Distribution of Sentiment",
    "Sentiment", "Number of Items", dataset, PlotOrientation.VERTICAL, false, true, false);
    
    BarRenderer r = (BarRenderer) chart.getCategoryPlot().getRenderer();
    // set the colours
    r.setSeriesPaint(0, Color.green);
    r.setSeriesPaint(1, Color.red);
    r.setSeriesPaint(2, Color.gray);
  
    
    return chart;
    
}
/* this one was a little more tricky to implement than the other two methods in this calss. This method takes in a 
 * search topic provided by the user and then, incorporating methods from theJDBC class, retrieves items from 
 * the database to render a time series chart. */

@SuppressWarnings("deprecation")
public static  org.jfree.chart.JFreeChart generateTimeSeriesFromDatabase(Connection conn, String topic) throws SQLException{
	
	// get the relevant data from the "tweetstats" table with an SQL statement
	String statement = "select * from tweetstats where twittertopic='" + topic +"'";
	Statement stmt = null;
	ResultSet rs = null;
	stmt = conn.createStatement();
	rs = stmt.executeQuery(statement); // create the result set of the statement
	
	//create a time series object which will store variables used to create the time series chart
	TimeSeries sentimentHistory = new TimeSeries("% Positive Sentiment");
	/* this while loop gets the relevant date and corresponding variable for %positive from
	 * each row of the table */
	while (rs.next()) {
		int averageNumber=0;
		int bigTotal=0;
		int counter=0;
		String date = rs.getString("timeanddate");
		
		// this code is used for when there are multiple entries with the same date
		String statement2 = "select * from tweetstats where twittertopic='" + topic +"' AND timeanddate='" + date +"'";
		Statement stmt2 = null;
		ResultSet rs2 = null;
		stmt2=conn.createStatement();
		rs2 =stmt2.executeQuery(statement2);
		
		/* this loop creates a ResultSet object of row containing the same date and then calculates the average
		 * % positive variable for that date */
   while (rs2.next()) {
	   String number = rs2.getString("sentiment");
	  // the two lines of code below remove anything after the decimal point in the double values
	   String [] parts = number.split("\\.");
		String myNumber= parts[0]; 
	   int tempNumber = Integer.parseInt(myNumber); // change number from String type to integer
	   bigTotal += tempNumber;
	   counter++;
	   
   }
        // calculate the average %positive variable for entries on a given date
        averageNumber= (bigTotal/counter);
        // add a variable to the Time Series 
		sentimentHistory.addOrUpdate(new Day(new Date(date)), averageNumber);
		
	}
	
	TimeSeriesCollection dataset = new TimeSeriesCollection();
    dataset.addSeries(sentimentHistory);
    //create the time series chart
    org.jfree.chart.JFreeChart chart = ChartFactory.createTimeSeriesChart("Sentiment History", "Date", "% Positive Sentiment", dataset, true, true, false);
   
    // this code set the display of the chart to the desired format
    XYPlot plot = chart.getXYPlot();
    DateAxis axis = (DateAxis) plot.getDomainAxis();
    axis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));
    XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) plot.getRenderer();
    r.setSeriesShape(0, ShapeUtilities.createDiamond(5));
    r.setSeriesShapesVisible(0, true);
	
    return chart;
	
}


@SuppressWarnings("deprecation")

	



public static void main(String[] args) {
       
        }
}
