package edu.cmu.andrew.gaoj;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * Server class for the web service
 * 
 * @author Gao
 */
@WebService(serviceName = "SpyService")
public class SpyService {
    
    SpyList spyList = new SpyList();


    /**
     * Web service operation
     */
    @WebMethod(operationName = "addSpy")
    public String addSpy(@WebParam(name = "name") String name, 
                         @WebParam(name = "title") String title, 
                         @WebParam(name = "location") String location, 
                         @WebParam(name = "password") String password) {
        
        if(spyList.get(name) == null) {
            Spy newSpy = new Spy(name, title, location, password);
            spyList.add(newSpy);
            
            return "Add a new spy:\tName:" + name + "\tTitle:" + title + "\tLocation:" + location;
       
        } else {
            
            return "This spy has already existed";
            
        }
    
    }
    

    /**
     * Web service operation
     */
    @WebMethod(operationName = "updateSpy")
    public String updateSpy(@WebParam(name = "name") String name, 
                            @WebParam(name = "title") String title, 
                            @WebParam(name = "location") String location, 
                            @WebParam(name = "password") String password) {
        
        if (spyList.get(name) != null) {
            Spy spy = spyList.get(name);
            
            spy.setTitle(title);
            spy.setLocation(location);
            spy.setPassword(password);
            
            return "Update information for spy" + name;
        } else {
            
            return "No such spy.";
            
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getSpy")
    public String getSpy(@WebParam(name = "name") String name) {
        
        if (spyList.get(name) != null) {
            
            return spyList.get(name).toString();
            
        } else {
            
            return "No such spy.";
        
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "deleteSpy")
    public String deleteSpy(@WebParam(name = "name") String name) {
        
        System.out.println("The spy to be deleted is " + name);
        
        if(spyList.get(name) != null) {
            
            spyList.delete(new Spy(name));
            return "Spy " + name + " was deleted from the list.";
            
        } else {
            
            return "The spy does not exist.";
            
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getList")
    public String getList() {
        
        return spyList.toString();
        
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getListAsXML")
    public String getListAsXML() {
        
        return spyList.toXML();
        
    }
}
