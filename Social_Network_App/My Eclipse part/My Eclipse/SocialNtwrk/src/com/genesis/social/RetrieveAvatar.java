package com.genesis.newzonia;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class RetrieveAvatar extends HttpServlet
{

    Connection con;
    Statement st;
    ResultSet res;

    public RetrieveAvatar()
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
        System.out.println("in side Retrieve Avatar");
        String planetname = null;
        String ringname = null;
        String expertise = null;
        String tagnames = null;
        String screenName = null;
        String websiteLink = null;
        String personalDescription = null;
        String country = null;
        String contentSource = "";
        String code = "6205";
        String data = "";
        String uname = request.getParameter("uname");
        System.out.println(uname);
        try
        {
            st = con.createStatement();
            res = st.executeQuery((new StringBuilder("SELECT * FROM  avatar where screenName='")).append(uname).append("'").toString());
            if(res.next())
            {
                code = "";
                planetname = res.getString("planetname");
                ringname = res.getString("ringname");
                expertise = res.getString("expertise");
                tagnames = res.getString("tagnames");
                screenName = res.getString("screenName");
                websiteLink = res.getString("websiteLink");
                personalDescription = res.getString("personalDescription");
                country = res.getString("country");
                contentSource = res.getString("contentSource");
                code = "";
            }
            data = (new StringBuilder("<user><event1><code>")).append(code).append("</code><planetname>").append(planetname).append("</planetname><ringname>").append(ringname).append("</ringname>").append("<expertise>").append(expertise).append("</expertise><tagnames>").append(tagnames).append("</tagnames><screenName>").append(screenName).append("</screenName>").append("<websiteLink>").append(websiteLink).append("</websiteLink><personalDescription>").append(personalDescription).append("</personalDescription><country>").append(country).append("</country>").append("<imagesource>").append(contentSource).append("</imagesource></event1></user>").toString();
            out.write(data);
            System.out.println((new StringBuilder("data = ")).append(data).toString());
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
