/*
 * Author : Suchita Thakur
 * Project Name: ApacheLogsVerification Java project using Maven and TestNG
 * Description:	This class LogVerificationTest.java extends TestBase and Call Methods of Log Verifaction for each of below Test Cases.
 * Uses TestNG annotation to define Priority and Description ,
 * Assertion used to mark Test Cases PASS/FAIL
 * Reporter used to log message in Reporter output log in TestNG Report.
 *  */

package com.apachelog.qa.test;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
//import org.testng.Reporter;
import org.testng.annotations.Test;
//import org.apache.log4j.Logger;

import com.apachelog.qa.base.TestBase;
import com.apachelog.qa.methoddef.LogVerification;

public class LogVerificationTest extends TestBase {

	public  LogVerification logverification;
	public LogVerificationTest(){
		super();
		initilization();
		
	}
	
	@BeforeMethod
	public void setUp()
	{
		logverification = new LogVerification();
		System.out.println("||********** Start Test **********||");
	}
	
	@Test(priority=1,description="This Test Case is used to find Response Byte received from server."
			+ " Each Response byte in given log should be less than 100000 byte. "
			+ "This Test case will FAIL if any one or More Response byte will be greater than 100000 bytes."
			+ "For Detailed logs please refer Reporter Output Log")
	public void smallResposeTest()
	{
		
		int big_res_count = logverification.getResposeByte();
		boolean flag = true;
		
		if(big_res_count > 0)
		{
			flag = false;
			Assert.assertEquals(flag, true, "Total " + big_res_count +" Number of response size is greater than 100000 byte in given log. Check Reporter Output for more info");
		}
		else
		{
			Reporter.log("********** All Responses are below 100000 **********");
		
		}
		
		
	}
	
	
	@Test(priority=2,description = "This Test is used to check any Unauthorized access (401) from site "
			+ "***.example.org. Test case will"
			+ " fail only if 401 found from site ***.example.org in given log. In all other"
			+ " condition Test Case will PASS. For Detailed logs please refer Reporter Output Log")
	
	public void authorizedAccessTest()
	{
		boolean flag = true;
		int http_status_code_401_count = logverification.checkAuthorizedAccess();
		if(http_status_code_401_count > 0)
		{
			
		flag= false;
		Assert.assertEquals(flag, true, "Total " +http_status_code_401_count +" 401 found from site"
				+ " ***.example.org . Failed to feed Rover. For detailed logs and information Please refer "
				+ "Reporter output log in TestNG report");
		}
		
	}
	
	
	@Test(priority=3, description = "This Test case is designed to check \"All POST requests to a path need to come after "
			+ "the PUT request for that path\".The Test case will FAIL if "
			+ "1) PUT request comes after POST for any given PATH for one or more times in given log. "
			+ "2) NO PUT Requset in log for POST request to a given path. "
			+ "For all all other conditions Test Case will PASS and print logs in Reporter Logs."
			+ "For more Detailed log, Please refer Reporter Output log in TestNG Report" )
	public void putBeforePostTest()
	{
		boolean flag = logverification.putBeforePost();
		Assert.assertEquals(flag, true,"Failure Reason may be either of two.1) PUT to a given path"
				+ " observed after POST to a given path for one or more times in given log. "
				+ "2) NO PUT Requset in log for POST request to a given path. "
				+ "For more Details Please refer Reporter Output log in TestNG Report");
	}
	
	
	
	@Test(priority=4,description = "This Test case is designed to check \"The log must have less than five 401 responses from any host.\""
			+ " The Test case will FAIL if count of 401 Response CODE exceeds FIVE from any HOST in given log."
			+ " For Detailed log ,Please refer Reporter Output log in TestNG Report" )
	public void suspiciousActivityTest()
	{
		boolean flag = true;
		int httpStatusCode_count = logverification.checkSuspiciousActivity();
		if(httpStatusCode_count>4)
		{
			flag = false;
			Assert.assertEquals(true, flag,"Count for 401 staus code in given log is greater than 4.\n Actual is :" +httpStatusCode_count 
					+" Expected is less than : 5");
		}
	}
	
	@AfterMethod
	public void tearDown()
	{
		System.out.println("||********** Finish Test **********||" );
	}
	
}
