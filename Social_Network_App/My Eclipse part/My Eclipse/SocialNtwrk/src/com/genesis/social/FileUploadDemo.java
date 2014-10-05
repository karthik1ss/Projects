package com.genesis.newzonia;
import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;
import java.io.*;
import java.util.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
public class FileUploadDemo extends HttpServlet
{  

public FileUploadDemo() {
    
}

/** Simply calls {@link #handleRequest(HttpServletRequest, HttpServletResponse)}.*/
public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    handleRequest(request, response);
}

/** Simply calls {@link #handleRequest(HttpServletRequest, HttpServletResponse)}.*/
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    handleRequest(request, response);
}

/** @throws RuntimeException if the user is attempting to upload a file that already exists in the repository or if the user
 *  did not enter a file name.
 *  @throws ServletException in the case of general IO problems
 *  */
public static String generateDirectoryStructure() {
	  UUID uuid = UUID.randomUUID();
	  return uuid.toString();
	 }
public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String fileName = null;
    String contentType = null;
    String fullPath = null;
    ServletFileUpload postReceiver = new ServletFileUpload();
    
    try {  // the parameter parsing logic here is a modified copy from SResEActionContextBuilder
        
        FileItemIterator iter = postReceiver.getItemIterator(request);
    
        // Rely on the fact that the file content comes after all other (form-data) parameters.
        while (iter.hasNext()) {
            FileItemStream item = iter.next();
            
            InputStream inputStream = item.openStream();
            if (item.isFormField()) {
            	
            }
            else { // we have the file upload content
//            	File f=null;
//            	FileItemHeaders fh=item.getHeaders();
//            	Iterator i=fh.getHeaderNames();
//            	if(i.hasNext()){
//            		f=(File)i.next();
//            	}
            	fileName=item.getName();
            	contentType=item.getContentType();
                if(fileName == null || "".equals(fileName)) {
                    throw new RuntimeException("missing file name");
                }
                
                // Get the local filesystem location to write to
                String path = getServletConfig().getServletContext().getRealPath("../WebDav/repository");
                //_logger.info("upload path " + path);
                //fullPath = path + "/" + fileName;
                
                String diectoryStructure = generateDirectoryStructure();
                String[] folders = diectoryStructure.split("-");
                String temppath="";
                String filePath =""; //path;
                for (String folder : folders) {
                	temppath += "/" + folder;
                }
                //_logger.info("upload temppath " + temppath);
                filePath=path+temppath;
               String fullFileName = filePath + "/" +fileName ;
                 new File(filePath).mkdirs();
                
                //_logger.info("upload fullPath " + fullPath);
                // First, check to see if the file already exists. If it does, do not allow overwrites just to be extra careful.
                // We certainly do not want to overwrite required nZ assets.
                
                File file = new File(fullFileName);          
               System.out.println(file.getAbsolutePath());
                OutputStream outputStream = new FileOutputStream(file);
                Streams.copy(inputStream, outputStream, true);
                PrintWriter out=response.getWriter();
                String tt = "..\\WebDav";
                tt=tt.concat(temppath);
                System.out.println(tt);
                String returnstring="<file><contentType>"+contentType+"</contentType><contentName>"+fileName+"</contentName><contentSource>../WebDav/repository"+temppath+"/" +fileName+"</contentSource></file>";
                out.write(returnstring);
              /* }else{
                	throw new RuntimeException("file " + fullPath + " The system cannot find the path specified");
                }*/
            }
        }
        
       
    
    } catch(FileUploadException fue) {
        throw new ServletException(fue);
    }
}
}