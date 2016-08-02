package FF_11312_Cherenkov_LE;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Continuous line formed by multiple straight line segments.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Polyline extends Observable implements Iterable<Point> {
	private ArrayList<Point> polyline;
	private Rectangle updateArea;

	/**
	 * Default constructor
	 */
	public Polyline() {
		polyline = new ArrayList<Point>();
		updateArea = new Rectangle();
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
	 * Concatenates the point to the end of the polyline
	 * 
	 * @param p
	 *            ending point
	 */
	public void addPoint(Point p) {
		polyline.add(p);
		if (isLine()) {
			Point last = polyline.get(polyline.size() - 2);
			setChanged();

			updateArea.setFrameFromDiagonal(last, p);
			updateArea.grow(1, 1);
			notifyObservers(updateArea);
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

}
