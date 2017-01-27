/**
 * 
 */
package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class Triangle extends Shape {

	protected int[] xPts;

	protected int[] yPts;

	public Triangle() {

	}

	public Triangle(ArrayList<Point> points, Color fill, Color outline, int stroke) {
		super(0, 0);
		super.stroke = stroke;
		xPts = new int[3];
		yPts = new int[3];
		RGBfill[0] = fill.getRed();
		RGBfill[1] = fill.getGreen();
		RGBfill[2] = fill.getBlue();
		RGBoutline[0] = outline.getRed();
		RGBoutline[1] = outline.getGreen();
		RGBoutline[2] = outline.getBlue();
		for (int i = 0; i < points.size(); i++) {
			xPts[i] = points.get(i).x;
			yPts[i] = points.get(i).y;
		}
	}

	private boolean cn_PnPoly(Point P) {
		int cn = 0;
		for (int i = 0; i < 3; i++) {
			if (((yPts[i] <= P.getY()) && (yPts[(i + 1) % 3] > P.getY()))
					|| ((yPts[i] > P.getY()) && (yPts[(i + 1) % 3] <= P.getY()))) {
				double vt = (double) (P.getY() - yPts[i]) / (yPts[(i + 1) % 3] - yPts[i]);
				if (P.x < xPts[i] + vt * (xPts[(i + 1) % 3] - xPts[i]))
					++cn;
			}
		}
		return ((cn & 1) == 1);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(stroke));
		g.setColor(new Color(RGBfill[0], RGBfill[1], RGBfill[2]));
		g.fillPolygon(xPts, yPts, 3);
		float dash[] = { 10.0f };
		if (dotted)
			g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
		g.setColor(new Color(RGBoutline[0], RGBoutline[1], RGBoutline[2]));
		g.drawPolygon(xPts, yPts, 3);
	}

	public int[] getxPts() {
		return xPts;
	}

	public int[] getyPts() {
		return yPts;
	}

	@Override
	public boolean isInShape(double x, double y) {
		Point temp = new Point();
		temp.setLocation(x, y);
		return cn_PnPoly(temp);
	}

	@Override
	public void moveShape(double deltaX, double deltaY) {
		for (int i = 0; i < 3; i++) {
			xPts[i] += deltaX;
			yPts[i] += deltaY;
		}

	}

	@Override
	public void resizeShape(double deltaX, double deltaY) {
		for (int i = 1; i < 3; i++) {
			xPts[i] += deltaX;
			yPts[i] += deltaY;
		}
	}

	@XmlElement
	public void setxPts(int[] xPts) {
		this.xPts = xPts;
	}

	@XmlElement
	public void setyPts(int[] yPts) {
		this.yPts = yPts;
	}

}
