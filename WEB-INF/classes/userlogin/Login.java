package userlogin;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        String pass = request.getParameter("pass");
		HttpSession session = request.getSession();
		session.setAttribute("login", false);
        
        if(Validate.checkUser(username, pass))
        {
			session.setAttribute("login", true);
			session.setAttribute("user", username);
			response.sendRedirect("/fileSharing/uploadedFilesServlet");
        }
        else
        {
           out.println("Username or Password incorrect");
           RequestDispatcher rs = request.getRequestDispatcher("login.html");
           rs.include(request, response);
        }
    }  
}