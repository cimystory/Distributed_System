<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%= request.getAttribute("doctype") %>

<html>
    <head>
        <title>Big Integer Calculation</title>
    </head>
    <body>
        <h1>Here is the result of the big integer calculations.</h1><br>
        <h1><%= request.getAttribute("x")%> <%= request.getAttribute("operation")%> <%= request.getAttribute("y")%> = <%= request.getAttribute("result")%></h1><br><br>
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



