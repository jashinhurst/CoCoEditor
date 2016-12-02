
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
        <title>CoCo Editor</title>
        <link rel="icon" href="logodark.png" />
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="global.css">
        <%@page import="edu.nmt.cocoeditor.AttributeNames" %>
        <% String sid = AttributeNames.SESSION_ID.getKey(); %>
    </head>
    <body>
        <div id="editor">
            <div id="header">
                <a href="index.jsp" ><img src="logo.png" /></a>
                <span id="session_link"> Session ID: <div id="session_url"><%= session.getAttribute(sid) %></div> </span>
            </div>
            <ul id="navbar">
                <li>
                    <a href="javascript:void(0)" onclick="show_hide('file_drop')" class="dropbtn" draggable="false">File</a>
                    <div id="file_drop" class="dropdown">
                        <a href="#" draggable="false">Download</a>
                        <a href="#" onclick="showSession()">Session ID..</a>
                        <a href="javascript:window.close()" draggable="false">Quit</a>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0)" onclick="show_hide('edit_drop')" class="dropbtn" draggable="false">Edit</a>
                    <div id="edit_drop" class="dropdown">
                        <a href="#" onclick="editor.selectAll()" draggable="false">Select All</a>
                        <a href="#" onclick="editor.undo()" draggable="false">Undo</a>
                        <a href="#" onclick="editor.redo()" draggable="false">Redo</a>
                </li>
                <li>
                    <a href="javascript:void(0)" onclick="show_hide('view_drop')" class="dropbtn" draggable="false">View</a>
                    <div id="view_drop" class="dropdown">
                        <a href="#" draggable="false">Download</a>
                        <a href="#" draggable="false">Quit</a>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0)" onclick="show_hide('help_drop')" class="dropbtn" draggable="false">Help</a>
                    <div id="help_drop" class="dropdown">
                        <a href="https://weave.cs.nmt.edu/apollo.8/project/" draggable="false">Project Info</a>
                    </div>
                </li>
            </ul>
        <div style="height: 100%;" id="textarea">function foo(items) {
            var x = "All this is syntax highlighted";
            return x;
        }</div>
            
            <table id="textarea_footer" class="footer_info">
                <tr>
                    <td>
                        <select>
                            <option onclick="changeMode('javascript')">Javascript</option>
                            <option onclick="changeMode('csharp')">C#</option>
                        </select>
                    </td>
                    <td>
                        <div id="char_count" class="footer_info"> &nbsp;&nbsp;&nbsp;Character Count</div>
                    </td>
                </tr>
                
            </table>
            
        </div>
        
    
        <script src="./ace-builds-1.2.5/src-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
        <script>
            var editor = ace.edit("textarea");
            editor.setTheme("ace/theme/monokai");
            editor.getSession().setMode("ace/mode/javascript");
            function changeMode(mode) {
                mode = "ace/mode/" + mode;
                editor.getSession().setMode(mode)
            }
            function showSession() {
                var text_sid = session.getAttribute(sid);
                window.alert(text_sid);
            }
        </script>
    </body>
</html>
