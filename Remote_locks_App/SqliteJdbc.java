

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;

public class SqliteJdbc{
	
	private String message;
	
	
	
					public static void main(String[] args) {
						 
						
						Connection c = null;
						Statement stmt = null;
						ResultSet rs = null;
					    try 
					    {
					    	Class.forName("org.sqlite.JDBC");
					    	c = DriverManager.getConnection("jdbc:sqlite:acngroup10");
					    	//c = DriverManager.getConnection("jdbc:sqlite:acngroup10.db");
					    	c.setAutoCommit(false);
					    	System.out.println("Opened acngroup10 database successfully");
					    	stmt = c.createStatement();
					    	
					    	rs = stmt.executeQuery("select * from androidclients");
					    	while(rs.next()){
					    		System.out.println("ID:"+rs.getString(1));
					    	}
				
					    	stmt.close();
					    	c.commit();
					    	c.close();
					    	
					    } 
					    catch ( Exception e ) 
					    {
					      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
					      System.exit(0);
					    }
					    System.out.println("Records created successfully");
	    
			
		}
	    
	    



}
