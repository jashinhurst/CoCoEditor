<%-- 
    Document   : join
    Created on : Nov 19, 2016, 2:26:30 PM
    Author     : chavezfk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" href="logodark.png" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="global.css">
        <title>CoCo Editor</title>
    </head>
    <body>
        <div id="header">
            <img src="logo.png" />
        </div>
        <div id="title">
            CoCo Editor
        </div>
        <div id="subtitle">
            Join Session
        </div>
        
        <div id="info">
            <p>
                To join an active session, enter the session ID and your desired
                alias below.
            </p>
            
        </div>
        <div>
            <form action="redirect.jsp" method="post">
            <center>
                <strong>Session ID: </strong><br />
                <input type="text" name="<%= edu.nmt.cocoeditor.AttributeNames.SESSION_ID.getKey() %>" required><br />
                <strong>Alias: </strong><br />
                <input type="text" name="<%= edu.nmt.cocoeditor.AttributeNames.ALIAS_ID.getKey() %>" required>
            </center>
            <br />
            <center>
                <button id="btnsubmit" type="submit">Join Session</button>
            </center>
            </form>
        </div>
    </body>
</html>
