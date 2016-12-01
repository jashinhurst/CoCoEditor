<%-- 
    Document   : create
    Created on : Nov 19, 2016, 2:27:21 PM
    Author     : chavezfk
--%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" href="logodark.png" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="global.css">
        <title>CoCoEditor</title>
    </head>
    <body>
        <div id="header">
            <a href="index.jsp" ><img src="logo.png" /></a>
        </div>
        <div id="title">
            CoCo Editor
        </div>
        <div id="subtitle">
            Create Session
        </div>
        
        <div id="info">
            <p>
                To create a new session, enter your desired alias below.<br />
                If you have a session ID, then you can <a href="join.jsp"> join
                it here</a>.
            </p>
            
        </div>
        <div>
            <form action="redirect.jsp" method="post">
            <center>
                <strong>Alias: </strong><br />
                <input type="text" name="<%= edu.nmt.cocoeditor.AttributeNames.ALIAS_ID.getKey() %>" required>
            </center>
            <br />
            <center>
                <button id="btnsubmit" type="submit">Create Session</button>
            </center>
            </form>
        </div>
    </body>
</html>
