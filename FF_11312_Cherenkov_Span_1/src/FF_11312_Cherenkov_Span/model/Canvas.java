package FF_11312_Cherenkov_Span.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * This class represents a collection of drawables.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Canvas extends Drawable implements Observer, Iterable<Drawable> {
	private ArrayList<Drawable> drawables;

	/**
	 * Default constructor
	 */
	public Canvas() {
		drawables = new ArrayList<Drawable>();
	}

	/**
	 * Add a new drawable to canvas
	 * 
	 * @param d
	 *            new drawable
	 */

	public void addDrawable(Drawable d) {
		d.addObserver(this);
		drawables.add(d);
		setChanged();
		notifyObservers(d);
	}

	/**
	 * Returns amount of drawables
	 * 
	 * @return amount of drawables
	 */
	public int getSize() {
		return drawables.size();
	}

	/**
	 * An implementation of Observer.update() method.
	 * 
	 * @see Observer#update(Observable, Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(o);
	}

	/**
	 * An implementation of Iterable.iterator() method.
	 * 
	 * @see Iterable#iterator()
	 */
	@Override
	public Iterator<Drawable> iterator() {
		return drawables.iterator();
	}

	/**
	 * An implementation of Iterable.forEach() method
	 * 
	 * @see Iterable#forEach(Consumer)
	 */
	@Override
	public void forEach(Consumer<? super Drawable> action) {
		drawables.forEach(action);
	}

	/**
	 * An implementation of Iterable.splititerator() method
	 * 
	 * @see Iterable#spliterator()
	 */
	@Override
	public Spliterator<Drawable> spliterator() {
		return drawables.spliterator();
	}

	/**
	 * An implementation of Drawable.getType() method
	 * 
	 * @see Drawable#getType()
	 */
	@Override
	public DrawableType getType() {
		// TODO Auto-generated method stub
		return DrawableType.CANVAS;
	}

}
