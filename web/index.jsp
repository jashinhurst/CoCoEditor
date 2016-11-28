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
        <title>CoCo Editor</title>
    </head>
    <body>
        <h1> Welcome to Collaborative Code Editor!<br>
             We are redirecting you, please wait :)
        </h1>
        <% 
            String sid = edu.nmt.cocoeditor.AttributeNames.SESSION_ID.getKey();
            session.setAttribute(sid, request.getParameter(sid));
            if(session.getAttribute(sid) != null){
                response.sendRedirect("./join.jsp");
            }else{
                //response.sendRedirect("https://weave.cs.nmt.edu/apollo.8/CoCoEditor/create.jsp");
                response.sendRedirect("./create.jsp");
            }
        %>
        <h2>
            If you are have not redirected and are seeing this, please click
            <a href="./create.jsp">here</a>
        </h2>
    </body>
</html>
