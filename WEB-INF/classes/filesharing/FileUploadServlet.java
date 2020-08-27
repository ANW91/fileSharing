package filesharing;
 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
 
@WebServlet(description = "Upload File To The Server", urlPatterns = { "/fileUploadServlet" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024 * 50)
public class FileUploadServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
    public static final String UPLOAD_DIR = "uploadedFiles";
 
    /***** This Method Is Called By The Servlet Container To Process A 'POST' Request *****/
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }
 
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        /***** Get The Absolute Path Of The Web Application *****/
        String uploadPath = "E:/uploaded";
 
        File fileUploadDirectory = new File(uploadPath);
        if (!fileUploadDirectory.exists()) {
            fileUploadDirectory.mkdirs();
        }
 
        String fileName = "";
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("user");
		boolean personal=false; 
		if(session.getAttribute("personal")!=null) personal = true;
        UploadDetail details = null;
        List<UploadDetail> fileList = new ArrayList<UploadDetail>();
		
 
        for (Part part : request.getParts()) {
            fileName = extractFileName(part);
            details = new UploadDetail();
            details.setFileName(fileName);
            details.setFileSize(part.getSize() / 1024);
            try {
                part.write(uploadPath + File.separator + fileName);
                details.setUploadStatus("Success");
            } catch (IOException ioObj) {
                details.setUploadStatus("Failure : "+ ioObj.getMessage());
            }
            fileList.add(details);
        }
		for(int i=0; i<fileList.size(); i++)
		{
			try {
			
				Class.forName("com.mysql.jdbc.Driver");
				
				Connection con = DriverManager.getConnection
							("jdbc:mysql://192.168.1.213:3306/filesharing","admin","SPP000cde");

				PreparedStatement ps = con.prepareStatement
							("insert into files (UserID, Filename, Personal) values (?,?,?)");

				ps.setString(1, username);
				ps.setString(2, fileList.get(i).getFileName());
				ps.setBoolean(3, personal);
				int k = ps.executeUpdate();
			
			}
			catch(Exception se) {
				se.printStackTrace();
			}
		}
 
        request.setAttribute("uploadedFiles", fileList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/fileuploadResponse.jsp");
        dispatcher.forward(request, response);
    }
 
    /***** Helper Method #1 - This Method Is Used To Read The File Names *****/
    private String extractFileName(Part part) {
        String fileName = "", 
                contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return fileName;
    }
}