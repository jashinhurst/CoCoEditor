
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
    </head>
    <body>
        <div id="editor">
            <div id="header">
                <img src="Logomakr_9FtNKi.png" alt="icon">
                <span id="session_link"> LINK: <div id="session_url">https://weave.cs.nmt.edu/apollo.8/project/editor.jsp?sid=<%= request.getParameter("sid") %></div> </span>
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
            <textarea></textarea>
        </div>
    </body>
</html>
