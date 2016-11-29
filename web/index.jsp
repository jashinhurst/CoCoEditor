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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="global.css" />
        <title>CoCo Editor</title>
    </head>
    <body>
        <div id="header">
            <img src="Logomakr_9FtNKi.png" />
        </div>
        <div id="title">
            CoCo Editor
        </div>
        
        <div id="info">
            <p>
                CoCo Editor is a collaborative browser-based program that
                will allow you to work with your colleagues on small pieces of code
            </p>
            
        </div>
        
        <div id="content">
            
        </div>
        
        <span>
            
        </span>
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
        <h2>
            If you are have not redirected and are seeing this, please click
            <a href="./create.jsp">here</a>
        </h2>
    </body>
</html>
