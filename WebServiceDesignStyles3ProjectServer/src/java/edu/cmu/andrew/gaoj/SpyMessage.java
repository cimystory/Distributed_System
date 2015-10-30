package edu.cmu.andrew.gaoj;

import java.io.StringReader;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
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
    
    /**
     * Transfer the xml string to a document
     * 
     * @param xmlString the given xml string
     * @return a document containing the xml string
     */
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
