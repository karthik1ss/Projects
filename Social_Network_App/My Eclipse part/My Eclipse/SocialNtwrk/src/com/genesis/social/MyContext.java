package com.genesis.newzonia;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContext;
import java.sql.DriverManager;
import java.sql.Connection;
public class MyContext implements ServletContextListener
{
	public void contextInitialized(ServletContextEvent sce)
	{
		ServletContext sc= sce.getServletContext();
		String driver = sc.getInitParameter("driver");
		String url = sc.getInitParameter("url");
		String user = sc.getInitParameter("user");
		String pass = sc.getInitParameter("pass");
		try
		{
			Class.forName(driver);
			Connection con=DriverManager.getConnection(url,user,pass);
			sc.setAttribute("connection",con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("context got the connection");
	}

	public void contextDestroyed(ServletContextEvent sce)
	{
		try
		{
			ServletContext sc=sce.getServletContext();
			Connection con=(Connection)sc.getAttribute("connection");
			con.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


	}
	
}