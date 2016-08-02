package FF_11312_Cherenkov_Span.model;

import java.util.Observable;

/**
 * An abstract class that represents an object to draw.
 * 
 * @author Pavel Cherenkov
 * 
 */
public abstract class Drawable extends Observable {
	/**
	 * Get type of drawable
	 * 
	 * @return type of drawable
	 */
	public abstract DrawableType getType();
}
