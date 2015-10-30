package webservicedesignstyles2projectclient;

import java.io.StringWriter;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A class to transfer plain text to xml formatted string
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
     * Generate the xml formatted string according to the different type of request 
     * 
     * @return the xml string
     */
    public String toXML() {
        
        String output = "";
        
        try {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
                
                // root elements
		Element rootElement = doc.createElement("spyMessage");
		doc.appendChild(rootElement);

		// spyOperation elements
		Element operation = doc.createElement("operation");
                operation.appendChild(doc.createTextNode(spyOperation));
		rootElement.appendChild(operation);
                
                switch (spyOperation) {
                    case "addSpy":   
                    case "updateSpy":
                        // spyObject elements
                        Element spy = doc.createElement("spy");
                        rootElement.appendChild(spy);

                        // name elements
                        Element name = doc.createElement("name");
                        name.appendChild(doc.createTextNode(spyObject.getName()));
                        spy.appendChild(name);

                        // spyTitle elements
                        Element spyTitle = doc.createElement("spyTitle");
                        spyTitle.appendChild(doc.createTextNode(spyObject.getTitle()));
                        spy.appendChild(spyTitle);

                        // location elements
                        Element location = doc.createElement("location");
                        location.appendChild(doc.createTextNode(spyObject.getLocation()));
                        spy.appendChild(location);

                        // password elements
                        Element password = doc.createElement("password");
                        password.appendChild(doc.createTextNode(spyObject.getPassword()));
                        spy.appendChild(password);
                        
                        break;
                    case "getSpyAsXML":                       
                    case "deleteSpy":
                        // spyObject elements
                        spy = doc.createElement("spy");
                        rootElement.appendChild(spy);

                        // name elements
                        name = doc.createElement("name");
                        name.appendChild(doc.createTextNode(spyObject.getName()));
                        spy.appendChild(name);
                        
                        break;
                    case "getList":
                    case "getListAsXML":
                        break;
                       
                }

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
                StringWriter writer = new StringWriter();
                transformer.transform(new DOMSource(doc), new StreamResult(writer));
                output = writer.getBuffer().toString();  

	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
                
        return output;
    }
}
