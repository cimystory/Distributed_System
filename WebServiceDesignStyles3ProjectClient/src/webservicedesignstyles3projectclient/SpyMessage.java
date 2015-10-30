package webservicedesignstyles3projectclient;

import java.io.StringWriter;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * SpyMessage class wraps the methods to transfer information from plain text to XML
 * @author Gao
 */
public class SpyMessage {
    public Spy spyObject;
    
    public SpyMessage() {
        
    }

    public SpyMessage(Spy spy) {
        this.spyObject = spy;
    }
    
    /**
     * Transfer the information of a spy to a XML representation
     * 
     * @return the XML formatted String of the information of the spy 
     */
    public String toXML() {
        
        String output = "";
        
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // root elements
            Element rootElement = doc.createElement("spy");
            doc.appendChild(rootElement);

            // name elements
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(spyObject.getName()));
            rootElement.appendChild(name);

            // spyTitle elements
            Element spyTitle = doc.createElement("spyTitle");
            spyTitle.appendChild(doc.createTextNode(spyObject.getTitle()));
            rootElement.appendChild(spyTitle);

            // location elements
            Element location = doc.createElement("location");
            location.appendChild(doc.createTextNode(spyObject.getLocation()));
            rootElement.appendChild(location);

            // password elements
            Element password = doc.createElement("password");
            password.appendChild(doc.createTextNode(spyObject.getPassword()));
            rootElement.appendChild(password);

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
