package userlogin;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Register extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
	
        String name = request.getParameter("username");
        String pass = request.getParameter("pass");
		
		String salt = PasswordUtils.getSalt(30);
		
		String securePass = PasswordUtils.generateSecurePassword(pass, salt);
        
        try {
        
            // loading drivers for mysql
            Class.forName("com.mysql.jdbc.Driver");
            
            //creating connection with the database 
            Connection con = DriverManager.getConnection
                        ("jdbc:mysql://192.168.1.213:3306/filesharing","admin","SPP000cde");

            PreparedStatement ps = con.prepareStatement
                        ("insert into register (UserID, UserPassword, Salt) values (?,?,?)");

            ps.setString(1, name);
            ps.setString(2, securePass);
			ps.setString(3, salt);
            int i = ps.executeUpdate();
            
            if(i > 0) {
                out.println("You are sucessfully registered");
				out.println("<a id=\"login\" class=\"hyperLink\" href="+request.getContextPath()+"/login.html>Go to login page</a>");
            }
        
        }
        catch(Exception se) {
            se.printStackTrace();
        }
	
    }
}