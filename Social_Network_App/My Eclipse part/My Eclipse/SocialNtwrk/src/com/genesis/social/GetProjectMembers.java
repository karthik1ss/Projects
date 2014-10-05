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

public class GetProjectMembers extends HttpServlet {
		Connection con;
	    Statement st;
	    Statement st1;
	    ResultSet res;
	    ResultSet res1;

	/**
	 * Constructor of the object.
	 */
	public GetProjectMembers() {
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
            st1.close();
            res.close();
            res1.close();
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
		
		System.out.println("in side Retrieve Project Members");
        int projectId = Integer.parseInt(request.getParameter("projectid"));
        System.out.println("projectid ="+projectId);
        String code = "6205";
        String data = "";
        
        String proj_member_name = null;
        String contentSource = null;

        try
        {
        	data = "<users>";
            st = con.createStatement();
            res = st.executeQuery("SELECT * FROM  projectdata where projectid="+projectId);
           
                code = "";
                while(res.next())
                {
                    proj_member_name = res.getString("proj_member_name");
                    st1 = con.createStatement();
                    res1 = st1.executeQuery("SELECT * FROM  avatar where screenName='"+proj_member_name+"'");
                    if(res1.next())
                    {
                    	contentSource = res1.getString("contentSource");
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
