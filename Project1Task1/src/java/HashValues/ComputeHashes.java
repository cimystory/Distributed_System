/* 
 * @author Gao Jiang
 *
 * This is a servlet dealing with the request, get 
 * parameters and then call methods in models to do computation.
 *
 * The servlet is acting as the controller.
 * There are two views - index.jsp and result.jsp.
 *
 * If the parameter in the request is null, dispatch to the index.jsp page
 * If the parameter in the request is not null, do computation and dispatch to the result.jsp page
 *
 * The model is provided by ComputerHashesModel.
 */
package HashValues;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * The following WebServlet annotation gives instructions to the web container.
 * It states that when the user browses to the URL path /ComputerHashes
 * then the servlet with the name ComputeHashes should be used.
 */
@WebServlet(name = "ComputeHashes",
        urlPatterns = {"/ComputerHashes"})
public class ComputeHashes extends HttpServlet {

    ComputerHashesModel hashes = null;  // The model for this app

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        hashes = new ComputerHashesModel();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // get the plaintext to calculate the hash values
        String plainText = request.getParameter("plainText");
        
        // get the type of calculating hash values
        String hashType = request.getParameter("hashFunction");

        // determine what type of device is
        String ua = request.getHeader("User-Agent");
        
        String resultHex;
        String resultBase64;
        String nextView;
        
        if (plainText != null) {
            if (hashType.equals("SHA-1")) {
                // Use the doSHA method in model to compute the hash value using SHA algorithm
                byte[] hashSHA1 = hashes.doSHA(plainText);
                // Encode the hashvalue in hexadecimal
                resultHex = hashes.getHexString(hashSHA1);
                // Ecode the hashvalue in base64
                resultBase64 = hashes.getBase64(hashSHA1);
            } else {
                // Use the doMD5 method in model to compute the hash value using MD5 algorith
                byte[] hashMD5 = hashes.doMD5(plainText);
                resultHex = hashes.getHexString(hashMD5);
                resultBase64 = hashes.getBase64(hashMD5);
            }

            // prepare the appropriate DOCTYPE for the view pages
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
            request.setAttribute("hashName", plainText);
            request.setAttribute("hashFunction", hashType);
            request.setAttribute("hashValueHex", resultHex);
            request.setAttribute("hashValueBase64", resultBase64);
          
            nextView = "result.jsp";        
        } else {
            // no plain text to be encrypted from index.jsp
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
            nextView = "index.jsp";
        }
        // Transfer control over the the correct view
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
}
