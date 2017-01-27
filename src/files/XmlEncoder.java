package files;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import drawTools.PaintGrid;
import shape.Shape;

public class XmlEncoder {

	private ArrayList<Shape> data;
	private Shapes shapes;
	private PaintGrid grid;

	public XmlEncoder(PaintGrid grid) {
		this.grid = grid;
		shapes = new Shapes();
	}

	public void save(String path) throws JAXBException {
		File file = new File(path);
		JAXBContext jaxbContext = JAXBContext.newInstance(Shapes.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		data = grid.getData();
		shapes.setShapes(data);
		jaxbMarshaller.marshal(shapes, file);
	}

	public ArrayList<Shape> load(String path) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Shapes.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		shapes = (Shapes) jaxbUnmarshaller.unmarshal(new File(path));
		data = shapes.getShapes();
		return data;
	}

}
