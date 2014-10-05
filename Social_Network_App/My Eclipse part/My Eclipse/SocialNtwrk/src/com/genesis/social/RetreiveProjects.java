package com.genesis.newzonia;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class RetreiveProjects extends HttpServlet
{

    Connection con;
    Statement st;
    ResultSet res;

    public RetreiveProjects()
    {
    }

    public void destroy()
    {
        super.destroy();
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
        System.out.println("in side Retrieve Projects");
        String uname = request.getParameter("uname");
        System.out.println("uname ="+uname);
        String imagesource = null;
        String code = "6205";
        String data = "";
        String projectId = null;
        String projname = null;
        String projdesc = null;
        String creationtime = null;
        String closingdate = null;
        String budgetType = null;
        String visibility = null;
        try
        {
        	data = "<projects>";
            st = con.createStatement();
            res = st.executeQuery((new StringBuilder("SELECT * FROM  projects where screenname='")).append(uname).append("'").toString());
            if(res.next())
            {
                code = "";
                
                do
                {
                    projectId = res.getString("projectId");
                    projname = res.getString("projname");
                    projdesc = res.getString("projdesc");
                    imagesource = res.getString("imagesource");
                    creationtime = res.getString("creationtime");
                    closingdate = res.getString("closingdate");
                    budgetType = res.getString("budgetType");
                    visibility = res.getString("visibility");
                    data +="<project><projectId>"+projectId+"</projectId><projname>"+projname+"</projname>"+"<projdesc>"+projdesc+"</projdesc><imagesource>"+imagesource+"</imagesource>"+"<creationtime>"+creationtime+"</creationtime>" +
                    		"<closingdate>"+closingdate+"</closingdate><budgetType>"+budgetType+"</budgetType><visibility>"+visibility+"</visibility></project>";
                } while(res.next());
                
            }
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

    public void init()
        throws ServletException
    {
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
