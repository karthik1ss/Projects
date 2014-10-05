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

public class PostArticle extends HttpServlet
{

    Connection con;
    Statement st;
    ResultSet res;

    public PostArticle()
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
        String data = request.getParameter("data");
        System.out.println("Inside Post Article");
        String success = "";
        System.out.println(data);
        success = parse_a_string(data);
        if(success == "success")
        {
            out.write("success");
            System.out.println("Article successfully stored");
        } else
        {
        	out.write("Failed to Register");
            System.out.println("Failed to Store Article");
        }
        out.flush();
        out.close();
    }

    private String parse_a_string(String xmlstring)
    {
        int i;
        i = 0;
        try
        {
		        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder db = factory.newDocumentBuilder();
		        InputSource inStream = new InputSource();
		        inStream.setCharacterStream(new StringReader(xmlstring));
		        Document doc = db.parse(inStream);
		        String Articlename = null;
		        String Article = null;
		        String allowcomments = null;
		        String ScreenName = null;
		        String posttime = null;
		        st = con.createStatement();
		        doc.getDocumentElement().normalize();
		        System.out.println((new StringBuilder("Root element ")).append(doc.getDocumentElement().getNodeName()).toString());
		        NodeList nodeLst = doc.getElementsByTagName("data");
		        System.out.println("Information of entire data");
		        int s;
		        for(s = 0; s < nodeLst.getLength(); s++)
		        {
		            Node fstNode = nodeLst.item(s);
		            if(fstNode.getNodeType() == 1)
		            {
		                Element Elmnt = (Element)fstNode;
		                NodeList ElmntLst = Elmnt.getElementsByTagName("Articlename");
		                Element ArticlenameNmElmnt = (Element)ElmntLst.item(0);
		                NodeList Articlenames = ArticlenameNmElmnt.getChildNodes();
		                Articlename = Articlenames.item(0).getNodeValue();
		                System.out.println((new StringBuilder("Articlename : ")).append(Articlenames.item(0).getNodeValue()).toString());
		                NodeList ArticleElmntLst = Elmnt.getElementsByTagName("Article");
		                Element ArticleElmnt = (Element)ArticleElmntLst.item(0);
		                NodeList Articles = ArticleElmnt.getChildNodes();
		                Article = Articles.item(0).getNodeValue();
		                System.out.println((new StringBuilder("Article : ")).append(Articles.item(0).getNodeValue()).toString());
		                NodeList allowcommentsElmntLst = Elmnt.getElementsByTagName("allowcomments");
		                Element allowcommentsElmnt = (Element)allowcommentsElmntLst.item(0);
		                NodeList allowcommentss = allowcommentsElmnt.getChildNodes();
		                allowcomments = allowcommentss.item(0).getNodeValue();
		                System.out.println((new StringBuilder("allowcomments : ")).append(allowcommentss.item(0).getNodeValue()).toString());
		                NodeList ScreenNameElmntLst = Elmnt.getElementsByTagName("ScreenName");
		                Element ScreenNameElmnt = (Element)ScreenNameElmntLst.item(0);
		                NodeList ScreenNames = ScreenNameElmnt.getChildNodes();
		                ScreenName = ScreenNames.item(0).getNodeValue();
		                System.out.println((new StringBuilder("ScreenName : ")).append(ScreenNames.item(0).getNodeValue()).toString());
		                NodeList posttimeElmntLst = Elmnt.getElementsByTagName("posttime");
		                Element posttimeElmnt = (Element)posttimeElmntLst.item(0);
		                NodeList posttimes = posttimeElmnt.getChildNodes();
		                posttime = posttimes.item(0).getNodeValue();
		                System.out.println((new StringBuilder("posttime : ")).append(posttimes.item(0).getNodeValue()).toString());
		            }
		        }
		
		        s = Integer.parseInt(allowcomments);
		        boolean b;
		        if(s == 1)
		        {
		            b = true;
		        } else
		        {
		            b = false;
		        }
		        st = con.createStatement();
		        i = st.executeUpdate((new StringBuilder("INSERT INTO articles(screenName, ArticleName,Article,commentsallowed,posttime) V" +
		"ALUES ('"
		)).append(ScreenName).append("', '").append(Articlename).append("','").append(Article).append("',").append(b).append(",'").append(posttime).append("')").toString());
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
        return "fail";
    }
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
