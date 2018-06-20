/*
 * Author : Suchita Thakur
 * Project Name: ApacheLogsVerification Java project using Maven and TestNG
 * Description: This class contain code to Parse the Apache Common Log Format, store them in separate collection array list object for each 
 * hostname , requsetTime, ClientRequest,http_StatusCode, Byte_Received in COnnection and return arraylist.
 */
package com.apachelog.qa.testUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.apachelog.qa.base.TestBase;

public class TestUtils extends TestBase {
	
	/*Log file feilds*/
	public static List <String> hostName = new ArrayList<String>();
	public static List <String> reqTime = new ArrayList<String>();
	public static List <String> clientReqLine = new ArrayList<String>();
	public static List<String> http_StatusCode = new ArrayList<String>();
	public static List<String> byte_Received = new ArrayList<String>();
	
	@SuppressWarnings("resource")
	public static void fileParse(String fp) throws  IOException
	{
		String logEntry = null;
		
		BufferedReader br = new BufferedReader(new FileReader(fp));
		
	 while ((logEntry = br.readLine()) != null)
	 {
     	
     	String logEntryLine = logEntry;
     	Pattern p = Pattern.compile(logPattern);
        Matcher matcher = p.matcher(logEntryLine);
          
          
      //System.out.println(matcher.toString());
          
          while (matcher.find())
          {
          if (!matcher.matches())
             {
        	      System.err.println("Bad log entry (or problem with RE?):");
        	      System.err.println(logEntryLine);
        	      return ;
        	    }
         // System.out.println(logEntryLine); 
          	//System.out.println("IP Address: " + matcher.group(1));
          	hostName.add(matcher.group(1));
            
           // System.out.println("Date&Time: " + matcher.group(4));
          	reqTime.add(matcher.group(4));
            
           // System.out.println("Request: " + matcher.group(5));
          	clientReqLine.add(matcher.group(5));
            
           // System.out.println("Response: " + matcher.group(6));
          	http_StatusCode.add(matcher.group(6));
            
           // System.out.println("Bytes Sent: " + matcher.group(7));
          	byte_Received.add(matcher.group(7));
            
           // System.out.println(numOfBytes.toString());
           // return ;
          
      
          }//end of while loop
         
	 }
	
	 
	}
	
	
	
	public static List<String> getnumOfBytes()
	{
		return byte_Received;
	}
	
	
	public static List<String> gethttpStatusCode()
	{
		return http_StatusCode;
	}
	
	public static List<String> getclientRequest()
	{
		return clientReqLine;
	}
	
	public static List<String> getrequestTime()
	{
		return reqTime;
	}
	
	public static List<String> getclientHost()
	{
		return hostName;
	}
	
	
}
