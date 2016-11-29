<%-- 
    Document   : index
    Created on : Nov 19, 2016, 1:27:39 PM
    Author     : chavezfk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="edu.nmt.cocoeditor.AttributeNames" %>
<%@page import="edu.nmt.cocoeditor.CoCoEditor" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" href="logodark.png"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="global.css" />
        <title>CoCo Editor</title>
    </head>
    <body>
        <%  /*if(CoCoEditor.instance== null){
                CoCoEditor.instance = new CoCoEditor();
        }*/
            if(CoCoEditor.getInstance() == null){
                CoCoEditor.setInstance(new CoCoEditor());
            }
            if(CoCoEditor.getLastOut() == null){
                CoCoEditor.setLastOut(response.getWriter()); //= response.getWriter();
            }
        %>
        <div id="header">
            <a href="index.jsp" ><img src="logo.png" /></a>
        </div>
        <div id="title">
            CoCo Editor
        </div>
        <br /><br />
        <div id="info">
            <p>
                CoCo Editor is a collaborative browser-based program where you
                and your colleagues can work together on small, generally single
                file coding projects. <br>
                To host a session click "Create A Session." <br>
                To join an already running session, click "Join Your Friends."
            </p>
            
        </div>
        
        <div id="content">
            <a href="create.jsp"><div id="createbutton">Create A Session</div></a>
            <span style="width: 50px;">&nbsp;</span>
            <a href="join.jsp"><div id="joinbutton">Join Your Friends</div></a>
        </div>
        <h4>Please Note:</h4>
        <p>Currently under development: Collaborative capabilities are very
        limited at the moment, but will be working soon! :)</p>
    </body>
</html>
