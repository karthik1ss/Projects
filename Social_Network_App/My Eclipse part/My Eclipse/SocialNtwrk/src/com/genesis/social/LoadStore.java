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

public class LoadStore extends HttpServlet {
	Connection con;
    Statement st;
    ResultSet res;
	/**
	 * Constructor of the object.
	 */
	public LoadStore() {
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

	public String uname="";
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		System.out.println("inside Retrieve Store Data");
		uname = request.getParameter("username");
        System.out.println("uname ="+uname);
        String code = "6205";
        String data = "";
        
        try
        {
        	data = "<storedata>";
//        	data+=getStdata(projectId);
//            data+=getUsers(projectId);
                
                data+="<storeimages>"+getAsset("Image")+"</storeimages>";
                data+="<storeVideos>"+getAsset("Video")+"</storeVideos>";
                data+="<storeAudios>"+getAsset("Audio")+"</storeAudios>";
                data+="<storeTexts>"+getAsset("Text")+"</storeTexts>";

                data += "<code>"+code+"</code></storedata>";
            
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
	public String getAsset(String type)
	{
		String str="";
		try
		{
			st = con.createStatement();
	        res = st.executeQuery("SELECT * FROM  products where screenname='"+uname+"' and type='"+type+"' and prodprice!=''");
			String code="6205"; 
	        while(res.next())
	         {
	        	code="";
	             	String contentSource = res.getString("contentSource");
	             	String contentDescription = res.getString("contentDescription");
	             	String contentName = res.getString("contentName");
	             	String contentType = res.getString("contentType");
	             	String assetid = res.getString("assetid");
	             	String status = res.getString("status");
	             	String prodname = res.getString("prodname");
	             	String prodprice = res.getString("prodprice");
	             	String url = res.getString("url");
	             	String projectimgname = res.getString("productimgname");
	             	String imagesource = res.getString("productimagesource");
	             	
	             	
	             	str+="<"+type+"><contentDescription>"+contentDescription+"</contentDescription><contentSource>"+contentSource+"</contentSource><contentName>"+contentName+"</contentName><contentType>"+contentType+"</contentType><assetid>"+assetid+"</assetid>" +
	             			"<status>"+status+"</status><prodowner>"+uname+"</prodowner><prodname>"+prodname+"</prodname><prodprice>"+prodprice+"</prodprice><url>"+url+"</url><projectimgname>"+projectimgname+"</projectimgname><imagesource>"+imagesource+"</imagesource></"+type+">";
	         }
	        str+="<code>"+code+"</code>";
		}
		catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Cannot retrive Data from database server");
        }
		return str;
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
