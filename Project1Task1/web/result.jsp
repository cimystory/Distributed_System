<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>

<html>
    <head>
        <title>Hash Value Computation</title>
    </head>
    <body>
        <h1>Here is the hash value of text <%= request.getAttribute("hashName")%>:</h1><br>
        <h1><%= request.getAttribute("hashFunction")%>(Hex): <%= request.getAttribute("hashValueHex")%></h1><br>
        <h1><%= request.getAttribute("hashFunction")%>(Base 64): <%= request.getAttribute("hashValueBase64")%></h1><br><br>
         <form action="ComputerHashes" method="GET">
            <label for ="letter">Type another text.</label>
            <input type ="text" name ="plainText" value ="" /><br>
            <input type ="radio" name ="hashFunction" value ="MD5" />MD5
            <input type ="radio" name ="hashFunction" value ="SHA-1" />SHA-1<br>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>