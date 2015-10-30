package cmu.edu.andrew.gaoj;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * SpyMessage class to transfer a xml string to a document for further extraction
 * 
 * @author Gao
 */
public class SpyMessage {
    public Spy spyObject;
    public String spyOperation;
    
    public SpyMessage() {
        
    }

    public SpyMessage(Spy spy, String operation) {
        this.spyObject = spy;
        this.spyOperation = operation;
    }
    
    
    public Document getDocument(String xmlString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document spyDoc = null;
        
        try {
            builder = factory.newDocumentBuilder();
            spyDoc = builder.parse(new InputSource(new StringReader(xmlString)));
        } catch(Exception e) {            
            e.printStackTrace();           
        }
        return spyDoc;
              
    }
}
