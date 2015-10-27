<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>

<html>
    <head>
        <title>Interesting Picture</title>
    </head>
    <body>
        <h1 align = "center">Here is an interesting picture of a <%= request.getAttribute("pictureTag")%></h1><br>
        <div align = "center">           
            <img src="<%= request.getAttribute("pictureURL")%>"><br><br>
             <form action="searchPictures" method="GET">
                <label for="letter">Type a subject to search for:</label>
                <input type="text" name="subject" value="" /><br>
                <input type="submit" value="Click Here" />
            </form>
        </div>
    </body>
</html>

