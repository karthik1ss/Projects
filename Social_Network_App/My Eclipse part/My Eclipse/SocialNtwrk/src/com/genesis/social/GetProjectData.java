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

public class GetProjectData extends HttpServlet {
	Connection con;
    Statement st;
    Statement st1;
    ResultSet res;
    ResultSet res1;
	/**
	 * Constructor of the object.
	 */
	public GetProjectData() {
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
	  String proj_member_name = null;
      String contentSource = null;
      String contentDescription = null;
      String contentName = null;
      String contentType = null;
      String projectassetType = null;
      int projectId=0;
	
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		System.out.println("inside Retrieve Project Members");
        projectId = Integer.parseInt(request.getParameter("projectid"));
        System.out.println("projectid ="+projectId);
        String code = "6205";
        String data = "";
        
        try
        {
        	data = "<projects>";
        	data+=getProjectdata(projectId);
            data+=getUsers(projectId);
                
                data+="<projectimages>"+getAsset("Image")+"</projectimages>";
                data+="<projectVideos>"+getAsset("Video")+"</projectVideos>";
                data+="<projectAudios>"+getAsset("Audio")+"</projectAudios>";
                data+="<projectTexts>"+getAsset("Text")+"</projectTexts>";
                
            
                data = (new StringBuilder(String.valueOf(data))+"<code>"+code+"</code></projects>").toString();
            
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
	
	public String getUsers(int id)
	{
		String data="";
		String code="6205";
		try
		{
			st1 = con.createStatement();
			st = con.createStatement();
            res = st.executeQuery("SELECT * FROM  projectdata where projectid="+id);
            	data+="<Users>";
               
                while(res.next())
                {
                	 code = "";
                    proj_member_name = res.getString("proj_member_name");
                    st1 = con.createStatement();
                    res1 = st1.executeQuery("SELECT * FROM  avatar where screenName='"+proj_member_name+"'");
                    if(res1.next())
                    {
                    	contentSource = res1.getString("contentSource");
                    	data+="<user><screenName>"+proj_member_name+"</screenName><contentSource>"+contentSource+"</contentSource></user>";
                    }
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
	
	
	public String getProjectdata(int id)
	{
		String str="";
		try
		{
			st1 = con.createStatement();
	        res1 = st1.executeQuery("SELECT * FROM  projects where projectid="+id);
			String code="6205";
			str+="<projectdata>";
	        if(res1.next())
	         {
	        	code="";
	             	String projectId = res1.getString("projectId");
	             	String projname = res1.getString("projname");
	             	String projdesc = res1.getString("projdesc");
	             	String imagesource = res1.getString("imagesource");
	             	String creationtime = res1.getString("creationtime");
	             	String closingdate = res1.getString("closingdate");
	             	String visibility = res1.getString("visibility");
	             	String budgetType = res1.getString("budgetType");
	             	
	             	str+="<projectId>"+projectId+"</projectId><projname>"+projname+"</projname><projdesc>"+projdesc+"</projdesc><imagesource>"+imagesource+"</imagesource><creationtime>"+creationtime+"</creationtime><closingdate>"+closingdate+"</closingdate><visibility>"+visibility+"</visibility><budgetType>"+budgetType+"</budgetType>";
	         }
	        str+="<code>"+code+"</code></projectdata>";
		}
		catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Cannot retrive Data from database server");
        }
		return str;
	}
	
	public String getAsset(String type)
	{
		String str="";
		try
		{
			st1 = con.createStatement();
	        res1 = st1.executeQuery("SELECT * FROM  projectassets where projectid="+projectId+" and type='"+type+"'");
			String code="6205"; 
	        while(res1.next())
	         {
	        	code="";
	             	contentSource = res1.getString("contentSource");
	             	contentDescription = res1.getString("contentDescription");
	             	contentName = res1.getString("contentName");
	             	contentType = res1.getString("contentType");
	             	projectassetType = res1.getString("projectassetType");
	             	
	             	str+="<"+type+"><contentDescription>"+contentDescription+"</contentDescription><contentSource>"+contentSource+"</contentSource><contentName>"+contentName+"</contentName><contentType>"+contentType+"</contentType><projectassetType>"+projectassetType+"</projectassetType></"+type+">";
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
