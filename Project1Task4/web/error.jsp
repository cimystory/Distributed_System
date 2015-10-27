<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <h1 align ="center"><%= request.getAttribute("error")%></h1>
        <h1 align ="center">Search the</h1>
        <h1 align ="center"><a href ="library.illinoisstate.edu/icca/">International Collection of Child Art</a></h1>
        <h1 align ="center">for picture for any subject.</h1>
        <form align = "center" action="searchPictures" method="GET">
            <label for="letter">Type a subject to search for:</label>
            <input type="text" name="subject" value="" /><br>
            <input type="submit" value="Click Here" />
        </form>
    </body>
</html>

