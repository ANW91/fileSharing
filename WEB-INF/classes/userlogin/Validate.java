package userlogin;

import java.sql.*;

public class Validate {
    public static boolean checkUser(String username,String pass)
    {
        boolean match =false;
		String userid="", securePass="", salt="";
		try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.1.213:3306/filesharing","admin","SPP000cde");
            PreparedStatement ps = con.prepareStatement("select * from register where UserID=?");
            ps.setString(1, username);
            ResultSet rs =ps.executeQuery();
            while(rs.next())
			{
				userid = rs.getString("UserID");
				securePass = rs.getString("UserPassword");
				salt = rs.getString("Salt");				
			}
			
			match = PasswordUtils.verifyUserPassword(pass, securePass, salt);
		}
		catch(Exception e){
			e.printStackTrace();
		}
        return match;                 
    }   
}