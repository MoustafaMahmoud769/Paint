/**
 * 
 */
package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.xml.bind.annotation.XmlElement;

public class Ellipse extends Shape {

	protected double majorAxis;

	protected double minorAxis;

	public Ellipse() {

	}

	public Ellipse(double x, double y, double minorAxis, double majorAxis, Color fill, Color outline, int stroke) {
		super(x, y);
		RGBfill[0] = fill.getRed();
		RGBfill[1] = fill.getGreen();
		RGBfill[2] = fill.getBlue();
		RGBoutline[0] = outline.getRed();
		RGBoutline[1] = outline.getGreen();
		RGBoutline[2] = outline.getBlue();
		super.stroke = stroke;
		this.minorAxis = minorAxis;
		this.majorAxis = majorAxis;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(stroke));
		g.setColor(new Color(RGBfill[0], RGBfill[1], RGBfill[2]));
		g.fill(new Ellipse2D.Double(x, y, minorAxis, majorAxis));
		float dash[] = { 10.0f };
		if (dotted)
			g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
		g.setColor(new Color(RGBoutline[0], RGBoutline[1], RGBoutline[2]));
		g.draw(new Ellipse2D.Double(x, y, minorAxis, majorAxis));
	}

	public double getMajorAxis() {
		return majorAxis;
	}

	public double getMinorAxis() {
		return minorAxis;
	}

	@Override
	public boolean isInShape(double x, double y) {
		double tempx, tempy, r1, r2;
		r1 = minorAxis / 2;
		r2 = majorAxis / 2;
		tempx = r1 + this.x;
		tempy = r2 + this.y;
		double val1 = ((x - tempx) * (x - tempx)) / (r1 * r1);
		double val2 = ((y - tempy) * (y - tempy)) / (r2 * r2);
		return val1 + val2 <= 1;
	}

	@Override
	public void moveShape(double deltaX, double deltaY) {
		super.x += deltaX;
		super.y += deltaY;
	}

	@Override
	public void resizeShape(double deltaX, double deltaY) {
		minorAxis += deltaX;
		majorAxis += deltaY;
		super.x += ((minorAxis - deltaX) / 2) - (minorAxis / 2);
		super.y += ((majorAxis - deltaY) / 2) - (majorAxis / 2);
	}

	@XmlElement
	public void setMajorAxis(double majorAxis) {
		this.majorAxis = majorAxis;
	}

	@XmlElement
	public void setMinorAxis(double minorAxis) {
		this.minorAxis = minorAxis;
	}

}
