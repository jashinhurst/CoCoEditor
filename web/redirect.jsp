<%-- 
    Document   : redirect
    Created on : Nov 28, 2016, 11:23:25 AM
    Author     : chavezfk
--%>


<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="edu.nmt.cocoeditor.AttributeNames" %>
<%@page import="edu.nmt.cocoeditor.CoCoEditor" %>
<!DOCTYPE html>
<html>
    <body>
        <%
            out.println("session id: ");
            out.println(request.getParameter("sid"));
            out.println("<br>");
            out.println("request alias by param: ");
            out.println(request.getParameter("alias"));
            out.println("<br>");
            out.println("request alias by att: ");
            out.println(request.getAttribute("alias"));
            out.println("<br>");

            String uid = AttributeNames.USER_ID.getKey();
            String sid = AttributeNames.SESSION_ID.getKey();
            String aid = AttributeNames.ALIAS_ID.getKey();
            if(request.getParameter(aid) != null){
                CoCoEditor.setErrorOutput(response.getWriter());
                out.println("create: ");
                String sessionID = null;
                sessionID = CoCoEditor.createSession();
                out.println(sessionID +"<br>");
                String alias = request.getParameter(aid);
                session.setAttribute(sid, sessionID);
                session.setAttribute(uid, CoCoEditor.submit(sessionID, alias));
                response.sendRedirect("./editor.jsp");
            }
            out.println("session id: ");
            out.println(session.getAttribute(sid));
            out.println("<br>");

        %>
    </body>
</html>
