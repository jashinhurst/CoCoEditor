<%-- 
    Document   : create
    Created on : Nov 19, 2016, 2:27:21 PM
    Author     : chavezfk
--%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="edu.nmt.cocoeditor.CoCoEditor"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="global.css">
        <title>CoCoEditor</title>
    </head>
    <body>
        <h1>Create</h1>
        <form method="get">
            <strong>Alias: </strong>
            <input type="text" name="alias" required>
            <button id="btnsubmit"type="submit">Create Session</button>
        </form>
        <%
            if(request.getParameter("alias") !=null){
                    CoCoEditor setup = new CoCoEditor();
                    String sessionID = setup.createSession();
                    String alias = request.getParameter("alias");
                    request.setAttribute("sid", sessionID);
                    request.setAttribute("uid", setup.submit(sessionID, alias));
                    response.sendRedirect("./editor.jsp");
                }
        %>
    </body>
    
</html>
