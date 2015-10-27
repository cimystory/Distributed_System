<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hash Value Computation</title>
    </head>
    <body>
        <h1 align = "center">Give me a string of text, and please choose an encrypt way below.</h1>
        <h3 align = "center"><form action ="ComputerHashes" method ="GET">
            <label for ="letter">Type the text.</label>
            <input type ="text" name ="plainText" value ="" /><br><br>
            <input type ="radio" name ="hashFunction" value ="MD5" />MD5
            <input type ="radio" name ="hashFunction" value ="SHA-1" />SHA-1<br><br>
            <input type="submit" value="Submit" />
            </form></h3>
    </body>
</html>

