/*
 * Author : Suchita Thakur
 * Project Name: ApacheLogsVerification Java project using Maven and TestNG
 * Description : This class TestBase is the Parent Class of all Classes where all variable of are
 *  Initialized and File is parsed which is required before beginning of Test case Execution.
 *  value when called from LogVerficationTest.java class
 *  */
package com.apachelog.qa.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.apachelog.qa.testUtils.TestUtils;



public class TestBase {
		
	
	boolean DEBUG = false;

	//Log file feilds
	public static List <String> host = new ArrayList<String>();
	public static List <String> serverRequestTime = new ArrayList<String>();
	public static List <String> requestLine = new ArrayList<String>();
	public static List<String> httpStatusCode = new ArrayList<String>();
	public static List <String> bytesReceived = new ArrayList<String>();

	
	public static String logPattern = "^([\\d\\D.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" ([\\d\\D]+) ([\\d\\D]+)";
	
	
	public static String filepath = "";
	
	
	public TestBase()
	{
		
	}
	public void initilization()
	{
		System.out.println("Enter Apache CLF log file Path to be parsed against which you need to run Test Case");
		Scanner sc = new Scanner(System.in);
		filepath = sc.nextLine();
		filepath.replace("\\", "\\\\");
		sc.close();
		try {
			TestUtils.fileParse(filepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		bytesReceived = TestUtils.getnumOfBytes();
		host = TestUtils.getclientHost();
		httpStatusCode = TestUtils.gethttpStatusCode();
		requestLine = TestUtils.getclientRequest();
		
	}
	
		
}
