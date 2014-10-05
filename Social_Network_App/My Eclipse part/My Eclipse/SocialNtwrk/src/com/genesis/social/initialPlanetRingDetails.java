package com.genesis.newzonia;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class initialPlanetRingDetails extends HttpServlet
{

    Connection con;
    Statement st;
    ResultSet res;

    public initialPlanetRingDetails()
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
        String planetName = request.getParameter("planetName");
        String ringName = request.getParameter("ringName");
        String expertise = null;
        String tagnames = null;
        String screenName = null;
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
        System.out.println((new StringBuilder("planetName = ")).append(planetName).toString());
        System.out.println((new StringBuilder("ringName = ")).append(ringName).toString());
        try
        {
            st = con.createStatement();
            res = st.executeQuery((new StringBuilder("SELECT * FROM  avatar where planetname='")).append(planetName).append("'and ringname='").append(ringName).append("'").toString());
            if(res.next())
            {
                result = "<planetrings>";
                do
                {
                    screenName = res.getString("screenName");
                    contentSource = res.getString("contentSource");
                    result = (new StringBuilder(String.valueOf(result))).append("<user><screenName>").append(screenName).append("</screenName><contentSource>").append(contentSource).append("</contentSource></user>").toString();
                } while(res.next());
                result = (new StringBuilder(String.valueOf(result))).append("</planetrings>").toString();
            }
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
