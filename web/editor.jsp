
<!DOCTYPE html>
<%-- 
    Document   : editor.jsp
    Created on : Nov 19, 2016, 2:27:21 PM
    Author     : jashinhurst, Skyler
--%>
<%@page import="edu.nmt.cocoeditor.CoCoEditor"%>

<%
  if (!CoCoEditor.isValidSession(request.getSession())) {
      //invalid session. Bounce to index page
      response.sendRedirect("index.jsp");
  }  
    
%>

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
            };
            
            /////////////////////////////////////////
            //          Data Functions             //
            /////////////////////////////////////////
            
            /**
             * Adds the provided stringText to the remote database.
             * After calling the remote method, returns the database's
             * version of the string (post adding text)
             * @param {String} stringText
             * @returns nothing
             */
            function addText(stringText) {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState !== 4 || this.status !== 200)
                        return;
                    
                    refreshText();
                };
                xhttp.open("POST", "data/addText.xml");
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("text=" + stringText);
            }
            
            /**
             * Sets the cursor position for the current user
             * @param pos the position to set the cursor to
             * @returns nothing
             */
            function setPos(pos) {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {};
                xhttp.open("POST", "data/setPos.xml");
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("pos=" + pos);
            }
            
            /**
             * Deletes some number of characters, from the current user's
             * remote position. Deletion is done backwards, as if the user
             * pressed backspace {len} times.
             * Refreshes the text after deleting remotely
             * @param len
             * @returns nothing
             */
            function sendDelete(len) {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState !== 4 || this.status !== 200)
                        return;
                    refreshText();
                };
                xhttp.open("POST", "data/delete.xml");
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("count=" + len);
            }
            
            /**
             * Fetches the text from the server.
             * !Note For Josh!
             * 
             * Please change this method to set the text in the editor, rather
             * than the innerhtml of some div (unless that's how you set it).
             * This method is called by addText and delete, so it's important
             * it's correct. It might also make sense to call it as soon as
             * the user gets to this page.
             * 
             * !End Note!
             * @param targetID the div to set the innerhtml of targetID
             * @returns nothing
             */
            function refreshText(targetID) {
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
            
            /**
             * Marks this user's session as invalid. Should probably also
             * bounce back to the CoCoEditor home page or something
             * @returns nothing
             */
            function leave() {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    window.location.href = "index.jsp";
                };
                xhttp.open("POST", "data/leave.xml");
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhttp.send("");
                
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
                        <a href="#" draggable="false">Session ID..</a>
                        <a href="#" onclick="leave();" draggable="false">Quit</a>
                    </div>
                </li>
                <li>
                    <a href="javascript:void(0)" onclick="show_hide('edit_drop')" class="dropbtn" draggable="false">Edit</a>
                    <div id="edit_drop" class="dropdown">
                        <a href="#" onclick="editor.selectAll()" draggable="false">Select All</a>
                        <a href="#" onclick="editor.undo()" draggable="false">Undo</a>
                        <a href="#" onclick="editor.redo()" draggable="false">Redo</a>
                    </div>
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
                        <a href="https://weave.cs.nmt.edu/apollo.8/project/" target="_blank" draggable="false">Project Info</a>
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
                        <select onclick="this.options[this.selectedIndex].onclick()">
                            <option onclick="changeMode('bash')">Bash</option>
                            <option onclick="changeMode('csharp')">C#</option>
                            <option onclick="changeMode('c_cpp')">C++</option>
                            <option onclick="changeMode('html')">HTML</option>
                            <option onclick="changeMode('java')">Java</option>
                            <option onclick="changeMode('javascript')" selected="selected">Javascript</option>
                            <option onclick="changeMode('lisp')">Lisp</option>
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
                var row = editor.selection.getCursor().row;
                var column = editor.selection.getCursor().column;
                document.getElementById('char_count').innerHTML = count + " " + row + ":" + column;
                //refreshText('textarea');
            };
            editor.getSession().on('change', function() {
                var count = "&nbsp;&nbsp;&nbsp;&nbsp;Character Count: " + editor.getSession().getValue().length;
                var row = editor.selection.getCursor().row;
                var column = editor.selection.getCursor().column;
                document.getElementById('char_count').innerHTML = count + " " + row + ":" + column;
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
