
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
                        <a href="javascript:void(0)" onclick="switchTheme()" draggable="false">Dark/Light</a>
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
                            <option onclick="changeMode('csharp')">C#</option>
                            <option onclick="changeMode('c_cpp')">C++</option>
                            <option onclick="changeMode('html')">HTML</option>
                            <option onclick="changeMode('javascript')">Javascript</option>
                            <option onclick="changeMode('lua')">Lua</option>
                            <option onclick="changeMode('perl')">Perl</option>
                            <option onclick="changeMode('plain_text')">Plain Text</option>
                            <option onclick="changeMode('python')">Python</option>
                            <option onclick="changeMode('sql')">SQL</option>
                        </select>
                    </td>
                    <td>
                        <p id="char_count" class="footer_info"></p>
                    </td>
                </tr>
                
            </table>
            
        </div>
        
    
        <script src="./ace-builds-1.2.5/src-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
        <script>
            var editor = ace.edit("textarea");
            var theme_counter = 0;
            editor.setTheme("ace/theme/monokai");
            editor.getSession().setMode("ace/mode/javascript");
            function changeMode(mode) {
                mode = "ace/mode/" + mode;
                editor.getSession().setMode(mode);
            }
            window.onload = function() {
                var count = "&nbsp;&nbsp;&nbsp;&nbsp;Character Count: " + editor.getSession().getValue().length;
                document.getElementById('char_count').innerHTML = count;
            };
            editor.getSession().on('change', function() {
                var count = "&nbsp;&nbsp;&nbsp;&nbsp;Character Count: " + editor.getSession().getValue().length;
                document.getElementById('char_count').innerHTML = count;
            });
            function switchTheme() {
                if (theme_counter % 2 === 0) {
                    editor.setTheme("ace/theme/textmate");
                } else {
                    editor.setTheme("ace/theme/monokai");
                }
                theme_counter++;
            }
        </script>
    </body>
</html>
