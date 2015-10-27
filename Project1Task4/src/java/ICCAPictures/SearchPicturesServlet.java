/* 
  * @author Gao Jiang
 *
 * This is a servlet dealing with the request, get 
 * parameters and then call methods in models to do computation.
 *
 * The servlet is acting as the controller in a MVC model.
 * There are two views - index.jsp, result.jsp.
 *
 * If the parameter in the request is null, dispatch to the index.jsp page
 * If the parameter in the request is not null, do computation and dispatch to the result.jsp page
 *
 * The model is provided by SearchPicturesModel.
 */
package ICCAPictures;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SearchPicturesServlet",
        urlPatterns = {"/searchPictures"})
public class SearchPicturesServlet extends HttpServlet {

    SearchPicturesModel spm = null;  // The model for this app

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        spm = new SearchPicturesModel();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // get the subject parameter if it exists
        String subject = request.getParameter("subject");

        // determine what type of device is
        String ua = request.getHeader("User-Agent");

        String deviceType;
        String imageUrl;
        
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.contains("Android")) || (ua.contains("iPhone")))) {
            deviceType = "mobile";
            // This is the latest XHTML Mobile doctype.
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            deviceType = "PC";
            // This is the XHTM Webpage doctype.
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        String nextView;
        /*
         * Check if the subject parameter is present.
         * If not, then give the user instructions and prompt for a subject string.
         * If there is a subject parameter, then do the subject and return the image sourde url.
         */
        if (subject != null) {
            // use model to get the image url and choose the result view
            imageUrl = spm.searchPictureUrl(subject, deviceType);
          
            if (imageUrl != null) {
                request.setAttribute("pictureURL", imageUrl);
                // Pass the user subject string (pictureTag) also to the view.
                request.setAttribute("pictureTag", spm.getPictureTag());
                nextView = "result.jsp";
            } else {
                nextView = "error.jsp";
                request.setAttribute("error", "Cannot found image for the given subject. Please try again.");
            }
        } else {
            if (ua != null && ((ua.contains("Android")) || (ua.contains("iPhone")))) {
                request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
            } else {
                request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
            }
            nextView = "welcome.jsp";
        }
        // Transfer control over the the correct view
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
}
