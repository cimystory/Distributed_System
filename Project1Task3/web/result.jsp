<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>

<html>
    <head>
        <title>Palindrome</title>
    </head>
    <body>
        <h1 align = "center">Determine whether the given String is palindrome.</h1><br>
        <h1 align = "center">Is <%= request.getAttribute("input")%> a palindrome?</h1>
        <h1 align = "center"><%= request.getAttribute("result")%></h1><br><br>
        <form action ="Palin" method ="GET">
            <label for ="letter">String to be judged.</label>
            Input String: <input type ="text" name ="inputString" value ="" />
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>



