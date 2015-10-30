package edu.cmu.andrew.gaoj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * SpyListCollection class extends HttpServlet to implement web service
 * 
 * @author Gao
 */
@WebServlet(name = "SpyListCollection", urlPatterns = {"/SpyListCollection/*"})
public class SpyListCollection extends HttpServlet {
    
    SpyList spyList = new SpyList();
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        System.out.println("Console: doGET visited");
        
        String acceptType = request.getHeader("Accept");  
        System.out.println("Accept type: " + acceptType);
        
        String output = "";        
        PrintWriter out = response.getWriter(); 
        
        if (request.getPathInfo() != null){ // if not null, get the information for a particular spy
            
            // get the parameter from the url path
            String name = (request.getPathInfo()).substring(1); 
            Spy spy = spyList.get(name); // get the information for the given spy
            
            // if the spy doesn't exit return 404
            if (spy == null) {
                response.setStatus(404);
            } else {
                
                response.setStatus(200);
                
                // generate required formatted representation of the response message according to the value of "Accept"
                if (acceptType.equals("text/xml")) {                   
                    response.setContentType("text/xml;charset=UTF-8");
                    output = spy.toXML();
                    System.out.println("Server side print out: " + output);
                }

                if (acceptType.equals("text/plain")) {
                    response.setContentType("text/plain;charset=UTF-8");
                    output = spy.toString();   
                    System.out.println("Server side print out: " + output);

                }
                
                // Construct the response body
                String[] outputList = output.split("\n");                
                for (int i = 0; i < outputList.length; i++) {
                    out.append(outputList[i] + "\r\n");
                }
                
                out.flush();
                
            }
        } else { // if the path information is null, get the spy list
            
            response.setStatus(200);
            
            // generate required formatted representation of the response message according to the value of "Accept"
            if (acceptType.equals("text/xml")) {
                response.setContentType("text/xml;charset=UTF-8");
                output = spyList.toXML();
                System.out.println("Server side print out: " + output);

            }
            
            if (acceptType.equals("text/plain")) {
                response.setContentType("text/plain;charset=UTF-8");
                output = spyList.toString();
                System.out.println("Server side print out: " + output);

            }
            
            // construct the response body
            String[] outputList = output.split("\n");
            for (int i = 0; i < outputList.length; i++) {
                out.append(outputList[i] + "\r\n");
            }

            out.flush();

        }   
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */ 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        System.out.println("Console: doPost visited");
        
        String output = "";
        PrintWriter out = response.getWriter(); 
        
        // get the input xml request body containing the need information
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String xmlString = br.readLine();
        
        // if the request body is empty, return error
        if (xmlString.equals("")) {
            response.setStatus(404);
        } else {
            
            SpyMessage spyMessage = new SpyMessage();
            
            // get the needed information from the xml request body
            Document doc = spyMessage.getDocument(xmlString);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nodeList = doc.getElementsByTagName("spy");
            Node node = nodeList.item(0);

            Element element = (Element) node;
            String name = element.getElementsByTagName("name").item(0).getTextContent();
            
            // update the given spy's information
            if (spyList.get(name) != null) {
                
                response.setStatus(200);
                String title = element.getElementsByTagName("spyTitle").item(0).getTextContent();
                String location = element.getElementsByTagName("location").item(0).getTextContent();
                String password = element.getElementsByTagName("password").item(0).getTextContent();

                Spy spy = spyList.get(name);
                spy.setTitle(title);
                spy.setLocation(location);
                spy.setPassword(password);

                output = "Update information for spy" + name;
                out.println(output);
                
            } else {
                response.setStatus(404);
            }  

        }
           
    }
    
    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */ 
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        System.out.println("Console: doDelete visited");
        
        String output = "";
        PrintWriter out = response.getWriter();
        
        // The name is on the path /name so skip over the '/'
        String name = (request.getPathInfo()).substring(1);
        
        if(name.equals("") || (spyList.get(name) == null)) {
            // no variable name return 404
            response.setStatus(404);  
        } else {
            
            // delete the given spy
            response.setStatus(200);
            spyList.delete(spyList.get(name));
            output = "Delete spy " + name;
            out.println(output);
        }
                  
    }
    
    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */ 
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         
        System.out.println("Console: doPut visited");
        
        String output = "";
        PrintWriter out = response.getWriter();
        
        // Read what the client has placed in the PUT data area
        String name = (request.getPathInfo()).substring(1);
        
        // if the name field from the path info is null or the spy is not exited, return error
        if ((!name.equals("")) && (spyList.get(name) != null)) {
            response.setStatus(405);
        } else {
            
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String xmlString = br.readLine();
            
            SpyMessage spyMessage = new SpyMessage();
            
            // get the needed information from the xml request body
            Document doc = spyMessage.getDocument(xmlString);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nodeList = doc.getElementsByTagName("spy");
            Node node = nodeList.item(0);

            Element element = (Element) node;
            
            name = element.getElementsByTagName("name").item(0).getTextContent();
            String title = element.getElementsByTagName("spyTitle").item(0).getTextContent();
            String location = element.getElementsByTagName("location").item(0).getTextContent();
            String password = element.getElementsByTagName("password").item(0).getTextContent();

            // create a new spy and add to the list
            Spy spy = new Spy(name, title, location, password);
            spyList.add(spy);
            
            response.setStatus(201);
            output = "Add new spy " + name;
            out.println(output);
        }
               
    } 
    

}
