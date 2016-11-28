<%-- 
    Document   : redirect
    Created on : Nov 28, 2016, 11:23:25 AM
    Author     : chavezfk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="edu.nmt.cocoeditor.AttributeNames" %>
<%@page import="edu.nmt.cocoeditor.CoCoEditor" %>
<!DOCTYPE html>
<html>
    <body>
        <%
            out.println("session id: ");
            out.println(session.getAttribute("sid"));
            out.println("<br>");

            String uid = AttributeNames.USER_ID.getKey();
            String sid = AttributeNames.SESSION_ID.getKey();
            String aid = AttributeNames.ALIAS_ID.getKey();
            if(request.getParameter(aid) != null){
                out.println("here <br>");
                String sessionID = CoCoEditor.instance.createSession();
                String alias = request.getParameter(aid);
                session.setAttribute(sid, sessionID);
                session.setAttribute(uid, CoCoEditor.instance.submit(sessionID, alias));
                response.sendRedirect("./editor.jsp");
            }
            out.println("session id: ");
            out.println(session.getAttribute("sid"));
            out.println("<br>");

        %>
    </body>
</html>
