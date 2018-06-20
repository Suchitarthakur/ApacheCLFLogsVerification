/*
 * Author : Suchita Thakur
 * Project Name: ApacheLogsVerification Java project using Maven and TestNG
 * Description: This class LogVerification extends TestBase class and  define Method Definition of each Test Cases below and return the respective
 *  value when called from LogVerficationTest.java class
 * 
 * 1.	Small responses
 * 2.	Authorized access
 * 3.	PUT before POST
 * 4.	Suspicious activity
 * 
 * Reporter used to log message in Reporter output log in TestNG Report.
 */
package com.apachelog.qa.methoddef;

//import java.util.List;

import org.testng.Reporter;

import com.apachelog.qa.base.TestBase;


public class LogVerification extends TestBase{
	
	
	/* 1 Small responses */
	
	public int getResposeByte()
	{
		int Big_Res_Byte_Count =0;
		int Loop_var_Req_Line_No = 0;
		
		for(Loop_var_Req_Line_No = 0;Loop_var_Req_Line_No<bytesReceived.size();Loop_var_Req_Line_No++)
    	{
    		try {
				if (Integer.parseInt(bytesReceived.get(Loop_var_Req_Line_No)) >= 100000) {
					Big_Res_Byte_Count = Big_Res_Byte_Count + 1;
					
					Reporter.log("********** Response Byte greater than 100000 found :" + " Response byte size = "
							+ bytesReceived.get(Loop_var_Req_Line_No) + " at line" + " number :"
							+ (Loop_var_Req_Line_No + 1) + " **********\n");
				} 
			} catch (Exception e)
    		
    		{
				
				System.out.println("Number format Exeption  " +e+ " "
						+ "at line number " +(Loop_var_Req_Line_No+1) + " for Response byte" + bytesReceived.get(Loop_var_Req_Line_No));
				
				Reporter.log("||********** A \"-\" in a field indicates missing data. "
     					+ "Request containing MISSING RESPONSE BYTE at line number "
     					+ (Loop_var_Req_Line_No + 1)+" in given log can not be considerd for this Test Case **********||");
				// TODO: handle exception
			}
    	 	
       	}
		
	return Big_Res_Byte_Count;
	}
	
	/*Authorized access*/
	
	public int checkAuthorizedAccess()
	{
		
		int global_http_status_code_401_count=0,Request_line_no=0,global_matching_request_count=0;
		
		
		for(Request_line_no=0;Request_line_no<host.size();Request_line_no++)
     	{
     		if(host.get(Request_line_no).matches(".*\\.example\\.org"))
     		{
     			global_matching_request_count = global_matching_request_count+1;
     			
     			try {
					if(Integer.parseInt(httpStatusCode.get(Request_line_no)) == 401)
					{
					global_http_status_code_401_count = global_http_status_code_401_count+1;
					
					Reporter.log("********** Received 401 from : " +
							host.get(Request_line_no).toString() + " at line number :" + (Request_line_no+1) +" in "
							+ "log\nFailed to feed Rover due to Unautorized access **********\n");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					
					System.out.println("index out of bound in Request_line_num :: " +(Request_line_no+1));
	     			
	     			System.out.println("execption " +e+ "for request " + host.get(Request_line_no));
	     			
	     			Reporter.log("||********** A \"-\" in a field indicates missing data. "
	     					+ "Request containing MISSING HTTP STATUS CODE at line number "
	     					+ (Request_line_no+1)+" in given log cant be considered for Test case **********||");
				}
     			
     		}
     	}
		
		if(global_matching_request_count == 0 && Request_line_no== host.size())
		{
			Reporter.log("********** No \"*.example.org\" found in given log **********" );
			return global_http_status_code_401_count;
					
		}
		else if (global_matching_request_count > 0 && global_http_status_code_401_count ==0)
		{
			Reporter.log("********** All access are authorized form and No "
					+ "401 code was found in the given log from host \"*.example.org\" **********");
			return global_http_status_code_401_count;
		}
		else
		{
		return global_http_status_code_401_count;
		}
		
		
		
	}
	
	/* PUT before POST */
	public boolean putBeforePost()
	{
		int upstream_loop =0,global_post_found_in_script = 0, Request_line_num,Loop_var_downstream,Loop_var_upstream;
		int downstream_loop = 0;
		int global_put_after_post_found = 0;
		int global_no_put_found_after_post = 0;
		
		
     	for(Request_line_num=0;Request_line_num<requestLine.size();Request_line_num++) /* to find post Request in given log*/
     	{
     	
     		
     		
     		try{
     		if(requestLine.get(Request_line_num).substring(0,requestLine.get(Request_line_num).indexOf(' ')).equals("POST"))
     		{
     			
     			
     			global_post_found_in_script = global_post_found_in_script+1;
     			int startIndex = requestLine.get(Request_line_num).indexOf(' ');
         		int endIndex = requestLine.get(Request_line_num).indexOf(" H");
     			String String_Request_url = requestLine.get(Request_line_num).substring(startIndex+1, endIndex);
         		
         		if(Request_line_num > 0){
         			
         			for(Loop_var_upstream = Request_line_num-1;Loop_var_upstream>=0;Loop_var_upstream--)//to check if put is found before post
         				{
         					upstream_loop=0;
         					if(requestLine.get(Loop_var_upstream).contains("PUT") && requestLine.get(Loop_var_upstream).contains(String_Request_url))
         					{
         						
         						
         						Reporter.log("||***************Test Case \"Put before Post \" is GOOD for given Request line As Put is found before POST in request : "+ requestLine.get(Loop_var_upstream).toString()
         								+" at line number : " + (Loop_var_upstream+1) + " in given log\n Respective Post request is : " + requestLine.get(Request_line_num).toString()
         								+" at line number : " + (Request_line_num+1) + " in given log***************||\n");
         							
         							upstream_loop = 1;
         							break;
         					}
         			
         				}//end of inner for Loop_var_upstream
         			
         			if(upstream_loop == 0)//to check if PUT is after POST in log.upstream_loop=0 means put is not found before post
         			{
         				for(Loop_var_downstream = Request_line_num+1 ; Loop_var_downstream<requestLine.size();Loop_var_downstream++)
         				{
         					downstream_loop = 0;
         					if(requestLine.get(Loop_var_downstream).contains("PUT") && requestLine.get(Loop_var_downstream).contains(String_Request_url))
         					{
         						Reporter.log("************Test Case \"Put before Post \" is NOT GOOD As PUT is found after POST in request : "+ requestLine.get(Loop_var_downstream).toString()
         								+" at line number : " + (Loop_var_downstream+1) + " in given log\n Respective POST request is : " + requestLine.get(Request_line_num).toString()
         								+" at line number :" + (Request_line_num+1) + " in given log******************\n");
         						
         						downstream_loop = 1;
         						global_put_after_post_found = global_put_after_post_found+1;
         						
         						break;
         					}	//end of inner for Loop_var_downstream
         						
         				}
         				if((downstream_loop == 0) && (upstream_loop == 0))
         				{
         					Reporter.log("*********** No PUT method found before OR after POST method in log for request " +
         							requestLine.get(Request_line_num).toString()+ " at line : " + (Request_line_num+1));
         					
         					global_no_put_found_after_post = global_no_put_found_after_post + 1;
         				}
         				
         				/*after for loop if no put is found change upstream_loop value*/
         				/*upstream_loop = 2;*/
         			}
         			
         		}	
         		else
         		{
         			Reporter.log("********** Post is found at line number 0, No put before Post for requset : " 
         		+requestLine.get(Request_line_num) +" **********" );
         		}
         		
         			
     		}//end of main(first) if loop
     		}
     		catch(Exception e)
     		{
     			System.out.println("index out of bound in Request_line_num :: " +(Request_line_num+1));
     			
     			System.out.println("execption " +e+ "for request " + requestLine.get(Request_line_num));
     			
     			Reporter.log("||********** A \"-\" in a field indicates missing data. "
     					+ "Request containing MISSING REQUEST LINE at line number "
     					+ (Request_line_num+1)+" in given log cant be considered for Test case **********||");
     		}
     					
     	}//end of main (first )for loop
     	
  
    	    	 
     if(global_post_found_in_script==0 && Request_line_num==requestLine.size())
  			Reporter.log("No POST method found in given log");
  	
     if((global_put_after_post_found != 0) || (global_no_put_found_after_post != 0))
     {
			return false;
     }
     else
     {
    	 return true;
     }
	}
	
	
	/*Suspicious activity*/
	
	public int checkSuspiciousActivity()
	{
		int global_httpStatusCode_401_count =0,Request_line_no=0;
		
	
	for(Request_line_no =0;Request_line_no<host.size();Request_line_no++)
 	{
 		try {
			if(Integer.parseInt(httpStatusCode.get(Request_line_no)) == 401)
			{
				global_httpStatusCode_401_count ++;
				Reporter.log("********** httpStatusCode : " +httpStatusCode.get(Request_line_no)+" found in given log at line"
						+ " number : " + (Request_line_no+1) +" from client host :" 
						+ host.get(Request_line_no)+" **********");
			 			
			}
		} catch (Exception e)
 		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
			System.out.println("Number for Exeption at " +e+ " "
					+ "at line number " +(Request_line_no+1) + " for " + httpStatusCode.get(Request_line_no));
			
			Reporter.log("||********** A \"-\" in a field indicates missing data. "
 					+ "Requset containing MISSING  HTTP STATUS CODE  at line number "
 					+ (Request_line_no + 1)+" in given log can not be considerd for this Test Case **********||");
		}
 	}
	
	
	
	if(global_httpStatusCode_401_count==0)
	{
		Reporter.log("||********** No 401 status found in given log for any host. All access are Authorized **********");
	}
	
		return global_httpStatusCode_401_count;	
		
 	}
	
	
	
}
