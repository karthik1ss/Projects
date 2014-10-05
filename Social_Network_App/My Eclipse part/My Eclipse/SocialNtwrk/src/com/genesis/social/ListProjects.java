package com.genesis.newzonia;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListProjects extends HttpServlet {
	Connection con;
    Statement st;
    ResultSet res;
	/**
	 * Constructor of the object.
	 */
	public ListProjects() {
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
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		System.out.println("in side List Projects");
        String code = "6205";
        String data = "";
        
        try
        {
        	st=con.createStatement();
        	res=st.executeQuery("Select * from projects");
        	data+="<projects>";
        	if(res.next())
        	{
        		code="";
        		do
        		{
        			int projectid=res.getInt("projectId");
        			String projectname=res.getString("projname");
        			String projectlogo=res.getString("imagesource");
        			String visibility=res.getString("visibility");
        			String creationtime=res.getString("creationtime");
        			data+="<project><projectid>"+projectid+"</projectid><projectname>"+projectname+"</projectname><projectlogo>"+projectlogo+"</projectlogo><visibility>"+visibility+"</visibility><creationtime>"+creationtime+"</creationtime></project>";
        			
        		}while(res.next());
        	
        	}
        	data+="<code>"+code+"</code></projects>";
        	System.out.println("data ="+data);
        	out.write(data);
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
