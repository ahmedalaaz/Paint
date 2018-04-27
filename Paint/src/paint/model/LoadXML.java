package paint.model;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import paint.controller.ShapesController;
import paint.view.Main;

public class LoadXML implements LoaderStrategy {

	@Override
	public ArrayList<Shape> load(String path) {
		// TODO Auto-generated method stub
		ArrayList<Shape> shapes = new ArrayList<Shape>();

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(path));
			NodeList nodes = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String className = element.getElementsByTagName("class").item(0).getChildNodes().item(0)
							.getNodeValue();
					className = className.substring(6, className.length());
					if (!ShapesController.getInstance(Main.getController()).isSupportedShape(className))
						return null;// TODO show error message cannot load
					Shape tempShape = (new ShapesFactory()).instantiate(className, Shape.class);
					if(tempShape == null)return null; //TODO show error message
					tempShape.loadXML(element);
					shapes.add(tempShape);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO show error message
			return null;
		}
		return shapes;

	}

}
