package com.genesis.newzonia;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class RetreiveArticles extends HttpServlet
{

    Connection con;
    Statement st;
    ResultSet res;

    public RetreiveArticles()
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
        System.out.println("in side Retrieve Articles");
        String ArticleName = null;
        String Article = null;
        String posttime = null;
        String code = "6205";
        String data = "";
        String comments = "";
        String commentsallowed = "";
        String uname = request.getParameter("uname");
        data = "<user>";
        String s1 = "";
        try
        {
            st = con.createStatement();
            res = st.executeQuery((new StringBuilder("SELECT * FROM  articles where screenname='")).append(uname).append("'").toString());
            if(res.next())
            {
                code = "";
                do
                {
                    String username = res.getString("screenname");
                    ArticleName = res.getString("ArticleName");
                    Article = res.getString("Article");
                    commentsallowed = res.getString("commentsallowed");
                    posttime = res.getString("posttime");
                    String ArticleId = res.getString("ArticleId");
                    s1 = (new StringBuilder(String.valueOf(s1))).append(username).append("$").append(ArticleName).append("$").append(Article).append("$").append(commentsallowed).append("$").append(posttime).append("$").append(ArticleId).append("$").toString();
                } while(res.next());
            }
            data = (new StringBuilder(String.valueOf(data))).append("<code>").append(code).append("</code><articles>").append(s1).append("</articles></user>").toString();
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
