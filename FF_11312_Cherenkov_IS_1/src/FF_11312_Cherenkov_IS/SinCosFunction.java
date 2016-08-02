package FF_11312_Cherenkov_IS;

/**
 * Sin(x) + Cos(y) function for rendering in the main window
 * 
 * @author Pavel Cherenkov
 * 
 */
public class SinCosFunction implements Function {

	/**
	 * Return sin(x)+cos(y)
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return sin(x)+cos(y)
	 */
	@Override
	public double compute(double x, double y) {
		return Math.sin(x) + Math.cos(y);
	}

}
