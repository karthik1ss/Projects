package com.genesis.newzonia;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class BrandView extends HttpServlet
{

    Connection con;
    Statement st;
    ResultSet res;

    public BrandView()
    {
        con = null;
        st = null;
        res = null;
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
        String result = "";
        String screenName = request.getParameter("Id");
        System.out.println((new StringBuilder("Screen name = ")).append(screenName).toString());
        String expertise = null;
        String tagnames = null;
        String websiteLink = null;
        String personalDescription = null;
        String emailAddress = null;
        String country = null;
        String isprofileexist = null;
        String firstname = null;
        String lastname = null;
        String contentSource = null;
        String representationname = null;
        String representationcontentType = null;
        String planetname = null;
        String ringname = null;
        String code = "6205";
        try
        {
            st = con.createStatement();
            res = st.executeQuery((new StringBuilder("SELECT * FROM  avatar where screenName='")).append(screenName).append("'").toString());
            if(res.next())
            {
                screenName = res.getString("screenName");
                contentSource = res.getString("contentSource");
                planetname = res.getString("planetname");
                ringname = res.getString("ringname");
                code = "";
            }
            result = "<planetrings>";
            result = (new StringBuilder(String.valueOf(result))).append("<user><code>").append(code).append("</code><screenName>").append(screenName).append("</screenName><contentSource>").append(contentSource).append("</contentSource><planetname>").append(planetname).append("</planetname><ringname>").append(ringname).append("</ringname></user>").toString();
            result = (new StringBuilder(String.valueOf(result))).append("</planetrings>").toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Cannot retrive Data from database server");
        }
        System.out.println(result);
        out.write(result);
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
