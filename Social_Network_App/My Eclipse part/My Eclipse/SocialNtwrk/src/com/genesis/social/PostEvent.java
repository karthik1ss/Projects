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

public class PostEvent extends HttpServlet
{

    Connection con;
    Statement st;
    ResultSet res;

    public PostEvent()
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
        String result = "";
        String data = request.getParameter("data");
        String code = "6205";
        String success = "";
        System.out.println(data);
        success = parse_a_string(data);
        if(success == "success")
        {
            code = "Registration successfull";
            System.out.println("Event successfully stored");
        } else
        {
            System.out.println("Failed to Store Event");
        }
        result = (new StringBuilder("<user><code>")).append(code).append("</code><success>").append(success).append("</success></user>").toString();
        System.out.println(result);
        out.write(result);
        out.flush();
        out.close();
    }

    private String parse_a_string(String xmlstring)
    {
        int i;
        String emailAddress;
        i = 0;
        emailAddress = null;
        try
        {		        
		        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder db = factory.newDocumentBuilder();
		        InputSource inStream = new InputSource();
		        inStream.setCharacterStream(new StringReader(xmlstring));
		        Document doc = db.parse(inStream);
		        String country = null;
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
		        st = con.createStatement();
		        doc.getDocumentElement().normalize();
		        System.out.println((new StringBuilder("Root element ")).append(doc.getDocumentElement().getNodeName()).toString());
		        NodeList nodeLst = doc.getElementsByTagName("data");
		        System.out.println("Information of entire data");
		        for(int s = 0; s < nodeLst.getLength(); s++)
		        {
		            Node fstNode = nodeLst.item(s);
		            if(fstNode.getNodeType() == 1)
		            {
		                Element Elmnt = (Element)fstNode;
		                NodeList ElmntLst = Elmnt.getElementsByTagName("eventname");
		                Element eventnameNmElmnt = (Element)ElmntLst.item(0);
		                NodeList eventnames = eventnameNmElmnt.getChildNodes();
		                eventname = eventnames.item(0).getNodeValue();
		                System.out.println((new StringBuilder("eventname : ")).append(eventnames.item(0).getNodeValue()).toString());
		                NodeList eventdateeElmntLst = Elmnt.getElementsByTagName("eventdate");
		                Element eventdateElmnt = (Element)eventdateeElmntLst.item(0);
		                NodeList eventdates = eventdateElmnt.getChildNodes();
		                eventdate = eventdates.item(0).getNodeValue();
		                System.out.println((new StringBuilder("eventdate : ")).append(eventdates.item(0).getNodeValue()).toString());
		                NodeList eventvenueElmntLst = Elmnt.getElementsByTagName("eventvenue");
		                Element eventvenueElmnt = (Element)eventvenueElmntLst.item(0);
		                NodeList eventvenues = eventvenueElmnt.getChildNodes();
		                eventvenue = eventvenues.item(0).getNodeValue();
		                System.out.println((new StringBuilder("eventvenue : ")).append(eventvenues.item(0).getNodeValue()).toString());
		                NodeList eventdescElmntLst = Elmnt.getElementsByTagName("eventdesc");
		                Element eventdescElmnt = (Element)eventdescElmntLst.item(0);
		                NodeList eventdescs = eventdescElmnt.getChildNodes();
		                eventdesc = eventdescs.item(0).getNodeValue();
		                System.out.println((new StringBuilder("eventdesc : ")).append(eventdescs.item(0).getNodeValue()).toString());
		                NodeList websiteElmntLst = Elmnt.getElementsByTagName("website");
		                Element websiteElmnt = (Element)websiteElmntLst.item(0);
		                NodeList websiteNames = websiteElmnt.getChildNodes();
		                website = websiteNames.item(0).getNodeValue();
		                System.out.println((new StringBuilder("website : ")).append(websiteNames.item(0).getNodeValue()).toString());
		                NodeList phoneElmntLst = Elmnt.getElementsByTagName("phone");
		                Element phoneElmnt = (Element)phoneElmntLst.item(0);
		                NodeList phones = phoneElmnt.getChildNodes();
		                phone = phones.item(0).getNodeValue();
		                System.out.println((new StringBuilder("phone : ")).append(phones.item(0).getNodeValue()).toString());
		                NodeList imagetypeElmntLst = Elmnt.getElementsByTagName("imagetype");
		                Element imagetypeElmnt = (Element)imagetypeElmntLst.item(0);
		                NodeList imagetypes = imagetypeElmnt.getChildNodes();
		                imagetype = imagetypes.item(0).getNodeValue();
		                System.out.println((new StringBuilder("imagetype : ")).append(imagetypes.item(0).getNodeValue()).toString());
		                NodeList imagenameElmntLst = Elmnt.getElementsByTagName("imagename");
		                Element imagenameElmnt = (Element)imagenameElmntLst.item(0);
		                NodeList imagenames = imagenameElmnt.getChildNodes();
		                imagename = imagenames.item(0).getNodeValue();
		                System.out.println((new StringBuilder("imagename : ")).append(imagenames.item(0).getNodeValue()).toString());
		                NodeList imagesourceElmntLst = Elmnt.getElementsByTagName("imagesource");
		                Element imagesourceElmnt = (Element)imagesourceElmntLst.item(0);
		                NodeList imagesources = imagesourceElmnt.getChildNodes();
		                imagesource = imagesources.item(0).getNodeValue();
		                System.out.println((new StringBuilder("imagesource : ")).append(imagesources.item(0).getNodeValue()).toString());
		                NodeList usernameElmntLst = Elmnt.getElementsByTagName("username");
		                Element usernameElmnt = (Element)usernameElmntLst.item(0);
		                NodeList usernames = usernameElmnt.getChildNodes();
		                username = usernames.item(0).getNodeValue();
		                System.out.println((new StringBuilder("Screen name : ")).append(usernames.item(0).getNodeValue()).toString());
		            }
		        }
		
		        res = st.executeQuery((new StringBuilder("Select * from events where screenname='")).append(username).append("'").toString());
		        if(res.next())
		        {
		            i = st.executeUpdate((new StringBuilder("update events set eventname='")).append(eventname).append("',eventdate='").append(eventdate).append("',eventvenue='").append(eventvenue).append("',").append("eventdesc='").append(eventdesc).append("',website='").append(website).append("',phone='").append(phone).append("',").append("imagetype='").append(imagetype).append("',imagename='").append(imagename).append("',imagesource='").append(imagesource).append("'where screenname='").append(username).append("'").toString());
		        } else
		        {
		            i = st.executeUpdate((new StringBuilder("INSERT INTO events(eventname,eventdate,eventvenue, eventdesc, website, phone,ima" +
		"getype,imagename,imagesource,screenname) VALUES ('"
		)).append(eventname).append("','").append(eventdate).append("','").append(eventvenue).append("', '").append(eventdesc).append("','").append(website).append("','").append(phone).append("','").append(imagetype).append("','").append(imagename).append("','").append(imagesource).append("','").append(username).append("')").toString());
		        }
		        if(i == 1)
		        {
		            return "success";
		        } else
		        {
		            return "fail";
		        }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Cannot close connection to database server");
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
