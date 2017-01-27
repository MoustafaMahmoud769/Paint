package drawTools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import shape.Circle;
import shape.Ellipse;
import shape.Line;
import shape.Rectangle;
import shape.Shape;
import shape.Square;
import shape.Triangle;

public class PaintGrid extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage curGrid;
	private ArrayList<Shape> data;
	Graphics g;
	private Stack<ArrayList<Shape>> left, right;
	private boolean mouseClck, moveMode, resizeMode, mouseMove, triangle;
	private Color outc, fillc;
	private ArrayList<Point> pts;
	private int selected = 0, shapeAction = -1;
	private int strokeVal;
	private double x1, x2, x3, y1, y2, y3;

	public PaintGrid() {
		data = new ArrayList<Shape>();
		pts = new ArrayList<Point>();
		setLeft(new Stack<ArrayList<Shape>>());
		setRight(new Stack<ArrayList<Shape>>());

		mouseClck = false;
		mouseMove = false;
		moveMode = false;
		resizeMode = false;
		triangle = false;
		MyListener();
		repaint();
	}

	public void changeFillColor(Color c) {
		if (shapeAction == -1)
			throw new RuntimeException();
		left.push(new ArrayList<Shape>(data));
		int[] color = new int[3];
		color[0] = c.getRed();
		color[1] = c.getGreen();
		color[2] = c.getBlue();
		data.get(shapeAction).setRGBfill(color);
		shapeAction = -1;
		repaint();
	}

	public void changeStrokeColor(Color c) {
		if (shapeAction == -1)
			throw new RuntimeException();
		left.push(new ArrayList<Shape>(data));
		int[] color = new int[3];
		color[0] = c.getRed();
		color[1] = c.getGreen();
		color[2] = c.getBlue();
		data.get(shapeAction).setRGBoutline(color);
		shapeAction = -1;
		repaint();
	}

	public void deleteShape() {
		if (shapeAction == -1)
			throw new RuntimeException();
		left.push(new ArrayList<Shape>(data));
		data.remove(shapeAction);
		shapeAction = -1;
		repaint();
	}

	/**
	 * @return the data
	 */
	public ArrayList<Shape> getData() {
		return data;
	}

	public int getDrawMode() {
		return selected;
	}

	private Ellipse getEllipse(double x, double y) {
		double f, s, r1, r2;
		f = 2 * x1 - x;
		s = 2 * y1 - y;
		r1 = Math.abs(x - x1) * 2;
		r2 = Math.abs(y - y1) * 2;
		if (x1 > x)
			f -= r1;
		if (y1 > y)
			s -= r2;
		return new Ellipse(f, s, r1, r2, fillc, outc, strokeVal);
	}

	public Color getFillColor() {
		return fillc;
	}

	public BufferedImage getImg() {
		return curGrid;
	}

	/**
	 * @return the left
	 */
	public Stack<ArrayList<Shape>> getLeft() {
		return left;
	}

	public Color getoutColor() {
		return outc;
	}

	private Rectangle getRectangle(double x, double y) {
		double f, s;
		f = x1;
		if (x1 > x)
			f -= Math.abs(x - x1);
		s = y1;
		if (y1 > y)
			s -= Math.abs(y - y1);
		return new Rectangle(f, s, Math.abs(x - x1), Math.abs(y - y1), fillc, outc, strokeVal);
	}

	/**
	 * @return the right
	 */
	public Stack<ArrayList<Shape>> getRight() {
		return right;
	}

	private Square getSquare(double x, double y) {
		double r = Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
		r /= Math.sqrt(2);
		double f, s;
		f = x1;
		if (x1 > x)
			f -= r;
		s = y1;
		if (y1 > y)
			s -= r;
		return new Square(f, s, r, fillc, outc, strokeVal);
	}

	public int getStroke() {
		return strokeVal;
	}

	public int isClicked(double x, double y) {
		for (int i = data.size() - 1; i >= 0; --i) {
			if (data.get(i).isInShape(x, y))
				return i;
		}
		return -1;
	}

	private void moveShape() {
		if (shapeAction == -1) {
			JOptionPane.showMessageDialog(null, "Please Move The Shape Correctly");
			return;
		}
		data.get(shapeAction).moveShape(x3 - x1, y3 - y1);
		shapeAction = -1;
	}

	private void MyListener() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent arg0) {
				x1 = arg0.getX();
				y1 = arg0.getY();
				if (selected == 6 && mouseMove)
					return;
				if (selected == 0) {
					shapeAction = isClicked(x1, y1);
					repaint();
				}
				mouseClck = true;
				triangle = true;
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				x3 = arg0.getX();
				y3 = arg0.getY();
				mouseClck = false;
				if (selected != 0) {
					left.push(new ArrayList<Shape>(data));
					right.clear();
					setShape();
				} else if (moveMode) {
					left.push(new ArrayList<Shape>(data));
					right.clear();
					moveShape();
					moveMode = false;
					shapeAction = -1;
				} else if (resizeMode) {
					left.push(new ArrayList<Shape>(data));
					right.clear();
					resizeShape();
					resizeMode = false;
					shapeAction = -1;
				}
				if (selected == 6) {
					Point p1 = new Point();
					p1.setLocation(x1, y1);
					pts.add(p1);
					Point p2 = new Point();
					p2.setLocation(x3, y3);
					pts.add(p2);
					mouseMove = true;
					triangle = false;
				}
				repaint();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent arg0) {
				x2 = arg0.getX();
				y2 = arg0.getY();
				repaint();
			}

			public void mouseMoved(MouseEvent e) {
				if (mouseMove) {
					x2 = e.getX();
					y2 = e.getY();
					repaint();
				}
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		curGrid = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		Graphics2D i2d = curGrid.createGraphics();
		if (shapeAction != -1) {
			data.get(shapeAction).setDotted(true);
		}
		for (int i = 0; i < data.size(); i++) {
			if ((moveMode || resizeMode) && shapeAction == i)
				continue;
			data.get(i).draw(g2d);
			data.get(i).draw(i2d);
		}
		if (shapeAction != -1)
			data.get(shapeAction).setDotted(false);
		if (mouseClck || mouseMove)
			whileDraw(g2d);
		if (moveMode && shapeAction != -1)
			whileMove(g2d);
		if (resizeMode && shapeAction != -1)
			whileResize(g2d);
	}

	public void redo() {
		if (right.size() == 0)
			throw new RuntimeException();
		shapeAction = -1;
		ArrayList<Shape> temp = new ArrayList<Shape>(data);
		left.push(temp);
		data = new ArrayList<Shape>(right.pop());
		repaint();
	}

	private void resizeShape() {
		if (shapeAction == -1) {
			JOptionPane.showMessageDialog(null, "Please Resize The Shape Correctly");
			return;
		}
		data.get(shapeAction).resizeShape(x3 - x1, y3 - y1);
		shapeAction = -1;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(ArrayList<Shape> data) {
		this.data = data;
	}

	public void setDrawMode(int x) {
		selected = x;
		pts.clear();
		shapeAction = -1;
	}

	public void setFillColor(Color c) {
		fillc = c;
	}

	/**
	 * @param left
	 *            the left to set
	 */
	public void setLeft(Stack<ArrayList<Shape>> left) {
		this.left = left;
	}

	public void setMoveMode(boolean x) {
		if (x && shapeAction == -1)
			throw new RuntimeException();
		moveMode = x;
	}

	public void setOutColor(Color c) {
		outc = c;
	}

	public void setResizeMode(boolean x) {
		if (x && shapeAction == -1)
			throw new RuntimeException();
		resizeMode = x;
	}

	/**
	 * @param right
	 *            the right to set
	 */
	public void setRight(Stack<ArrayList<Shape>> right) {
		this.right = right;
	}

	private void setShape() {
		if (selected == 1) {
			data.add(new Line(x1, y1, x3, y3, fillc, outc, strokeVal));
			selected = 0;
		} else if (selected == 2) {
			data.add(getEllipse(x3, y3));
			selected = 0;
		} else if (selected == 3) {
			double r = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));
			data.add(new Circle(x1 - r, y1 - r, r * 2.0, fillc, outc, strokeVal));
			selected = 0;
		} else if (selected == 4) {
			data.add(getRectangle(x3, y3));
			selected = 0;
		} else if (selected == 5) {
			data.add(getSquare(x3, y3));
			selected = 0;
		} else if (selected == 6 && pts.size() == 2) {
			Point temp = new Point();
			temp.setLocation(x3, y3);
			pts.add(temp);
			data.add(new Triangle(pts, fillc, outc, strokeVal));
			pts.clear();
			selected = 0;
			mouseMove = false;
		}

	}

	public void setStroke(int val) {
		strokeVal = val;
	}

	public void undo() {
		if (left.size() == 0)
			throw new RuntimeException();
		shapeAction = -1;
		ArrayList<Shape> temp = new ArrayList<Shape>(data);
		getRight().push(temp);
		data = new ArrayList<Shape>(left.pop());
		repaint();
	}

	private void whileDraw(Graphics2D g2d) {
		Shape shape = new Line(0, 0, 0, 0, fillc, outc, strokeVal);
		if (selected == 1)
			shape = new Line(x1, y1, x2, y2, fillc, outc, strokeVal);
		else if (selected == 2)
			shape = getEllipse(x2, y2);
		else if (selected == 3) {
			double r = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
			shape = new Circle(x1 - r, y1 - r, r * 2.0, fillc, outc, strokeVal);
		} else if (selected == 4)
			shape = getRectangle(x2, y2);
		else if (selected == 5)
			shape = getSquare(x2, y2);
		else if (selected == 6 && triangle)
			shape = new Line(x1, y1, x2, y2, fillc, outc, strokeVal);
		else if (selected == 6 && mouseMove) {
			Point p = new Point();
			p.setLocation(x2, y2);
			pts.add(p);
			shape = new Triangle(pts, fillc, outc, strokeVal);
			pts.remove(2);
		}
		shape.draw(g2d);
	}

	private void whileMove(Graphics2D g2d) {
		if (shapeAction == -1)
			throw new RuntimeException();
		data.get(shapeAction).moveShape(x2 - x1, y2 - y1);
		x1 = x2;
		y1 = y2;
		data.get(shapeAction).draw(g2d);
	}

	private void whileResize(Graphics2D g2d) {
		if (shapeAction == -1)
			throw new RuntimeException();
		data.get(shapeAction).resizeShape(x2 - x1, y2 - y1);
		x1 = x2;
		y1 = y2;
		data.get(shapeAction).draw(g2d);
	}

}
