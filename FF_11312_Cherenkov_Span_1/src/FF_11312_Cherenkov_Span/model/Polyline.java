package FF_11312_Cherenkov_Span.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Continuous line formed by multiple straight line segments.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Polyline extends Drawable implements Iterable<Point> {
	private ArrayList<Point> polyline;
	private int width;

	/**
	 * Default constructor
	 */
	public Polyline() {
		polyline = new ArrayList<Point>();
		setWidth(1);
	}

	/**
	 * Constructor with the starting point
	 * 
	 * @param p
	 *            starting point
	 */
	public Polyline(Point p) {
		this();
		polyline.add(p);
	}

	/**
	 * Constructor with the starting point and width
	 * 
	 * @param p
	 *            starting point
	 * @param width
	 *            line width in pixels
	 */
	public Polyline(Point p, int width) {
		this(p);
		this.setWidth(width);
	}

	/**
	 * Concatenates the point to the end of the polyline
	 * 
	 * @param p
	 *            ending point
	 */
	public void addPoint(Point p) {
		polyline.add(p);
		if (isLine()) {
			setChanged();
			notifyObservers(this);
		}
	}

	/**
	 * Shows whether this polyline has at least 2 points
	 * 
	 * @return true if there are more than 2 points in the line, false otherwise
	 */
	public boolean isLine() {
		return polyline.size() > 1;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	public int getLength() {
		return polyline.size();
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * An implementation of Iterable.iterator() method.
	 * 
	 * @see Iterable#iterator()
	 */
	@Override
	public Iterator<Point> iterator() {
		return polyline.iterator();
	}

	/**
	 * An implementation of Iterable.forEach() method
	 * 
	 * @see Iterable#forEach(Consumer)
	 */
	@Override
	public void forEach(Consumer<? super Point> action) {
		polyline.forEach(action);
	}

	/**
	 * An implementation of Iterable.splititerator() method
	 * 
	 * @see Iterable#spliterator()
	 */
	@Override
	public Spliterator<Point> spliterator() {
		return polyline.spliterator();
	}

	/**
	 * An implementation of Drawable.getType() method
	 * 
	 * @see Drawable#getType()
	 */
	@Override
	public DrawableType getType() {
		// TODO Auto-generated method stub
		return DrawableType.POLYLINE;
	}

}
