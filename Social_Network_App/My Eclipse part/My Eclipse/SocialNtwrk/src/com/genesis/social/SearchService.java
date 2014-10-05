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

public class SearchService extends HttpServlet {
	Connection con;
    Statement st;
    ResultSet res;
	/**
	 * Constructor of the object.
	 */
	public SearchService() {
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
		System.out.println("inside Search Service");
		String searchstring = request.getParameter("searchString");
        String data = "";
        try
        {
        		data = "<searchResults>";
                data+=searchUsers(searchstring);
                data+=searchEvents(searchstring);
                data+=searchProjects(searchstring);
                data+=searchStore(searchstring);
                data += "</searchResults>";
            
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

	public String searchUsers(String str)
	{
		String data="";
		String code="6205";
		try
		{
			st = con.createStatement();
            res = st.executeQuery("SELECT * FROM avatar WHERE  isprofileexist = '1' and expertise like '%"+str+"%' or tagnames like '%"+str+"%' or screenName like '%"+str+"%' or websiteLink like '%"+str+"%' or personalDescription like '%"+str+"%' or firstname like '%"+str+"%' or lastname like '%"+str+"%' or emailAddress like '%"+str+"%'");
            	data="<Users>";
               
                while(res.next())
                {
                	 code = "";
                    String username = res.getString("screenName");
                    String contentSource = res.getString("contentSource");
                    if(username != null)
                    	data+="<user><screenName>"+username+"</screenName><contentSource>"+contentSource+"</contentSource></user>";
                }
                data+="<code>"+code+"</code></Users>";
		}
		catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Cannot retrive Data from database server");
        }
		return data;
	}
	
	public String searchEvents(String str)
	{
		String data="";
		String code="6205";
		try
		{
			st = con.createStatement();
            res = st.executeQuery("SELECT * FROM events WHERE eventname like '%"+str+"%' or eventdate like '%"+str+"%' or eventvenue like '%"+str+"%' or eventdesc like '%"+str+"%' or website like '%"+str+"%' or phone like '%"+str+"%'");
            	data="<Events>";
               
                while(res.next())
                {
                	 code = "";
                    String eventname = res.getString("eventname");
                    String imagesource = res.getString("imagesource");
                    String screenname = res.getString("screenname");
                    
                    data+="<event><eventname>"+eventname+"</eventname><imagesource>"+imagesource+"</imagesource><screenname>"+screenname+"</screenname></event>";
                }
                data+="<code>"+code+"</code></Events>";
		}
		catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Cannot retrive Data from database server");
        }
		return data;
	}
	
	public String searchProjects(String str)
	{
		String data="";
		String code="6205";
		try
		{
			st = con.createStatement();
            res = st.executeQuery("SELECT * FROM projects WHERE projname like '%"+str+"%' or projdesc like '%"+str+"%' or closingdate like '%"+str+"%' or creationtime like '%"+str+"%'");
            	data="<Projects>";
               
                while(res.next())
                {
                	 code = "";
                    String projname = res.getString("projname");
                    String projectId = res.getString("projectId");
                    String imagesource = res.getString("imagesource");
                    String visibility = res.getString("visibility");
                    data+="<project><visibility>"+visibility+"</visibility><projname>"+projname+"</projname><projectId>"+projectId+"</projectId><imagesource>"+imagesource+"</imagesource></project>";
                }
                data+="<code>"+code+"</code></Projects>";
		}
		catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Cannot retrive Data from database server");
        }
		return data;
	}
	public String searchStore(String str)
	{
		String data="";
		String code="6205";
		try
		{
			st = con.createStatement();
            res = st.executeQuery("SELECT * FROM products WHERE prodname like '%"+str+"%' or prodprice like '%"+str+"%' or status like '%"+str+"%' or contentDescription like '%"+str+"%'");
            	data="<Products>";
               
                while(res.next())
                {
                	code = "";
                    String username = res.getString("screenName");
                    String prodname = res.getString("prodname");
                    String contentSource = res.getString("productimagesource");
                    
                    data+="<product><screenName>"+username+"</screenName><prodname>"+prodname+"</prodname><contentSource>"+contentSource+"</contentSource></product>";
                }
                data+="<code>"+code+"</code></Products>";
		}
		catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Cannot retrive Data from database server");
        }
		return data;
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
