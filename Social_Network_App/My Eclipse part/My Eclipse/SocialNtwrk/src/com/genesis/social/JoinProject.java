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

public class JoinProject extends HttpServlet {
	Connection con;
    Statement st;
    ResultSet res;
	/**
	 * Constructor of the object.
	 */
	public JoinProject() {
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
		
		String projname = request.getParameter("projname");
		String ScreenName = request.getParameter("ScreenName");
		String acceptance_status = request.getParameter("acceptance_status");
		String role = request.getParameter("role");
		int projectid = Integer.parseInt(request.getParameter("projectid"));
		
        System.out.println("Inside Join Project");
        String code = "6205";
		int delete=0,update=0;
        try
        {
            st = con.createStatement();
            int i=0;
            i = st.executeUpdate("Delete FROM invitations where proj_member_name='"+ScreenName+"' and projectid="+projectid);
            if(i!=0)
            {
            	delete=1;
            	System.out.println("Record deleted from invitations");
            }
            if(acceptance_status.equals("Accepted"))
            {
	            i = st.executeUpdate("INSERT INTO projectdata(proj_member_name,role,projectid,projectname) VALUES " +
		        		"('"+ScreenName+"','"+role+"',"+projectid+",'"+projname+"')");
		        if(i == 1)
		        {
		        	update=1;
		            System.out.println("Project successfully joined");
		        }
		        else
		        {
		        	update=0;
		        	System.out.println("Unable to join Project");
		        }
            }
            if(delete==1 && acceptance_status.equals("Accepted") && update==1)
            {
            	code="Project successfully joined";
            }
            else if(delete==1 && acceptance_status.equals("Rejected") && update==0)
            {
            	code="Project rejected successfully";
            }
            else
            {
            	code="Unable to Process";
            }
            out.write(code);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            code="Unable to Process";
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
