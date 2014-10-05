package com.genesis.newzonia;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class RetreiveEvent extends HttpServlet
{

    Connection con;
    Statement st;
    ResultSet res;

    public RetreiveEvent()
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
        System.out.println("in side Retrieve Events");
        String website = null;
        String eventdate = null;
        String eventdesc = null;
        String eventname = null;
        String eventvenue = null;
        String phone = null;
        String websiteLink = null;
        String imagetype = null;
        String imagename = null;
        String imagesource = null;
        String username = null;
        String code = "6205";
        String data = "";
        String uname = request.getParameter("uname");
        try
        {
            st = con.createStatement();
            res = st.executeQuery((new StringBuilder("SELECT * FROM  events where screenname='")).append(uname).append("'").toString());
            if(res.next())
            {
                code = "";
                website = res.getString("website");
                eventdate = res.getString("eventdate");
                eventdesc = res.getString("eventdesc");
                eventname = res.getString("eventname");
                eventvenue = res.getString("eventvenue");
                phone = res.getString("phone");
                websiteLink = res.getString("website");
                imagetype = res.getString("imagetype");
                imagename = res.getString("imagename");
                imagesource = res.getString("imagesource");
                data = (new StringBuilder("<user><event1><code>")).append(code).append("</code><website>").append(website).append("</website><eventdate>").append(eventdate).append("</eventdate>").append("<eventdesc>").append(eventdesc).append("</eventdesc><eventname>").append(eventname).append("</eventname><eventvenue>").append(eventvenue).append("</eventvenue>").append("<phone>").append(phone).append("</phone><websiteLink>").append(websiteLink).append("</websiteLink><imagetype>").append(imagetype).append("</imagetype>").append("<imagename>").append(imagename).append("</imagename><imagesource>").append(imagesource).append("</imagesource></event1></user>").toString();
            }
           
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
