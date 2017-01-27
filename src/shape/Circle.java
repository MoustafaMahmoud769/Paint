package shape;

import java.awt.Color;

public class Circle extends Ellipse {

	public Circle(double x, double y, double radius, Color fill, Color outline, int stroke) {
		super(x, y, radius, radius, fill, outline, stroke);
	}

	@Override
	public void resizeShape(double deltaX, double deltaY) {
		minorAxis += deltaX;
		majorAxis += deltaX;
		super.x += ((minorAxis - deltaX) / 2) - (minorAxis / 2);
		super.y += ((majorAxis - deltaX) / 2) - (majorAxis / 2);
	}

}
