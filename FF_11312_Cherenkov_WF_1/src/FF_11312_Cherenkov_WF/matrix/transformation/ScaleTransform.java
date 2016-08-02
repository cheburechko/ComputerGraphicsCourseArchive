package FF_11312_Cherenkov_WF.matrix.transformation;

/**
 * This class represents scaling transformation.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class ScaleTransform extends Transformation {

	/**
	 * Default constructor
	 * 
	 * @param sx
	 *            x scale
	 * @param sy
	 *            y scale
	 * @param sz
	 *            z scale
	 */
	public ScaleTransform(double sx, double sy, double sz) {
		super();
		setScale(sx, sy, sz);
		setCell(3, 3, 1);
	}

	/**
	 * Sets x scale
	 * 
	 * @param s
	 *            x scale
	 */
	public void setXScale(double s) {
		setCell(0, 0, s);
	}

	/**
	 * Sets y scale
	 * 
	 * @param s
	 *            y scale
	 */
	public void setYScale(double s) {
		setCell(1, 1, s);
	}

	/**
	 * Sets z scale
	 * 
	 * @param s
	 *            z scale
	 */
	public void setZScale(double s) {
		setCell(2, 2, s);
	}

	/**
	 * Sets scales for all coordinates
	 * 
	 * @param sx
	 *            x scale
	 * @param sy
	 *            y scale
	 * @param sz
	 *            z scale
	 */
	public void setScale(double sx, double sy, double sz) {
		setXScale(sx);
		setYScale(sy);
		setZScale(sz);
	}
}