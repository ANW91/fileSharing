<%@page import="java.net.URLDecoder"%>
<%@page import="org.apache.commons.io.FilenameUtils"%>

<!DOCTYPE html>
<html lang="en">
<head>
</head>

<body>
<%
	String filename = URLDecoder.decode(request.getParameter("fileName"), "UTF-8");
	String ext = FilenameUtils.getExtension(filename);
	if(ext.equals("mp4")||ext.equals("webm")||ext.equals("ogg")){
%>
  <video width="640" height="480" controls="controls" autoplay>
         <source src='<%="files/"+filename%>' type=<%="video/"+ext%>>
   </video> 
<%
	}else if(ext.equals("png")||ext.equals("jpg")||ext.equals("gif")){
%> 
	<img src='<%="files/"+filename%>' />  
<%
	}else if(ext.equals("mp3")||ext.equals("wav")){
%>
	<audio controls>
		<source src='<%="files/"+filename%>' type="audio/ogg">
		<source src='<%="files/"+filename%>' type="audio/mpeg">
		Your browser does not support the audio element.
	</audio>
<%
	}else if(ext.equals("txt")){
%>
	<object type="text/plain" data='<%="files/"+filename%>' />
<%
	}else if(ext.equals("pdf")){
%>
	<object data='<%="files/"+filename+"#view=FitH"%>' type="application/pdf" width="100%" height="1000px" >
	
	</object>
<%
	}else{
%>
		<a href='<%="files/"+filename%>'>Don't know this extention - download</a>\
<%
	}
%>
</body>
</html>