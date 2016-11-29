<%-- 
    Document   : create
    Created on : Nov 19, 2016, 2:27:21 PM
    Author     : chavezfk
--%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="global.css">
        <title>CoCoEditor</title>
    </head>
    <body>
        <div id="header">
            <img src="logo.png" />
        </div>
        <div id="title">
            CoCo Editor
        </div>
        <div id="subtitle">
            Create Session
        </div>
        
        <div id="info">
            <p>
                Here are some instructions: Take the banana firmly in your hands, and peel it. Then, ingest.
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
