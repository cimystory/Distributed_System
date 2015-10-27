/* 
 * @author Gao Jiang
 *
 * This is a servlet dealing with the request, get 
 * parameters and then call methods in models to do computation.
 *
 * The servlet is acting as the controller.
 * There are three views - index.jsp, result.jsp and errorPage.jsp.
 *
 * If the parameter in the request is null, dispatch to the index.jsp page
 * If the parameter in the request is not null, do computation and dispatch to the result.jsp page
 * If the input parameter in the request is illegal, dispatch to the errorPage.jsp
 *
 * The model is provided by BigCalcModel.
 */
package bigIntegerCalc;

import java.io.IOException;
import java.math.BigInteger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "BigCalc",
        urlPatterns = {"/BigCalc"})
public class BigCalc extends HttpServlet {

    BigCalcModel calc = null;  // The model for this app

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        calc = new BigCalcModel();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // get the input argument of x 
        String xString = request.getParameter("x");
        // get the input argument of y
        String yString = request.getParameter("y");
        // get the input argument of operation
        String operation = request.getParameter("operations");
        
        BigInteger resultInteger = null;
        int resultInt = 0;
        
        String result;
        String nextView;
        
        // determine what type of device is
        String ua = request.getHeader("User-Agent");
        
        if (xString != null && yString != null){
            // if the inputs are illegal and cannot generate a big integer, when catching an exception, dispatch to the error page
            try{
                BigInteger x = new BigInteger(xString);
                BigInteger y = new BigInteger(yString);
                
                // match the given operation and do the appropriate computation
                switch (operation) {
                    case "add": 
                        resultInteger = calc.doAddition(x, y);
                        break;
                    case "multiply":
                        resultInteger = calc.doMultiply(x, y);
                        break;
                    case "relativelyPrime":
                        resultInt = calc.isRelativePrime(x, y);
                        break;
                    case "mod":
                        resultInteger = calc.doMod(x, y);
                        break;
                    case "modeInverse":
                        resultInteger = calc.doModInverse(x, y);
                        break;
                    case "power":
                        resultInteger = calc.doPower(x, y);
                        break;
                    default:
                        resultInteger = null;
                        break;
                }

                // prepare the appropriate DOCTYPE for the view pages
                request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
                request.setAttribute("x", xString);
                request.setAttribute("y", yString);
                request.setAttribute("operation", operation);
                
                // deal with the output, treat the result if isPrime specially
                if (resultInteger != null) {
                    if (operation.equals("relativelyPrime")) {
                        if (resultInt == 1)
                            result = "true";
                        else
                            result = "false";
                    } else{
                        result = resultInteger.toString();                                     
                    }
                    request.setAttribute("result", result);
                    nextView = "result.jsp";
                } else {
                    request.setAttribute("message", "Illegal operation. Please select the correct opereations.");
                    nextView = "errorPage.jsp";                
                }

            } catch (NumberFormatException ex) {
                request.setAttribute("message", "Illegal input. Please give correct number of x and y.");
                nextView = "errorPage.jsp";          
            }
        } else {
            nextView = "index.jsp";
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(nextView);
        dispatcher.forward(request, response);

    }
}
