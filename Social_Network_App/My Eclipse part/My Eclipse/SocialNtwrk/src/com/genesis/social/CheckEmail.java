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

public class CheckEmail extends HttpServlet {
	Connection con;
    Statement st;
    ResultSet res;
	/**
	 * Constructor of the object.
	 */
	public CheckEmail() {
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
		String uname = request.getParameter("email");
		System.out.println("Inside Check Email");
		String code = "6205";
		String email = "";
		try
		{
		    st = con.createStatement();
		    res = st.executeQuery("SELECT * FROM  avatar where emailAddress='"+uname+"'");
		    if(res.next())
		    {
		        code = "";
		        email = res.getString("email");
		           
		    }
		    if(code=="6205")
		    {
		    	out.write("Member does not exist");
		        System.out.println("Member does not exist");
		    }
		    else
		    {
		    	out.write(email);
		    	System.out.println("Member exist");
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
