package com.genesis.newzonia;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class UserApproval extends HttpServlet {

	Connection con;
    Statement st;
    ResultSet res;
	public UserApproval() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
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
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String result = "";
        String data = request.getParameter("data");
        System.out.println("Inside Member Approval");
        String code = "6205";
        String success = "";
        System.out.println(data);
        success = parse_a_string(data);
        if(success == "success")
        {
            code = "Member Accepted";
            System.out.println("Member Request Accepted");
        } else
        {
            System.out.println("Member Request Rejected");
        }
        result = "<user><code>"+code+"</code><success>"+success+"</success></user>";
        out.write(result);
		out.flush();
		out.close();
	}

	private String parse_a_string(String xmlstring)
    {
        int i;
        i = 0;
        try
        {
        		DocumentBuilderFactory factory =
        		DocumentBuilderFactory.newInstance();
        		DocumentBuilder db = factory.newDocumentBuilder();
        		InputSource inStream = new InputSource();
        		inStream.setCharacterStream(new StringReader(xmlstring));
        		Document doc = db.parse(inStream);
	        String member_name = null;
	        int projectid=0;
	        String UserName = null;
	        String ScreenName = null;
	       	        
	        st = con.createStatement();
	        doc.getDocumentElement().normalize();
	        System.out.println("Root element "+doc.getDocumentElement().getNodeName().toString());
	        NodeList nodeLst = doc.getElementsByTagName("data");
	        System.out.println("Information of entire data");
	        for(int s = 0; s < nodeLst.getLength(); s++)
	        {
	            Node fstNode = nodeLst.item(s);
	            if(fstNode.getNodeType() == 1)
	            {
	                Element Elmnt = (Element)fstNode;
	                NodeList ElmntLst = Elmnt.getElementsByTagName("UserName");
	                Element UserNameElmnt = (Element)ElmntLst.item(0);
	                NodeList UserNames = UserNameElmnt.getChildNodes();
	                UserName = UserNames.item(0).getNodeValue();
	                System.out.println((new StringBuilder("User Name : ")).append(UserNames.item(0).getNodeValue()).toString());
	                	                              
	                NodeList projectidElmntLst = Elmnt.getElementsByTagName("projectid");
	                Element projectidElmnt = (Element)projectidElmntLst.item(0);
	                NodeList projectids = projectidElmnt.getChildNodes();
	                projectid = Integer.parseInt(projectids.item(0).getNodeValue());
	                System.out.println((new StringBuilder("projectid : ")).append(projectids.item(0).getNodeValue()).toString());
	                
	            }
	        }
	
	        st = con.createStatement();
	        i = st.executeUpdate("INSERT INTO projectdata(ScreenName,projectid) VALUES ('"+UserName+"',"+projectid+")");
	        if(i == 1)
	        {
	            return "success";
	        } 
       
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Cannot retrive Data from database server");
        }
        return "fail";
    }
	public void init() throws ServletException {
		// Put your code here
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
