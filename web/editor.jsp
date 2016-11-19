
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
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
        </script>
        <title>[session name]</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="global.css">
    </head>
    <body>
        <div id="editor">
                <ul id="navbar">
                    <li 
                        <a onclick="show_hide('file_drop')">File</a>
                        <div id="file_drop" class="dropdown">
                            <a href="#">Download</a>
                            <a href="#">Quit</a>
                        </div>
                    </li>
                    <li>
                        <a  onclick="show_hide('edit_drop')">Edit</a>
                        <div id="edit_drop" class="dropdown">
                            <a href="#">Download</a>
                            <a href="#">Quit</a>
                        </div>
                    </li>
                    <li 
                        <a onclick="show_hide('view_drop')">View</a>
                        <div id="view_drop" class="dropdown">
                            <a href="#">Download</a>
                            <a href="#">Quit</a>
                        </div>
                    </li>
                    <li>
                        <a  onclick="show_hide('help_drop')">Help</a>
                        <div id="help_drop" class="dropdown">
                            <a href="#">Download</a>
                            <a href="#">Quit</a>
                        </div>
                    </li>
                </ul>
            <form>
                <textarea rows="10" cols="100">//Sample code&#13;int main(void)&#13;{&#13;&nbsp;&nbsp;&nbsp;&nbsp;printf("hello world\n");&#13;&nbsp;&nbsp;&nbsp;&nbsp;return 0;&#13;}</textarea>
            </form>
        </div>
    </body>
</html>
