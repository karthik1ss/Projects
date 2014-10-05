package com.genesis.newzonia;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateProductAvailability extends HttpServlet {
	Connection con;
    Statement st;
    ResultSet res;
	/**
	 * Constructor of the object.
	 */
	public UpdateProductAvailability() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
		try
        {
            con.close();
            st.close();
            res.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Cannot close connection to database server");
        }
	}
	public void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String assetid = request.getParameter("productid");
		System.out.println("Product Id = "+assetid);
		System.out.println("Inside UpdateProduct Availability");
		String code = "6205";
		String email = "";
		try 
		{
		    st = con.createStatement();
		    int i = st.executeUpdate("UPDATE products SET status='Un Available' WHERE assetid = '"+assetid+"'");
		    if(i==1)
		    {
		    	System.out.println("Updated Successfully..");
		    }
		    else
		    {
		    	System.out.println("Unable to Update");
		    	System.out.println("I value = "+i);
		    }
		}
		catch(Exception e)
		{
		    e.printStackTrace();
		    System.err.println("Cannot retrive Data from database server");
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
		try
        {
		 ServletContext sc=getServletContext();
			con=(Connection)sc.getAttribute("connection");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Cannot connect to database server");
        }
	}

}
