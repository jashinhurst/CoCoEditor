<%-- 
    Document   : join
    Created on : Nov 19, 2016, 2:26:30 PM
    Author     : chavezfk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="global.css">
        <title>CoCo Editor</title>
    </head>
    <body>
        <h1>Join</h1>
        <form action="redirect.jsp">
            <strong>Alias: </strong>
            <input type="text" name=<%= edu.nmt.cocoeditor.AttributeNames.ALIAS_ID.getKey() %>value="John Doe" required>
            <button type="submit">Join Session</button>
        </form>
    </body>
</html>
