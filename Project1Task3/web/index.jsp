<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Is Palindrome</title>
    </head>
    <body>
        <h1 align = "center">Please give me a String.</h1><br><br>
        <h3 align = "center">
            <form action ="Palin" method ="GET">
            <label for ="letter">String to be judged.</label>
            Input String: <input type ="text" name ="inputString" value ="" /><br><br>
            <input type="submit" value="Submit" /><br>
            </form></h3>
    </body>
</html>

