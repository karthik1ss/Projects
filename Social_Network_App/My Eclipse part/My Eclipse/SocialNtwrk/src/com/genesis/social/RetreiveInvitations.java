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

public class RetreiveInvitations extends HttpServlet {
	Connection con;
    Statement st;
    ResultSet res;
	/**
	 * Constructor of the object.
	 */
	public RetreiveInvitations() {
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
		System.out.println("in side Retrieve Invitations");
        String uname = request.getParameter("uname");
        String data="";
        String code = "6205";
        int projectId=0;
        String projname=null;
        String inviteename=null;
        String role=null;
        try
        {
        	data = "<invitation>";
            st = con.createStatement();
            res = st.executeQuery((new StringBuilder("SELECT * FROM  invitations where proj_member_name='")).append(uname).append("'").toString());
            if(res.next())
            {
                code = "";
                projectId = res.getInt("projectId");
                projname = res.getString("projectname");
                inviteename=res.getString("ScreenName");
                role=res.getString("role");
            }
            data += "<code>"+code+"</code><projectId>"+projectId+"</projectId><projname>"+projname+"</projname><inviteename>"+inviteename+"</inviteename><role>"+role+"</role></invitation>";
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
