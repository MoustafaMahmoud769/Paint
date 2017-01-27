package files;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import shape.Shape;

@XmlRootElement(name = "Shapes")
@XmlAccessorType(XmlAccessType.FIELD)
public class Shapes {

	@XmlElement(name = "Shape")
	private ArrayList<Shape> data = null;

	public ArrayList<Shape> getShapes() {
		return data;
	}

	public void setShapes(ArrayList<Shape> data) {
		this.data = data;
	}

}
