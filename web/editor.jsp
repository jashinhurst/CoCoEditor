
<!DOCTYPE html>
<%-- 
    Document   : editor.jsp
    Created on : Nov 19, 2016, 2:27:21 PM
    Author     : jashinhurst
--%>
<%@page import="edu.nmt.cocoeditor.CoCoEditor"%>
<html>
    <head>
        <script>
            function show_hide(div_id) {
                var dropdowns = document.getElementsByClassName('dropdown');
                
                for (var i = 0; i < dropdowns.length; i++) {
                    if (dropdowns[i].getAttribute("id") === div_id && dropdowns[i].style.display !== 'block') {
                        dropdowns[i].style.display = 'block';
                    } else {
                        dropdowns[i].style.display = 'none';
                    }
                }
            }
            window.onclick = function(e) {
                if (e.target.className !== "dropbtn") {
                    var dropdowns = document.getElementsByClassName('dropdown');
                
                    for (var i = 0; i < dropdowns.length; i++) {
                        dropdowns[i].style.display = 'none';
                    }
                }
            }
        </script>
        <title>[session name]</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="global.css">
        <%@page import="edu.nmt.cocoeditor.AttributeNames" %>
        <% String sid = AttributeNames.SESSION_ID.getKey(); %>
    </head>
    <body>
        <div id="editor" style="height: 82vh">
            <div id="header">
                <img src="Logomakr_9FtNKi.png" alt="icon">
                <span id="session_link"> Session ID: <div id="session_url"><%= session.getAttribute(sid) %></div> </span>
            </div>
            <ul id="navbar">
                <li>
                    <a href="javascript:void(0)" onclick="show_hide('file_drop')" class="dropbtn">File</a>
                    <div id="file_drop" class="dropdown">
                        <a href="#">Download</a>
                        <a href="#">Quit</a>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0)" onclick="show_hide('edit_drop')" class="dropbtn">Edit</a>
                    <div id="edit_drop" class="dropdown">
                        <a href="#">Download</a>
                        <a href="#">Quit</a>
                </li>
                <li>
                    <a href="javascript:void(0)" onclick="show_hide('view_drop')" class="dropbtn">View</a>
                    <div id="view_drop" class="dropdown">
                        <a href="#">Download</a>
                        <a href="#">Quit</a>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0)" onclick="show_hide('help_drop')" class="dropbtn">Help</a>
                    <div id="help_drop" class="dropdown">
                        <a href="#">Download</a>
                        <a href="#">Quit</a>
                    </div>
                </li>
            </ul>
        <div style="height: 100%;" id="textarea">function foo(items) {
            var x = "All this is syntax highlighted";
            return x;
        }</div>
            
        </div>
        
    
        <script src="./ace-builds-1.2.5/src-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
        <script>
            var editor = ace.edit("textarea");
            editor.setTheme("ace/theme/monokai");
            editor.getSession().setMode("ace/mode/javascript");
        </script>
    </body>
</html>
