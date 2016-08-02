package FF_11312_Cherenkov_LE;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * This class represents a collection of polylines.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Canvas extends Observable implements Observer, Iterable<Polyline> {
	private ArrayList<Polyline> polylines;

	/**
	 * Default constructor
	 */
	public Canvas() {
		polylines = new ArrayList<Polyline>();
	}

	/**
	 * Add a new polyline to canvas
	 * 
	 * @param p
	 *            new polyline
	 */

	public void addPolyline(Polyline p) {
		p.addObserver(this);
		polylines.add(p);
	}

	/**
	 * Returns amount of polylines
	 * 
	 * @return amount of polylines
	 */
	public int getSize() {
		return polylines.size();
	}

	/**
	 * Erases last drawn polyline
	 */
	public void eraseLastPolyline() {
		int index = polylines.size() - 1;
		if (index < 0)
			return;

		Polyline polyline = polylines.get(index);
		polyline.deleteObserver(this);

		Rectangle updateArea = new Rectangle();
		for (Point p : polyline) {
			updateArea.add(p);
		}
		updateArea.grow(1, 1);

		polylines.remove(index);

		setChanged();
		notifyObservers(updateArea);
	}

	/**
	 * An implementation of Observer.update() method.
	 * 
	 * @see Observer#update(Observable, Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(arg);
	}

	/**
	 * An implementation of Iterable.iterator() method.
	 * 
	 * @see Iterable#iterator()
	 */
	@Override
	public Iterator<Polyline> iterator() {
		return polylines.iterator();
	}

	/**
	 * An implementation of Iterable.forEach() method
	 * 
	 * @see Iterable#forEach(Consumer)
	 */
	@Override
	public void forEach(Consumer<? super Polyline> action) {
		polylines.forEach(action);
	}

	/**
	 * An implementation of Iterable.splititerator() method
	 * 
	 * @see Iterable#spliterator()
	 */
	@Override
	public Spliterator<Polyline> spliterator() {
		return polylines.spliterator();
	}

}
