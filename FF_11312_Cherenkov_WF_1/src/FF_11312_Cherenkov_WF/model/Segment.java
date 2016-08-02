package FF_11312_Cherenkov_WF.model;

import java.util.Observable;
import java.util.Observer;

import FF_11312_Cherenkov_WF.matrix.Point;
import FF_11312_Cherenkov_WF.matrix.transformation.Transformation;

/**
 * This class represents a single segment
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Segment implements Observer {
	private Point p1, p2;
	private Point corner1, corner2;

	/**
	 * Default constructor
	 * 
	 * @param p1
	 *            first point of the segment
	 * @param p2
	 *            second point of the segment
	 */
	public Segment(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
		p1.addObserver(this);
		p2.addObserver(this);
		corner1 = new Point();
		corner2 = new Point();
		computeBoundingBox();
	}

	/**
	 * Computes the bounding box of the segments
	 */
	public void computeBoundingBox() {
		corner1.setX(Math.min(p1.getX(), p2.getX()));
		corner2.setX(Math.max(p1.getX(), p2.getX()));
		corner1.setY(Math.min(p1.getY(), p2.getY()));
		corner2.setY(Math.max(p1.getY(), p2.getY()));
		corner1.setZ(Math.min(p1.getZ(), p2.getZ()));
		corner2.setZ(Math.max(p1.getZ(), p2.getZ()));
	}

	/**
	 * Gets minimal z of the segments bounding box
	 * 
	 * @return minimal z
	 */
	public double minZ() {
		return corner1.getZ();
	}

	/**
	 * Gets minimal y of the segments bounding box
	 * 
	 * @return minimal y
	 */
	public double minY() {
		return corner1.getY();
	}

	/**
	 * Gets minimal x of the segments bounding box
	 * 
	 * @return minimal x
	 */
	public double minX() {
		return corner1.getX();
	}

	/**
	 * Gets maximal x of the segments bounding box
	 * 
	 * @return maximal x
	 */
	public double maxX() {
		return corner2.getX();
	}

	/**
	 * Gets maximal y of the segments bounding box
	 * 
	 * @return maximal y
	 */
	public double maxY() {
		return corner2.getY();
	}

	/**
	 * Gets maximal z of the segments bounding box
	 * 
	 * @return maximal z
	 */
	public double maxZ() {
		return corner2.getZ();
	}

	/**
	 * Gets the corner of the bounding box with minimal coordinates
	 * 
	 * @return corner with minimal coordinates
	 */
	public Point getMinCorner() {
		return corner1;
	}

	/**
	 * Gets the corner of the bounding box with maximal coordinates
	 * 
	 * @return corner with maximal coordinates
	 */
	public Point getMaxCorner() {
		return corner2;
	}

	/**
	 * Applies transformation to a segment
	 * 
	 * @param t
	 *            transformation
	 */
	public void transform(Transformation t) {
		p1.deleteObserver(this);
		p2.deleteObserver(this);
		p1 = t.mult(p1);
		p2 = t.mult(p2);
		p1.addObserver(this);
		p2.addObserver(this);
		computeBoundingBox();
	}

	/**
	 * Return the first point the segment
	 * 
	 * @return first point of the segment
	 */
	public Point getFirstPoint() {
		return p1;
	}

	/**
	 * Return the second point the segment
	 * 
	 * @return second point of the segment
	 */
	public Point getSecondPoint() {
		return p2;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		computeBoundingBox();
	}

	/**
	 * Prints the segment in console
	 */
	public void printInConsole() {
		System.out.println("Segment");
		p1.printInConsole();
		p2.printInConsole();
	}
}