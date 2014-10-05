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

public class RegisterAvatar extends HttpServlet
{

    Connection con;
    Statement st;
    ResultSet res;

    public RegisterAvatar()
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
        String code = "failed to register";
        String email = "";
        boolean isprofileexist = false;
        System.out.println(data);
        email = parse_a_string(data);
        result ="<user>";
        if(email != null)
        {
        	String s[]= email.split(" "); 
            code = "Registration successfull";
            isprofileexist = true;
            result += "<screenname>"+s[1]+"</screenname>";
            result +="<email>"+s[0]+"</email>";
            System.out.println("Avatar successfully created");
        } else
        {
            System.out.println("Failed to create Avatar");
        }
        result += "<code>"+code+"</code><email>"+email+"</email><isprofileexist>"+isprofileexist+"</isprofileexist></user>";
        out.write(result);
        out.flush();
        out.close();
    }

    private String parse_a_string(String xmlstring)
    {
        int i = 0;
        String str = null;
        
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            InputSource inStream = new InputSource();
            inStream.setCharacterStream(new StringReader(xmlstring));
            Document doc = db.parse(inStream);
            String planetname = null;
            String ringname = null;
            String expertise = null;
            String tagnames = null;
            String screenName = null;
            String websiteLink = null;
            String personalDescription = null;
            String country = null;
            String contentSource = null;
            String representationname = null;
            String representationcontentType = null;
            String emailAddress = null;
           
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
                    NodeList ElmntLst = Elmnt.getElementsByTagName("planetname");
                    Element planetnameNmElmnt = (Element)ElmntLst.item(0);
                    NodeList planetnames = planetnameNmElmnt.getChildNodes();
                    planetname = planetnames.item(0).getNodeValue();
                    System.out.println((new StringBuilder("planetname : ")).append(planetnames.item(0).getNodeValue()).toString());
                    NodeList ringnameElmntLst = Elmnt.getElementsByTagName("ringname");
                    Element ringnameElmnt = (Element)ringnameElmntLst.item(0);
                    NodeList ringnames = ringnameElmnt.getChildNodes();
                    ringname = ringnames.item(0).getNodeValue();
                    System.out.println((new StringBuilder("ringname : ")).append(ringnames.item(0).getNodeValue()).toString());
                    NodeList expertiseElmntLst = Elmnt.getElementsByTagName("expertise");
                    Element expertiseElmnt = (Element)expertiseElmntLst.item(0);
                    NodeList expertises = expertiseElmnt.getChildNodes();
                    expertise = expertises.item(0).getNodeValue();
                    System.out.println((new StringBuilder("expertise : ")).append(expertises.item(0).getNodeValue()).toString());
                    NodeList tagnamesElmntLst = Elmnt.getElementsByTagName("tagnames");
                    Element tagnamesElmnt = (Element)tagnamesElmntLst.item(0);
                    NodeList tagnamess = tagnamesElmnt.getChildNodes();
                    tagnames = tagnamess.item(0).getNodeValue();
                    System.out.println((new StringBuilder("tagnames : ")).append(tagnamess.item(0).getNodeValue()).toString());
                    NodeList screenNameElmntLst = Elmnt.getElementsByTagName("screenName");
                    Element screenNameElmnt = (Element)screenNameElmntLst.item(0);
                    NodeList screenNames = screenNameElmnt.getChildNodes();
                    screenName = screenNames.item(0).getNodeValue();
                    System.out.println((new StringBuilder("screenName : ")).append(screenNames.item(0).getNodeValue()).toString());
                    NodeList websiteLinkElmntLst = Elmnt.getElementsByTagName("websiteLink");
                    Element websiteLinkElmnt = (Element)websiteLinkElmntLst.item(0);
                    NodeList websiteLinks = websiteLinkElmnt.getChildNodes();
                    websiteLink = websiteLinks.item(0).getNodeValue();
                    System.out.println((new StringBuilder("websiteLink : ")).append(websiteLinks.item(0).getNodeValue()).toString());
                    NodeList personalDescriptionElmntLst = Elmnt.getElementsByTagName("personalDescription");
                    Element personalDescriptionElmnt = (Element)personalDescriptionElmntLst.item(0);
                    NodeList personalDescriptions = personalDescriptionElmnt.getChildNodes();
                    personalDescription = personalDescriptions.item(0).getNodeValue();
                    System.out.println((new StringBuilder("personalDescription : ")).append(personalDescriptions.item(0).getNodeValue()).toString());
                    NodeList emailAddressElmntLst = Elmnt.getElementsByTagName("emailAddress");
                    Element emailAddressElmnt = (Element)emailAddressElmntLst.item(0);
                    NodeList emailAddresss = emailAddressElmnt.getChildNodes();
                    emailAddress = emailAddresss.item(0).getNodeValue();
                    System.out.println((new StringBuilder("emailAddress : ")).append(emailAddresss.item(0).getNodeValue()).toString());
                    NodeList countryElmntLst = Elmnt.getElementsByTagName("country");
                    Element countryElmnt = (Element)countryElmntLst.item(0);
                    NodeList countrys = countryElmnt.getChildNodes();
                    country = countrys.item(0).getNodeValue();
                    System.out.println((new StringBuilder("country : ")).append(countrys.item(0).getNodeValue()).toString());
                    NodeList contentSourceElmntLst = Elmnt.getElementsByTagName("contentSource");
                    Element contentSourceElmnt = (Element)contentSourceElmntLst.item(0);
                    NodeList contentSources = contentSourceElmnt.getChildNodes();
                    contentSource = contentSources.item(0).getNodeValue();
                    System.out.println((new StringBuilder("contentSource : ")).append(contentSources.item(0).getNodeValue()).toString());
                    NodeList representationnameElmntLst = Elmnt.getElementsByTagName("representationname");
                    Element representationnameElmnt = (Element)representationnameElmntLst.item(0);
                    NodeList representationnames = representationnameElmnt.getChildNodes();
                    representationname = representationnames.item(0).getNodeValue();
                    System.out.println((new StringBuilder("representationname : ")).append(representationnames.item(0).getNodeValue()).toString());
                    NodeList representationcontentTypeElmntLst = Elmnt.getElementsByTagName("representationcontentType");
                    Element representationcontentTypeElmnt = (Element)representationcontentTypeElmntLst.item(0);
                    NodeList representationcontentTypes = representationcontentTypeElmnt.getChildNodes();
                    representationcontentType = representationcontentTypes.item(0).getNodeValue();
                    System.out.println((new StringBuilder("representationcontentType : ")).append(representationcontentTypes.item(0).getNodeValue()).toString());
                }
            }

            i = st.executeUpdate((new StringBuilder("update avatar set planetname='")).append(planetname).append("',ringname='").append(ringname).append("',expertise='").append(expertise).append("',").append("tagnames='").append(tagnames).append("',screenName='").append(screenName).append("',websiteLink='").append(websiteLink).append("',").append("personalDescription='").append(personalDescription).append("',country='").append(country).append("',isprofileexist='").append(1).append("',contentSource='").append(contentSource).append("',").append("representationname='").append(representationname).append("',representationcontentType='").append(representationcontentType).append("' ").append("where emailAddress='").append(emailAddress).append("'").toString());
            
            str+=emailAddress;
            str+=" ";
            str+=screenName;
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return str;
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
