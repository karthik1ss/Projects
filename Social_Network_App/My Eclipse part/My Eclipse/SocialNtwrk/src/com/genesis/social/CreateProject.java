package com.genesis.newzonia;

import java.io.*;
import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

// Referenced classes of package com.genesis.newzonia:
//            DateUtils

public class CreateProject extends HttpServlet
{

    Connection con;
    Statement st;
    ResultSet res;

    public CreateProject()
    {
    }

    public void destroy()
    {
        super.destroy();
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
        String data = request.getParameter("data");
        System.out.println("Inside Create Project");
        String code = "6205";
        String success = "";
        System.out.println(data);
        success = parse_a_string(data);
        if(success == "success")
        {
            code = "Article Posted";
            System.out.println("Project successfully created");
        } else
        {
            System.out.println("Failed to Store Project");
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
	        String projname = null;
	        String projdesc = null;
	        String projectimgname = null;
	        String imagesource = null;
	        String ScreenName = null;
	        String closingdate = null;
	        String budgetType = null;
	        String visibility = null;
	        String CreationTime = null;
	        
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
	                NodeList ElmntLst = Elmnt.getElementsByTagName("projname");
	                Element projnameElmnt = (Element)ElmntLst.item(0);
	                NodeList projnames = projnameElmnt.getChildNodes();
	                projname = projnames.item(0).getNodeValue();
	                System.out.println((new StringBuilder("projname : ")).append(projnames.item(0).getNodeValue()).toString());
	                NodeList projdescElmntLst = Elmnt.getElementsByTagName("projdesc");
	                Element projdescElmnt = (Element)projdescElmntLst.item(0);
	                NodeList projdescs = projdescElmnt.getChildNodes();
	                projdesc = projdescs.item(0).getNodeValue();
	                System.out.println((new StringBuilder("projdesc : ")).append(projdescs.item(0).getNodeValue()).toString());
	                NodeList projectimgnameElmntLst = Elmnt.getElementsByTagName("projectimgname");
	                Element projectimgnameElmnt = (Element)projectimgnameElmntLst.item(0);
	                NodeList projectimgnames = projectimgnameElmnt.getChildNodes();
	                projectimgname = projectimgnames.item(0).getNodeValue();
	                System.out.println((new StringBuilder("projectimgname : ")).append(projectimgnames.item(0).getNodeValue()).toString());
	                NodeList imagesourceElmntLst = Elmnt.getElementsByTagName("imagesource");
	                Element imagesourceElmnt = (Element)imagesourceElmntLst.item(0);
	                NodeList imagesources = imagesourceElmnt.getChildNodes();
	                imagesource = imagesources.item(0).getNodeValue();
	                System.out.println((new StringBuilder("imagesource : ")).append(imagesources.item(0).getNodeValue()).toString());
	                NodeList ScreenNameElmntLst = Elmnt.getElementsByTagName("ScreenName");
	                Element ScreenNameElmnt = (Element)ScreenNameElmntLst.item(0);
	                NodeList ScreenNames = ScreenNameElmnt.getChildNodes();
	                ScreenName = ScreenNames.item(0).getNodeValue();
	                System.out.println((new StringBuilder("ScreenName : ")).append(ScreenNames.item(0).getNodeValue()).toString());
	                
	                NodeList closingdateElmntLst = Elmnt.getElementsByTagName("closingdate");
	                Element closingdateElmnt = (Element)closingdateElmntLst.item(0);
	                NodeList closingdates = closingdateElmnt.getChildNodes();
	                closingdate = closingdates.item(0).getNodeValue();
	                System.out.println((new StringBuilder("closingdate : ")).append(closingdates.item(0).getNodeValue()).toString());
	                
	                NodeList budgetTypeElmntLst = Elmnt.getElementsByTagName("budgetType");
	                Element budgetTypeElmnt = (Element)budgetTypeElmntLst.item(0);
	                NodeList budgetTypes = budgetTypeElmnt.getChildNodes();
	                budgetType = budgetTypes.item(0).getNodeValue();
	                System.out.println((new StringBuilder("budgetType : ")).append(budgetTypes.item(0).getNodeValue()).toString());
	                
	                NodeList visibilityElmntLst = Elmnt.getElementsByTagName("visibility");
	                Element visibilityElmnt = (Element)visibilityElmntLst.item(0);
	                NodeList visibilitys = visibilityElmnt.getChildNodes();
	                visibility = visibilitys.item(0).getNodeValue();
	                System.out.println((new StringBuilder("visibility : ")).append(visibilitys.item(0).getNodeValue()).toString());
	                
	                NodeList CreationTimeElmntLst = Elmnt.getElementsByTagName("CreationTime");
	                Element CreationTimeElmnt = (Element)CreationTimeElmntLst.item(0);
	                NodeList CreationTimes = CreationTimeElmnt.getChildNodes();
	                CreationTime = CreationTimes.item(0).getNodeValue();
	                System.out.println((new StringBuilder("CreationTime : ")).append(CreationTimes.item(0).getNodeValue()).toString());
	                
	            }
	        }
	
	        st = con.createStatement();
	        i = st.executeUpdate("INSERT INTO projects(ScreenName, projname,projdesc,projectimgname,imagesource,creationtime,closingdate,budgetType,visibility) VALUES " +
	        		"('"+ScreenName+"', '"+projname+"','"+projdesc+"','"+projectimgname+"','"+imagesource+"','"+CreationTime+"','"+closingdate+"','"+budgetType+"','"+visibility+"')");
	        if(i == 1)
	        {
	           
	            res=st.executeQuery("select projectId from projects where projname='"+projname+"'");
	            if(res.next())
	            {
	            	int projectid= Integer.parseInt(res.getString("projectId"));
	            	
	            	int j = st.executeUpdate("INSERT INTO projectdata(proj_member_name,role,projectid,projectname) VALUES " +
			        		"('"+ScreenName+"','Owner',"+projectid+",'"+projname+"')");
	            	if(j==1)
	            	{
	            		return "success";
	            	}
	            }
	        }       
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Cannot retrive Data from database server");
        }
        return "fail";
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
