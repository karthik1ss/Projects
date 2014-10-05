package com.genesis.newzonia;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class RegisterMember extends HttpServlet
{

    Connection con;
    Statement st;
    ResultSet res;

    public RegisterMember()
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

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String creationTime = request.getParameter("creationTime");
        String password = request.getParameter("password");
        System.out.println((new StringBuilder("creationTime ")).append(creationTime).toString());
        System.out.println("Inside Register servlet");
        try
        {
            st = con.createStatement();
            res = st.executeQuery("SELECT max(id) FROM  avatar");
            if(res.next())
            {
                int s1 = Integer.parseInt(res.getString("max(id)"));
                System.out.println(s1);
                s1++;
                String regno = (new StringBuilder("reg")).append(s1).toString();
                int i = st.executeUpdate((new StringBuilder("INSERT INTO avatar(regno,firstname,lastname, emailAddress, creationTime, passwor" +
"d) VALUES ('"
)).append(regno).append("','").append(firstName).append("','").append(lastName).append("', '").append(email).append("','").append(creationTime).append("','").append(password).append("')").toString());
                System.out.println(i);
                if(i == 1)
                {
                    out.write("Registration successfull");
                } else
                {
                    out.write("fail");
                }
            } else
            {
                out.write("fail..");
            }
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
