package paint.model;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

public class SaveXML implements SaverStrategy{

	@Override
	public String save(ArrayList<Shape > shapes) {
		// TODO Auto-generated method stub
		StringWriter outWriter = new StringWriter();
		String output = null;
		   DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder;
	        try {
	            dBuilder = dbFactory.newDocumentBuilder();
	            Document doc = dBuilder.newDocument();
	            Element rootElement =
	                doc.createElement("shapes");
	            doc.appendChild(rootElement);
	            for(Shape shape : shapes) {
	           rootElement.appendChild(shape.getXMLNode(doc));
	            }
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	            DOMSource source = new DOMSource(doc);
	            StreamResult console = new StreamResult(outWriter);
	            transformer.transform(source, console);
	            StringBuffer sb = outWriter.getBuffer(); 
	            output = sb.toString();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null; //TODO show error message
	        }
		return output;
	}

}
