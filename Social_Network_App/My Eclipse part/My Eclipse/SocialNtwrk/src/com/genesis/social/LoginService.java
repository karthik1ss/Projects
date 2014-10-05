package com.genesis.newzonia;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class LoginService extends HttpServlet
{

    Connection con;
    Statement st;
    ResultSet res;

    public LoginService()
    {
        con = null;
        st = null;
        res = null;
    }

    public void destroy()
    {
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
        super.destroy();
    }

    public void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String uname = request.getParameter("LOGIN");
        String pwd = request.getParameter("PASSWORD");
        String email = null;
        String contentSource = null;
        String code = "6205";
        String data = "";
        String isprofileexist = "0";
        String screenName = "";
        try
        {
            st = con.createStatement();
            res = st.executeQuery((new StringBuilder("SELECT * FROM  avatar where emailAddress='")).append(uname).append("' and password='").append(pwd).append("'").toString());
            if(res.next())
            {
                email = res.getString("emailAddress");
                code = null;
                isprofileexist = res.getString("isprofileexist");
                screenName = res.getString("screenName");
                contentSource = res.getString("contentSource");
                System.out.println("Login success");
            }
            data = (new StringBuilder("<user><code>")).append(code).append("</code><email>").append(email).append("</email><isprofileexist>").append(isprofileexist).append("</isprofileexist><screenName>").append(screenName).append("</screenName><contentSource>").append(contentSource).append("</contentSource></user>").toString();
            out.write(data);
            System.out.println((new StringBuilder("data = ")).append(data).toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Cannot retrive Data from database server");
        }
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
