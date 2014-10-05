package com.genesis.newzonia;
import java.io.*;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class DeleteArticle extends HttpServlet
{

    Connection con;
    Statement st;
    ResultSet res;

    public DeleteArticle()
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
            System.err.println("Not able to close connections");
        }
    }

    public void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        System.out.println("Inside Delete article");
        int articleid = Integer.parseInt(request.getParameter("Id"));
        System.out.println("Artilce Id = "+articleid);
        try
        {
            st = con.createStatement();
            int i = st.executeUpdate("DELETE FROM articles WHERE ArticleId = "+articleid);
            if(i != 0)
            {
                System.out.println(i);
                out.write("successfully Deleted");
            } else
            {
                System.out.println(i);
                out.write("Unable to Delete");
            }
        }
        catch(Throwable e)
        {
            e.printStackTrace();
            System.err.println("Cannot execute query on database server");
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
