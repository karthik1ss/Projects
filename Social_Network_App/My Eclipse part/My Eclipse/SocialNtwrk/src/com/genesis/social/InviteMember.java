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

public class InviteMember extends HttpServlet {
	 	Connection con;
	    Statement st;
	    ResultSet res;
	/**
	 * Constructor of the object.
	 */
	public InviteMember() {
		super();
		
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
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
        System.out.println("Inside Invite Member");
        String code = "6205";
        String success = "";
        System.out.println(data);
        success = parse_a_string(data);
        if(success == "success")
        {
            code = "Member Invited";
            System.out.println("Member Request Stored");
        } else
        {
            System.out.println("Failed to store Member Request");
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
	        String role = null;
	        int projectid=0;
	        String ScreenName = null;
	        String projectname = null;
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
	                NodeList ElmntLst = Elmnt.getElementsByTagName("member_name");
	                Element member_nameElmnt = (Element)ElmntLst.item(0);
	                NodeList member_names = member_nameElmnt.getChildNodes();
	                member_name = member_names.item(0).getNodeValue();
	                System.out.println((new StringBuilder("Member Name : ")).append(member_names.item(0).getNodeValue()).toString());
	                
	                NodeList roleElmntLst = Elmnt.getElementsByTagName("role");
	                Element roleElmnt = (Element)roleElmntLst.item(0);
	                NodeList roles = roleElmnt.getChildNodes();
	                role = roles.item(0).getNodeValue();
	                System.out.println((new StringBuilder("Role : ")).append(roles.item(0).getNodeValue()).toString());
	                
	                NodeList projectidElmntLst = Elmnt.getElementsByTagName("projectid");
	                Element projectidElmnt = (Element)projectidElmntLst.item(0);
	                NodeList projectids = projectidElmnt.getChildNodes();
	                projectid = Integer.parseInt(projectids.item(0).getNodeValue());
	                System.out.println((new StringBuilder("projectid : ")).append(projectids.item(0).getNodeValue()).toString());
	                
	                NodeList ScreenNameElmntLst = Elmnt.getElementsByTagName("ScreenName");
	                Element ScreenNameElmnt = (Element)ScreenNameElmntLst.item(0);
	                NodeList ScreenNames = ScreenNameElmnt.getChildNodes();
	                ScreenName = ScreenNames.item(0).getNodeValue();
	                System.out.println((new StringBuilder("ScreenName : ")).append(ScreenNames.item(0).getNodeValue()).toString());
	                
	                NodeList projectnameElmntLst = Elmnt.getElementsByTagName("projectname");
	                Element projectnameElmnt = (Element)projectnameElmntLst.item(0);
	                NodeList projectnames = projectnameElmnt.getChildNodes();
	                projectname = projectnames.item(0).getNodeValue();
	                System.out.println((new StringBuilder("projectname : ")).append(projectnames.item(0).getNodeValue()).toString());
	                
	                
	            }
	        }
	
	        st = con.createStatement();
	        i = st.executeUpdate("INSERT INTO invitations(ScreenName, proj_member_name,role,projectid,projectname) VALUES " +
	        		"('"+ScreenName+"', '"+member_name+"','"+role+"',"+projectid+",'"+projectname+"')");
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
	
	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
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
