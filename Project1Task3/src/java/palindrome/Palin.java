/* 
 * @author Gao Jiang
 *
 * This is a servlet dealing with the request, get 
 * parameters and then call methods in models to do computation.
 *
 * The servlet is acting as the controller.
 * There are two views - index.jsp, result.jsp.
 *
 * If the parameter in the request is null, dispatch to the index.jsp page
 * If the parameter in the request is not null, do computation and dispatch to the result.jsp page
 *
 * The model is provided by PalinModel.
 */
package palindrome;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Palin",
        urlPatterns = {"/Palin"})
public class Palin extends HttpServlet {

    PalinModel palin = null;  // The model for this app

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        palin = new PalinModel();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // get the input string
        String inputString = request.getParameter("inputString");
        
        boolean result;
        String nextView;
        
        // determine what type of device is
        String ua = request.getHeader("User-Agent");
        
        if (inputString != null){
            // prepare the appropriate DOCTYPE for the view pages
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
            request.setAttribute("input", inputString);
            
            // use the isPalin method in PalinModel to determin whether the string is palindrome
            result = palin.isPalin(inputString);
            
            request.setAttribute("result", result);
            nextView = "result.jsp";                              
        } else {
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
            nextView = "index.jsp";
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(nextView);
        dispatcher.forward(request, response);

    }
}
