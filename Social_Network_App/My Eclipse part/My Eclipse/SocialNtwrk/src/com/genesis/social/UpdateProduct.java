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

public class UpdateProduct extends HttpServlet {
	Connection con;
    Statement st;
    ResultSet res;
	/**
	 * Constructor of the object.
	 */
	public UpdateProduct() {
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
        String code = "6205";
        String success = "";
        System.out.println("In side Update Product");
        System.out.println(data);
        success = parse_a_string(data);
        if(success.equals("success"))
        {
            code = "product successfully stored";
            System.out.println("product successfully stored");
        } else
        {
            System.out.println("Failed to Store product");
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
        i = 0;
        try
        {		        
		        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder db = factory.newDocumentBuilder();
		        InputSource inStream = new InputSource();
		        inStream.setCharacterStream(new StringReader(xmlstring));
		        Document doc = db.parse(inStream);
		        String assetid = null;
		        String prodname = null;
		        String prodprice = null;
		        String url = null;
		        String productimgname = null;
		        String productimagesource = null;
		        String type = null;
		        String screenname = null;
		        String contentDescription = null;
		        String contentName = null;
		        String contentSource = null;
		        String contentType = null;
		        
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
		                
		                NodeList assetidElmntLst = Elmnt.getElementsByTagName("assetid");
		                Element assetidNmElmnt = (Element)assetidElmntLst.item(0);
		                NodeList assetids = assetidNmElmnt.getChildNodes();
		                assetid = assetids.item(0).getNodeValue();
		                System.out.println((new StringBuilder("assetid : ")).append(assetids.item(0).getNodeValue()).toString());
		                
		                NodeList prodnameElmntLst = Elmnt.getElementsByTagName("prodname");
		                Element prodnameElmnt = (Element)prodnameElmntLst.item(0);
		                NodeList prodnames = prodnameElmnt.getChildNodes();
		                prodname = prodnames.item(0).getNodeValue();
		                System.out.println((new StringBuilder("prodname : ")).append(prodnames.item(0).getNodeValue()).toString());
		                
		                NodeList prodpriceElmntLst = Elmnt.getElementsByTagName("prodprice");
		                Element prodpriceElmnt = (Element)prodpriceElmntLst.item(0);
		                NodeList prodprices = prodpriceElmnt.getChildNodes();
		                prodprice = prodprices.item(0).getNodeValue();
		                System.out.println((new StringBuilder("prodprice : ")).append(prodprices.item(0).getNodeValue()).toString());
		                
		                NodeList urlElmntLst = Elmnt.getElementsByTagName("url");
		                Element urlElmnt = (Element)urlElmntLst.item(0);
		                NodeList urls = urlElmnt.getChildNodes();
		                url = urls.item(0).getNodeValue();
		                System.out.println((new StringBuilder("url : ")).append(urls.item(0).getNodeValue()).toString());
		                
		                NodeList productimgnameElmntLst = Elmnt.getElementsByTagName("productimgname");
		                Element productimgnameElmnt = (Element)productimgnameElmntLst.item(0);
		                NodeList productimgnames = productimgnameElmnt.getChildNodes();
		                productimgname = productimgnames.item(0).getNodeValue();
		                System.out.println((new StringBuilder("productimgname : ")).append(productimgnames.item(0).getNodeValue()).toString());
		                
		                NodeList productimagesourceElmntLst = Elmnt.getElementsByTagName("productimagesource");
		                Element productimagesourceElmnt = (Element)productimagesourceElmntLst.item(0);
		                NodeList productimagesources = productimagesourceElmnt.getChildNodes();
		                productimagesource = productimagesources.item(0).getNodeValue();
		                System.out.println((new StringBuilder("imagesource : ")).append(productimagesources.item(0).getNodeValue()).toString());
		                
		                NodeList screennameElmntLst = Elmnt.getElementsByTagName("screenname");
		                Element screennameElmnt = (Element)screennameElmntLst.item(0);
		                NodeList screennames = screennameElmnt.getChildNodes();
		                screenname = screennames.item(0).getNodeValue();
		                System.out.println((new StringBuilder("screenname : ")).append(screennames.item(0).getNodeValue()).toString());
		                
		                NodeList contentDescriptionElmntLst = Elmnt.getElementsByTagName("contentDescription");
		                Element contentDescriptionElmnt = (Element)contentDescriptionElmntLst.item(0);
		                NodeList contentDescriptions = contentDescriptionElmnt.getChildNodes();
		                contentDescription = contentDescriptions.item(0).getNodeValue();
		                System.out.println((new StringBuilder("contentDescription : ")).append(contentDescriptions.item(0).getNodeValue()).toString());
		                
		                NodeList contentNameElmntLst = Elmnt.getElementsByTagName("contentName");
		                Element contentNameElmnt = (Element)contentNameElmntLst.item(0);
		                NodeList contentNames = contentNameElmnt.getChildNodes();
		                contentName = contentNames.item(0).getNodeValue();
		                System.out.println((new StringBuilder("contentName : ")).append(contentNames.item(0).getNodeValue()).toString());
		                
		                NodeList contentSourceElmntLst = Elmnt.getElementsByTagName("contentSource");
		                Element contentSourceElmnt = (Element)contentSourceElmntLst.item(0);
		                NodeList contentSources = contentSourceElmnt.getChildNodes();
		                contentSource = contentSources.item(0).getNodeValue();
		                System.out.println((new StringBuilder("contentSource : ")).append(contentSources.item(0).getNodeValue()).toString());
		                
		                NodeList contentTypeElmntLst = Elmnt.getElementsByTagName("contentType");
		                Element contentTypeElmnt = (Element)contentTypeElmntLst.item(0);
		                NodeList contentTypes = contentTypeElmnt.getChildNodes();
		                contentType = contentTypes.item(0).getNodeValue();
		                System.out.println((new StringBuilder("contentType : ")).append(contentTypes.item(0).getNodeValue()).toString());
		                
		                NodeList typeElmntLst = Elmnt.getElementsByTagName("type");
		                Element typeElmnt = (Element)typeElmntLst.item(0);
		                NodeList types = typeElmnt.getChildNodes();
		                type = types.item(0).getNodeValue();
		                System.out.println((new StringBuilder("type : ")).append(types.item(0).getNodeValue()).toString());
		                
		            }
		        }
		        if(url.equals("NA"))
        		{
		        	ServletContext context = getServletContext();
		        	String url1=context.getInitParameter("paypalbuttonid");
        			url="https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id="+url1;
        		}
		        else 
		        {
		        	url="https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id="+url;
		        }

		        i = st.executeUpdate("INSERT INTO Products(assetid,screenname,contentDescription, contentName, contentSource, contentType, type, status, prodname, prodprice, url, productimgname, productimagesource ) VALUES" +
	            		" ('"+assetid+"','"+screenname+"','"+contentDescription+"', '"+contentName+"','"+contentSource+"','"+contentType+"','"+type+"','Available' ,'"+prodname+"' ,'"+prodprice+"' ,'"+url+"' ,'"+productimgname+"' ,'"+productimagesource+"')");
		        
		       
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
