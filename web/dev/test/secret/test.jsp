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
            
            function addText(stringText) {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {};
                xhttp.open("POST", "data/addText.xml");
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("text=" + stringText);
            }
            
            function setPos(pos) {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {};
                xhttp.open("POST", "data/setPos.xml");
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("pos=" + pos);
            }
            
            function refreshText(targetID = "content") {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState !== 4 || this.status !== 200)
                        return;
                    
                    var element = document.getElementById(targetID);
                    if (typeof element === 'undefined') {
                        //do nothing. 
                        return;
                    }
                    
                    element.innerHTML = this.responseText;
                };
                xhttp.open("POST", "data/getText.xml");
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("");
            }
            
            
            // Helper methods. Ignore these
            
            function doText() {
                var input = document.getElementById("textfield");
                var text = input.value;
                addText(text);
            }
            
            function doPos() {
                var input = document.getElementById("posfield");
                var pos = input.value;
                setPos(pos);
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
            <p id="content" style="border: black solid 1px; background-color: #FFD">
                This page is used to blow up the world.
            </p>
            
        </div>
        <div>
            <input type="text" value="Enter Text" name="intext" id="textfield" />
            <button name="addText" onclick="doText()">Add Text</button><br />
            <input type="text" value="Enter new POS" name="inpos" id="posfield" />
            <button name="addText" onclick="doPos()">Set Pos</button><br />
            <br />
            <button name="addText" onclick="refreshText('content')">Refresh</button><br />
        </div>
    </body>
</html>
