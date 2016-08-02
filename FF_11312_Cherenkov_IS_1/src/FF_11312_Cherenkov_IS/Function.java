package FF_11312_Cherenkov_IS;

/**
 * This interface represents a single function passed to FuncMap class.
 * 
 * @author Pavel Cherenkov
 * 
 */
public interface Function {
	/**
	 * Computes function at given point
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return function value
	 */
	public abstract double compute(double x, double y);
}
