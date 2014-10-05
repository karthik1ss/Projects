
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paypal.sdk.services.NVPCallerServices;

import com.paypal.sdk.core.nvp.NVPEncoder;

import com.paypal.sdk.core.nvp.NVPDecoder;
import com.paypal.sdk.profiles.APIProfile;
import com.paypal.sdk.profiles.ProfileFactory;

import java.util.*;
import java.text.*;



public class VerifyTransaction extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public VerifyTransaction() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}


	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		System.out.println("Inside Verification");
		String Transactionid = request.getParameter("TransactionId");
		
		try {
			//String str="An5ns1Kso7MWUdW4ErQKJJJ4qi4-AMonGoV828O3vjYhVq9FhG0J0mJp";
			APIProfile profile = null;
			profile = ProfileFactory.createSSLAPIProfile();
			System.out.println("Setting Username and password");
			profile.setAPIUsername("Seller_1270122131_biz_api1.gmail.com");
			profile.setAPIPassword("57YK3ZXWSN6BVWH9");
			//System.out.println("Before calling the call method");
			//profile.setSignature((String)str);
			System.out.println("Before providing the Certificate");
			profile.setCertificateFile(getServletContext().getRealPath("WEB-INF/cert/paypal_cert.p12"));
			profile.setPrivateKeyPassword("password");
			profile.setEnvironment("sandbox");
			profile.setSubject("");
			System.out.println("Before creating the caller object");
			NVPCallerServices caller = new NVPCallerServices();
			System.out.println("after creating the caller object");
			caller.setAPIProfile(profile);
			System.out.println("after setting the API Profile");
			NVPEncoder encoder = new NVPEncoder();

			encoder.add("VERSION", "61.0");
			encoder.add("METHOD","TransactionSearch");

			// Add request-specific fields to the request string.
			encoder.add("TRXTYPE","Q"); 
			
			DateFormat dfRead = new SimpleDateFormat("MM/dd/yyyy");
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Calendar startDate = Calendar.getInstance();
			startDate.add(Calendar.DATE, -1);

			Calendar endDate = Calendar.getInstance();

			Calendar startDateObj = Calendar.getInstance();
			startDateObj.setTime(dfRead.parse((String)df.format(startDate.getTime())));

			Calendar endDateObj = Calendar.getInstance();
			endDateObj.setTime(dfRead.parse((String)df.format(endDate.getTime())));
			
			System.out.println("before setting the Dates");
			encoder.add("STARTDATE",startDateObj.get(Calendar.YEAR)+"-"+(startDateObj.get(Calendar.MONTH)+1)+"-"+(startDateObj.get(Calendar.DAY_OF_MONTH))+"T00:00:00Z");
			System.out.println(startDateObj.toString());
			encoder.add("ENDDATE",startDateObj.get(Calendar.YEAR)+"-"+(startDateObj.get(Calendar.MONTH)+1)+"-"+startDateObj.get(Calendar.DAY_OF_MONTH)+"T24:00:00Z");
						System.out.println("before setting the Dates");
//			encoder.add("TRANSACTIONID",(String)request.getParameter("TransactionId"));
			encoder.add("TRANSACTIONID",Transactionid);
			//encode method will encode the name and value and form NVP string for the request		
			String strNVPString = encoder.encode();
			
			
			//encode method will encode the name and value and form NVP string for the request
			String strNVPRequest = encoder.encode(); 
			System.out.println("Before calling the call method");
			//call method will send the request to the server and return the response NVPString
			String strNVPResponse = caller.call( strNVPRequest);

			//NVPDecoder object is created
			NVPDecoder decoder = new NVPDecoder();

			//decode method of NVPDecoder will parse the request and decode the name and value pair
			decoder.decode(strNVPResponse);

			//checks for Acknowledgement and redirects accordingly to display error messages			
			String strAck = decoder.get("ACK");
			
			System.out.println("Transaction Status : "+strAck);
		
				out.write(decoder.get("ACK"));
				//checks for Acknowledgement and redirects accordingly to display error messages			
				
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
