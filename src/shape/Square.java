package shape;

import java.awt.Color;

public class Square extends Rectangle {

	public Square(double x, double y, double length, Color fill, Color outline, int stroke) {
		super(x, y, length, length, fill, outline, stroke);
	}

	@Override
	public void resizeShape(double deltaX, double deltaY) {
		width += deltaX;
		height += deltaX;
	}
}
