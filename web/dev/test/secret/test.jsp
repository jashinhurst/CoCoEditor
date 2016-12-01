<%-- 
    Document   : create
    Created on : Nov 19, 2016, 2:27:21 PM
    Author     : Skyler
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
        
        <script type="text/javascript">
            function addText() {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {};
                xhttp.open("POST", "data/addText.xml");
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("text=add123");
            }
        </script>
    </head>
    <body>
        <div id="header">
            <a href="index.jsp" ><img src="logo.png" /></a>
        </div>
        <div id="title">
            CoCo Editor
        </div>
        <div id="subtitle">
            Blow Up World
        </div>
        
        <div id="info">
            <p>
                This page is used to blow up the world.
            </p>
            
        </div>
        <div>
            <button name="addText" onclick="addText()">Add Text</button>
        </div>
    </body>
</html>
