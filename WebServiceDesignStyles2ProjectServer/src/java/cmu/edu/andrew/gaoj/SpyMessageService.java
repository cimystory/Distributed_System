/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmu.edu.andrew.gaoj;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.w3c.dom.*;

/**
 * Web service server class
 * 
 * @author Gao
 */
@WebService(serviceName = "SpyMessageService")
public class SpyMessageService {
    
    SpyList spyList = new SpyList();

    /**
     * Web service operation
     */
    @WebMethod(operationName = "spyOperation")
    public String spyOperation(@WebParam(name = "xmlString") String xmlString) {
        
        String resultString = "";
        SpyMessage spyMessage = new SpyMessage();
        
        // get the needed field from the xml request body
        Document doc = spyMessage.getDocument(xmlString);
        doc.getDocumentElement().normalize();
        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
        
        NodeList nodeList = doc.getElementsByTagName("spyMessage");
        Node node = nodeList.item(0);
        
        Element element = (Element) node;
        
        String operation = element.getElementsByTagName("operation").item(0).getTextContent();
        
        // implement different operations according to the operation field in the xml field
        switch (operation){
            case "addSpy":
                
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                
                // if the spy doesn't exist, create a new spy and add to the list
                if(spyList.get(name) == null) {
                    
                    String title = element.getElementsByTagName("spyTitle").item(0).getTextContent();
                    String location = element.getElementsByTagName("location").item(0).getTextContent();
                    String password = element.getElementsByTagName("password").item(0).getTextContent();

                    Spy newSpy = new Spy(name, title, location, password);
                    spyList.add(newSpy);

                    resultString = "Add a new spy:\tName:" + name + "\tTitle:" + title + "\tLocation:" + location;

                } else { // if the spy exists, print error information
                    
                    resultString = "This spy has already existed";
                  
                }
                
                break;
                
            case "updateSpy":
                
                name = element.getElementsByTagName("name").item(0).getTextContent();
                
                // if the spy exists, do the update operation
                if (spyList.get(name) != null) {
                    
                    String title = element.getElementsByTagName("spyTitle").item(0).getTextContent();
                    String location = element.getElementsByTagName("location").item(0).getTextContent();
                    String password = element.getElementsByTagName("password").item(0).getTextContent();
                    
                    Spy spy = spyList.get(name);
                    spy.setTitle(title);
                    spy.setLocation(location);
                    spy.setPassword(password);

                    resultString = "Update information for spy" + name;
                    
                } else { // if the spy doesn't exist, print error information

                    resultString = "No such spy.";
            
                }  
                
                break;
                
            case "getSpyAsXML":
                
                name = element.getElementsByTagName("name").item(0).getTextContent();
                
                // if the spy exist return the xml formatted information
                if (spyList.get(name) != null) {
            
                    resultString = spyList.get(name).toXML();
            
                } else { // if the spy doesn't exit, print error information
                    
                    resultString = "No such spy.";
        
                }
                
                break;
                
            case "deleteSpy":
                
                name = element.getElementsByTagName("name").item(0).getTextContent();

                if(spyList.get(name) != null) { // if the spy exits, delete the spy
            
                    spyList.delete(new Spy(name));
                    resultString = "Spy " + name + " was deleted from the list.";

                } else { // if the spy doesn't exit, return error information

                    resultString = "The spy does not exist.";

                }
                
                break;
                
            case "getList":
                
                resultString = spyList.toString();
                break;
                
            case "getListAsXML":
                
                resultString = spyList.toXML();
                break;
           
        }
     
        return resultString;
    }


}
