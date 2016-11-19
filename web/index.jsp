<%-- 
    Document   : index
    Created on : Nov 19, 2016, 1:27:39 PM
    Author     : chavezfk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1> Welcome to Collaborative Code Editor!<br>
             We are redirecting you, please wait :)
        </h1>
        <% 
            request.setAttribute("sid", request.getParameter("sid"));
            if(request.getAttribute("sid") != null){
                request.setAttribute("sid",request.getParameter("sid"));
                response.sendRedirect("./join.jsp");
            }else{
                response.sendRedirect("./create.jsp");
            }
        %>
        <h2>
            If you are have not redirected and are seeing this, please click
            <a href="./create.jsp">here</a>
        </h2>
    </body>
</html>
