package shape;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * 
 */
@XmlRootElement
@XmlSeeAlso({ Triangle.class, Rectangle.class, Ellipse.class, Line.class, Square.class, Circle.class })
public abstract class Shape {

	protected boolean dotted;
	protected int[] RGBfill;

	protected int[] RGBoutline;

	protected int stroke;

	protected double x;

	protected double y;

	public Shape() {

	}

	public Shape(double x, double y) {
		this.x = x;
		this.y = y;
		RGBfill = new int[3];
		RGBoutline = new int[3];
	}

	public abstract void draw(Graphics2D g);

	public int[] getRGBfill() {
		return RGBfill;
	}

	public int[] getRGBoutline() {
		return RGBoutline;
	}

	public int getStroke() {
		return stroke;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean isDotted() {
		return dotted;
	}

	public abstract boolean isInShape(double x, double y);

	public abstract void moveShape(double deltaX, double deltaY);

	public abstract void resizeShape(double deltaX, double deltaY);

	@XmlElement
	public void setDotted(boolean dotted) {
		this.dotted = dotted;
	}

	@XmlElement
	public void setRGBfill(int[] rGBfill) {
		RGBfill = rGBfill;
	}

	@XmlElement
	public void setRGBoutline(int[] rGBoutline) {
		RGBoutline = rGBoutline;
	}

	@XmlElement
	public void setStroke(int thikness) {
		this.stroke = thikness;
	}

	@XmlElement
	public void setX(double x) {
		this.x = x;
	}

	@XmlElement
	public void setY(double y) {
		this.y = y;
	}

}
