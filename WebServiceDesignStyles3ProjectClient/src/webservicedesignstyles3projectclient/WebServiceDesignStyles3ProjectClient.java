/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservicedesignstyles3projectclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;

/**
 * Client class of the web service
 * @author Gao
 */
public class WebServiceDesignStyles3ProjectClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("Begin main");
           
        // Create new spies for adding into the list
        Spy spy1 = new Spy("mikem","spy", "Pittsburgh","sesame");
        Spy spy2 = new Spy("joem","spy", "Philadelphia","obama");
        Spy spy3 = new Spy("seanb","spy commander", "Adelaide","pirates");
        Spy spy4 = new Spy("jamesb","007", "Boston","queen");

        System.out.println(doPut(spy1)); // 201, if existed, 405
        System.out.println(doPut(spy2)); // 201
        System.out.println(doPut(spy3)); // 201
        System.out.println(doPut(spy4)); // 201

        System.out.println(doDelete("joem")); // 200
        spy1.setPassword("Doris");
        System.out.println(doPost(spy1)); // 200
        
        System.out.println(doGetListAsXML()); // display xml
        System.out.println(doGetListAsText()); // display text

        System.out.println(doGetSpyAsXML("mikem")); // display xml
        System.out.println(doGetSpyAsText("joem")); // 404

        System.out.println(doGetSpyAsXML("mikem")); // display xml
        System.out.println(doPut(spy2)); // 201
        System.out.println(doGetSpyAsText("joem")); // display text

        System.out.println(doGetListAsXML());
        System.out.println("End main");
        
    }
    
    /**
     * Update an existing spy with the representation in the body of the request
     * 
     * @param spy the spy needs to be updated
     * @return 200 if updated successfully; 404 if the name of the spy doesn't exist
     */
    public static String doPost(Spy spy) {
          
        int status; // the response status
        String response = "";
        
        try {  
            // Make call to a particular URL
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set request method to POST and send name value pair
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            
            // write to POST data area
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            SpyMessage spyMessage = new SpyMessage(spy);
            out.write(spyMessage.toXML());
            out.close();
            
            // get HTTP response code sent by server
            status = conn.getResponseCode();
            
            // If things went poorly, get the error status and message
            if (status != 200) {
                // the response message
                String msg = conn.getResponseMessage();
                return conn.getResponseCode() + " " + msg;
            }
            
            // read the response massage 
            String output = "";
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            while ((output = br.readLine()) != null) {
                response += output + "\n";
            }
         
            //close the connection
            conn.disconnect();
            
        } 
        // handle exceptions
        catch (MalformedURLException e) {
            e.printStackTrace();        
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        // return HTTP status
        return response;
    }
    
    /**
     * Get the spy list and present as XML format
     * 
     * @return 200 and the XML formatted string of the list of spies
     */
    public static String doGetListAsXML() {
        String response = "";
        HttpURLConnection conn;
        int status;

        try {  
            // pass the name on the URL line
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            // tell the server what format we want back
            conn.setRequestProperty("Accept", "text/xml");

            // wait for response
            status = conn.getResponseCode();

            // If things went poorly, get the error status and message
            if (status != 200) {
                
                String msg = conn.getResponseMessage();
                return conn.getResponseCode() + " " + msg;
            }
            
            // read the response massage 
            String output = "";
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            while ((output = br.readLine()) != null) {
                response += output + "\n";
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            
        return response;
    }
    
    /**
     * Get the spy list and present as plain text format
     * 
     * @return 200 and the plain text formatted string of the list of spies 
     */
    public static String doGetListAsText() {
        String response = "";
        HttpURLConnection conn;
        int status;

        try {  
            // pass the name on the URL line
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // tell the server what format we want back
            conn.setRequestProperty("Accept", "text/plain");

            // wait for response
            status = conn.getResponseCode();

            // If things went poorly, get the error status and message
            if (status != 200) {
                // not using msg
                String msg = conn.getResponseMessage();
                return conn.getResponseCode() + " " + msg;
            }
            
            // get the response message
            String output = "";
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            while ((output = br.readLine()) != null) {
                response += output + "\n";
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            
        return response;
    }
    
    /**
     * Get the information of a given spy in a plain text format
     * 
     * @param name the name of the spy
     * @return 200 and a plain text formatted string of the information of the spy
     *         404 if the spy not found
     */
    public static String doGetSpyAsText(String name) {
        String response = "";
        HttpURLConnection conn;
        int status;

        try {  
            // pass the name on the URL line
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection" + "//" + name);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // tell the server what format we want back
            conn.setRequestProperty("Accept", "text/plain");

            // wait for response
            status = conn.getResponseCode();

            // If things went poorly, get the error status and message            
            if (status != 200) {
                // not using msg
                String msg = conn.getResponseMessage();
                return conn.getResponseCode() + " " + msg;
            }
            
            // get the response
            String output = "";
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            while ((output = br.readLine()) != null) {
                response += output + "\n";
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            
        return response;
    }
    
    /**
     * Get the information of a given spy in a plain text format
     * 
     * @param name the name of the spy
     * @return 200 and a XML formatted string of the information of the spy
     *         404 if the spy not found
     */
    public static String doGetSpyAsXML(String name) {
        String response = "";
        HttpURLConnection conn;
        int status;

        try {  
            // pass the name on the URL line
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection" + "//" + name);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // tell the server what format we want back
            conn.setRequestProperty("Accept", "text/xml");

            // wait for response
            status = conn.getResponseCode();

            // If things went poorly, don't try to read any response, just return.
            if (status != 200) {
                // not using msg
                String msg = conn.getResponseMessage();
                return conn.getResponseCode() + " " + msg;
            }
            
            String output = "";
            // things went well so let's read the response
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            while ((output = br.readLine()) != null) {
                response += output + "\n";
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            
        return response;
    }
     
    /**
     * Create a new spy and add the spy to the spy list
     * 
     * @param spy a new spy to be added to the list
     * @return 201 if add successfully
     *         405 if the spy already existed
     */
    public static String doPut(Spy spy) {
        
        System.out.println("Enter do put");
              
        int status;
        String response = "";
        
        try {  
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection" + "//" + spy.getName());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            SpyMessage spyMessage = new SpyMessage(spy);
            out.write(spyMessage.toXML());            
            out.close();
            
            status = conn.getResponseCode();
            
            // If things went poorly, read the error status and message
            if (status != 201) {
                // not using msg
                String msg = conn.getResponseMessage();
                return conn.getResponseCode() + " " + msg;
            }
            
            // read the response message
            String output = "";
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            while ((output = br.readLine()) != null) {
                response += output + "\n";
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return response;   
    }  
    
    /**
     * Delete the given spy
     * 
     * @param name the name of the spy to be deleted
     * @return 200 if delete successfully
     *         404 if the spy not found
     */
    public static String doDelete(String name) {
        
        int status;
        String response = "";
        
        try {  
            URL url = new URL("http://localhost:8080/WebServiceDesignStyles3ProjectServer/SpyListCollection" + "//"+ name);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            status = conn.getResponseCode();
            
            // If things went poorly, get the error status and message
            if (status != 200) {
                // not using msg
                String msg = conn.getResponseMessage();
                return conn.getResponseCode() + " " + msg;
            }
            
            // get the response
            String output = "";
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            while ((output = br.readLine()) != null) {
                response += output + "\n";
            }

            conn.disconnect();
         } 
         catch (MalformedURLException e) {
               e.printStackTrace();
         } catch (IOException e) {
               e.printStackTrace();
         }
        return response;
    }
    
}
