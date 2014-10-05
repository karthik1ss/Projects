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

public class GetProjectFileDetails extends HttpServlet {

	Connection con;
    Statement st;
    Statement st1;
    Statement st2;
    ResultSet res;
    ResultSet res1;
    ResultSet res2;
	/**
	 * Constructor of the object.
	 */
	public GetProjectFileDetails() {
		super();
	}
	
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
		try
        {
            con.close();
            st.close();
            st1.close();
            st2.close();
            res.close();
            res1.close();
            res2.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Cannot close connection to database server");
        }
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String result = "";
        String data = request.getParameter("data");
		System.out.println("inside Retrieve Project File details");
        int projectId = Integer.parseInt(request.getParameter("projectid"));
        System.out.println("projectid ="+projectId);
        String code = "6205";
        
        String proj_member_name = null;
        String contentSource = null;
        String contentDescription = null;
        String contentName = null;
        try
        {
        	data = "<assetid>";
            st = con.createStatement();
            res = st.executeQuery("SELECT * FROM  projectassets where projectid="+projectId);
           
                code = "";
                while(res.next())
                {
                    proj_member_name = res.getString("proj_member_name");
                    st = con.createStatement();
                    res = st.executeQuery("SELECT * FROM  avatar where screenName='"+proj_member_name+"'");
                    if(res.next())
                    {
                    	contentSource = res.getString("contentSource");
                    	data+="<user><screenName>"+proj_member_name+"</screenName><contentSource>"+contentSource+"</contentSource></user>";
                    }
                }
            data = (new StringBuilder(String.valueOf(data))+"<code>"+code+"</code></users>").toString();
            out.write(data);
            System.out.println("data = "+data);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Cannot retrive Data from database server");
        }
		
		out.flush();
		out.close();
	}

	
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
