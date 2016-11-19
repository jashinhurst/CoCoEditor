
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <script>
            function show_hide() {
                var item = document.getElementById("stat_view");
                if (item.style.display !== 'block') {
                    item.style.display = 'block';
                }
                else {
                    item.style.display = 'none';
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
                <li>File</li>
                <li>Edit</li>
                <li onclick="show_hide()">View</li>
                <li>Help</li>
            </ul>
            <form>
                <textarea rows="10" cols="100">//Sample code&#13;int main(void)&#13;{&#13;&nbsp;&nbsp;&nbsp;&nbsp;printf("hello world\n");&#13;&nbsp;&nbsp;&nbsp;&nbsp;return 0;&#13;}</textarea>
            </form>
        </div>
    </body>
</html>
