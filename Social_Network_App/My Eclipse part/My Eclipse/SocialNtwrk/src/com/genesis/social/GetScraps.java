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

public class GetScraps extends HttpServlet {
	Connection con;
    Statement st;
    ResultSet res;
    Statement st1;
    ResultSet res1;
	/**
	 * Constructor of the object.
	 */
	public GetScraps() {
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
            st1.close();
            res1.close();
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
		String ScreenName = request.getParameter("ScreenName");
		System.out.println("in side Get Scraps");
        String code = "6205";
        String data = "";
        
        try
        {
        	st=con.createStatement();
        	res=st.executeQuery("Select * from scraps where touser='"+ScreenName+"'");
        	data+="<scraps>";
        	if(res.next())
        	{
        		code="";
        		do
        		{
        			int MessageId=res.getInt("MessageId");
        			String Message=res.getString("Message");
        			String Posttime=res.getString("Posttime");
        			String fromuser=res.getString("fromuser");
        			String touser=res.getString("touser");
        			data+="<scrap><MessageId>"+MessageId+"</MessageId><Message>"+Message+"</Message><Posttime>"+Posttime+"</Posttime><fromuser>"+fromuser+"</fromuser><contentsource>"+getSource(fromuser)+"</contentsource><touser>"+touser+"</touser></scrap>";
        			
        			
        		}while(res.next());
        	
        	}
        	data+="<code>"+code+"</code></scraps>";
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
	public String getSource(String fromuser)
	{
		try
		{
		st1=con.createStatement();
    	res1=st1.executeQuery("Select * from avatar where screenName='"+fromuser+"'");
    	res1.next();
    	return res1.getString("contentSource");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return "";
		}
		
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
