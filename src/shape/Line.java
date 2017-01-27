/**
 * 
 */
package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.xml.bind.annotation.XmlElement;

public class Line extends Shape {
	protected double endpointX;

	protected double endpointY;

	public Line() {

	}

	public Line(double x, double y, double endX, double endY, Color fill, Color outline, int stroke) {
		super(x, y);
		RGBfill[0] = fill.getRed();
		RGBfill[1] = fill.getGreen();
		RGBfill[2] = fill.getBlue();
		RGBoutline[0] = outline.getRed();
		RGBoutline[1] = outline.getGreen();
		RGBoutline[2] = outline.getBlue();
		super.stroke = stroke;
		this.endpointX = endX;
		this.endpointY = endY;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(stroke));
		float dash[] = { 10.0f };
		if (dotted)
			g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
		g.setColor(new Color(RGBoutline[0], RGBoutline[1], RGBoutline[2]));
		g.draw(new Line2D.Double(x, y, endpointX, endpointY));
	}

	public double getEndpointX() {
		return endpointX;
	}

	public double getEndpointY() {
		return endpointY;
	}

	@Override
	public boolean isInShape(double x, double y) {
		if (this.x == endpointX)
			return (y >= Math.min(this.y, endpointY) && (y <= Math.max(this.y, endpointY)));
		if (this.y == endpointY)
			return (x >= Math.min(this.x, endpointX) && (x <= Math.max(this.x, endpointX)));
		double slope = (this.y - endpointY) / (this.x - endpointX);
		double val = (slope * x) - (slope * endpointX) + endpointY;
		return Math.abs(y - val) <= 8;
	}

	@Override
	public void moveShape(double deltaX, double deltaY) {
		this.x += deltaX;
		this.y += deltaY;
		endpointX += deltaX;
		endpointY += deltaY;
	}

	@Override
	public void resizeShape(double deltaX, double deltaY) {
		endpointX += deltaX;
		endpointY += deltaY;
	}

	@XmlElement
	public void setEndpointX(double endpointX) {
		this.endpointX = endpointX;
	}

	@XmlElement
	public void setEndpointY(double endpointY) {
		this.endpointY = endpointY;
	}

}
