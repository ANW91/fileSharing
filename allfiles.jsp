<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@page import="filesharing.UploadDetail"%>
<%@page import="userlogin.Validate"%>
<%@page language="java" session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="upload.css">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <title>Servlet File Upload/Download</title>
		<style>	
		.center {
		  margin: auto;
		  width: 50%;
		  padding: 10px;
		}
		.panel-heading {
			cursor: pointer;
		}
		</style>
         
        <link rel="stylesheet" href="main.css" />
    </head>
    <body>
	<%
		boolean login = false;
		if(session.getAttribute("login")!=null){
			login = (boolean)session.getAttribute("login");
		}
        
        if(login)
        {
	%>
	<div class="center">
		<div class="container">
			<div id="accordion">
				<div class="card">
					<a class="card-link card-header" data-toggle="collapse" href="#collapseOne" >
						Upload Files
					</a>
					<div id="collapseOne" class="collapse" data-parent="#accordion" >
						<div class="card-body" >
							<form id="fileUploadForm" method="post" action="fileUploadServlet" enctype="multipart/form-data" >
								<div class="form-group files" >
									<label>Upload Your File </label>
									<input type="file" class="form-control" multiple="multiple" name="fileUpload">
								</div>
								<button id="uploadBtn" type="submit" class="btn btn_primary">Upload</button><input type="checkbox" name="id" />Private
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="center">
        <div class="table - responsive">
            <h1>Uploaded Files</h1>
            <table class="table table-striped">
               <thead>
                  <tr align="center"><th>File Name</th><th>File Size</th><th>User</th><th>Private</th><th>Action</th></tr>
               </thead>
               <tbody>
                  <%
					Map<String, List<String>> files = new HashMap<>();
					try{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://192.168.1.213:3306/filesharing","admin","SPP000cde");
					PreparedStatement ps = con.prepareStatement("select * from files");
					ResultSet rs =ps.executeQuery();
					while(rs.next())
					{
						ArrayList<String> list = new ArrayList<>();
						list.add(rs.getString("UserID"));
						list.add(Boolean.toString(rs.getBoolean("Personal")));
						files.put(rs.getString("Filename"), list);			
					}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				  
				  
				  
				  String username = (String) session.getAttribute("user"); 
					List<UploadDetail> uploadDetails = (List<UploadDetail>)request.getAttribute("uploadedFiles");
                     if(uploadDetails != null && uploadDetails.size() > 0) {
                     for(int i=0; i<uploadDetails.size(); i++) {
						List<String> details = files.get(uploadDetails.get(i).getFileName());
						if((details.get(1)=="false")||(details.get(0).equals(username))){
                  %>
                  <tr>
                     <td align="center"><span id="fileName"><%=uploadDetails.get(i).getFileName() %></span></td>
                     <td align="center"><span id="fileSize"><%=uploadDetails.get(i).getFileSize() %> KB</span></td>
					 <td align="center"><span id="fileUsername"><%=details.get(0) %></span></td>
					 <td align="center"><span id="Private"><%=details.get(1) %></span></td>
                     <td align="center"><span id="fileDownload"><a id="downloadLink" class="hyperLink" href="<%=request.getContextPath()%>/downloadServlet?fileName=<%=uploadDetails.get(i).getFileName() %>">Download,</a>
					 <a id="viewLink" class="hyperLink" href="<%=request.getContextPath()%>/fileViewer.jsp?fileName=<%=URLEncoder.encode(uploadDetails.get(i).getFileName(), "UTF-8") %>"> View</a></span></td>
                  </tr>
						<% }}
                   } else { %>
                  <tr>
                     <td colspan="5" align="center"><span id="noFiles">No Files Uploaded.....!</span></td>
                  </tr>
                  <% } %>
               </tbody>
            </table>
         </div>
	</div>
		 <%
			}else{
			response.sendRedirect("/fileSharing/login.html");
			}
		 %>
     </body>
</html>