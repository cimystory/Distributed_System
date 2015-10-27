<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <h1><%= request.getAttribute("message")%></h1><br>
        <h1>Please retry the input.</h1><br><br>
        <form action ="BigCalc" method ="GET">
            <label for ="letter">Please type the integer x and y, and choose the operation.</label>
            <input type ="text" name ="x" value ="" />x
            <select name="operations">
                <option value="add" selected="selected">Add</option>
                <option value="multiply">Multiply</option>
                <option value="relativelyPrime">Relatively Prime</option>
                <option value="mod">Mod</option>
                <option value="modeInverse">Mod Inverse</option>
                <option value="power">Power</option>
            </select>
            <input type ="text" name ="y" value ="" />y<br>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>

