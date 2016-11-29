<%-- 
    Document   : index
    Created on : Nov 19, 2016, 1:27:39 PM
    Author     : chavezfk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="edu.nmt.cocoeditor.AttributeNames" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" href="logodark.png" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="global.css" />
        <title>CoCo Editor</title>
    </head>
    <body>
        <div id="header">
            <img src="logo.png" />
        </div>
        <div id="title">
            CoCo Editor
        </div>
        <br /><br />
        <div id="info">
            <p>
                CoCo Editor is a collaborative browser-based program that
                will allow you to work with your colleagues on small pieces of code
            </p>
            
        </div>
        
        <div id="content">
            <a href="create.jsp"><div id="createbutton">Create A Session</div></a>
            <span style="width: 50px;">&nbsp;</span>
            <a href="join.jsp"><div id="joinbutton">Join Your Friends</div></a>
        </div>
        
        <%-- 
            String sid = edu.nmt.cocoeditor.AttributeNames.SESSION_ID.getKey();
            session.setAttribute(sid, request.getAttribute(sid));
            out.println("request att id: ");
            out.println(request.getAttribute(sid));
            out.println("<br>");
            out.println("request param id: ");
            out.println(request.getParameter(sid));
            out.println("<br>");
            if(session.getAttribute(sid) != null){
                response.sendRedirect("./join.jsp");
                //response.sendRedirect("https://weave.cs.nmt.edu/apollo.8/CoCoEditor/join.jsp");
            }else{
                //response.sendRedirect("https://weave.cs.nmt.edu/apollo.8/CoCoEditor/create.jsp");
                response.sendRedirect("./create.jsp");
            }
        --%>
    </body>
</html>
