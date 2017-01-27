/**
 * 
 */
package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.xml.bind.annotation.XmlElement;

public class Rectangle extends Shape {
	protected double height;

	protected double width;

	public Rectangle() {

	}

	public Rectangle(double x, double y, double width, double height, Color fill, Color outline, int stroke) {
		super(x, y);
		RGBfill[0] = fill.getRed();
		RGBfill[1] = fill.getGreen();
		RGBfill[2] = fill.getBlue();
		RGBoutline[0] = outline.getRed();
		RGBoutline[1] = outline.getGreen();
		RGBoutline[2] = outline.getBlue();
		super.stroke = stroke;
		this.height = height;
		this.width = width;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(stroke));
		g.setColor(new Color(RGBfill[0], RGBfill[1], RGBfill[2]));
		g.fill(new Rectangle2D.Double(x, y, width, height));
		float dash[] = { 10.0f };
		if (dotted)
			g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
		g.setColor(new Color(RGBoutline[0], RGBoutline[1], RGBoutline[2]));
		g.draw(new Rectangle2D.Double(x, y, width, height));
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	@Override
	public boolean isInShape(double x, double y) {
		double x1, x2, y1, y2;
		x1 = this.x;
		y1 = this.y;
		x2 = x1 + width;
		y2 = y1 + height;
		boolean v1, v2;
		v1 = (x >= x1 && x <= x2);
		v2 = (y >= y1 && y <= y2);
		return v1 && v2;
	}

	@Override
	public void moveShape(double deltaX, double deltaY) {
		this.x += deltaX;
		this.y += deltaY;
	}

	@Override
	public void resizeShape(double deltaX, double deltaY) {
		width += deltaX;
		height += deltaY;
	}

	@XmlElement
	public void setHeight(double height) {
		this.height = height;
	}

	@XmlElement
	public void setWidth(double width) {
		this.width = width;
	}

}
