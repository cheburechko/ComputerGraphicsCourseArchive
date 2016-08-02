package FF_11312_Cherenkov_WF.matrix.transformation;

import FF_11312_Cherenkov_WF.matrix.Point;

/**
 * This class represents transition transformation
 * 
 * @author Pavel Cherenkov
 * 
 */
public class TransitionTransform extends Transformation {

	/**
	 * Default constructor
	 */
	public TransitionTransform() {
		super();
		for (int i = 0; i < 4; i++)
			setCell(i, i, 1);
	}

	/**
	 * Constructor with initial transitions
	 * 
	 * @param tx
	 *            x transition
	 * @param ty
	 *            y transition
	 * @param tz
	 *            z transition
	 */
	public TransitionTransform(double tx, double ty, double tz) {
		this();
		setTrans(tx, ty, tz);
	}

	/**
	 * Constructor with initial transition vector.
	 * 
	 * @param p
	 *            transition vector
	 */
	public TransitionTransform(Point p) {
		this();
		setTrans(p);
	}

	/**
	 * Sets x transition
	 * 
	 * @param tx
	 *            x transition
	 */
	public void setXTrans(double tx) {
		setCell(3, 0, tx);
	}

	/**
	 * Sets y transition
	 * 
	 * @param ty
	 *            y transition
	 */
	public void setYTrans(double ty) {
		setCell(3, 1, ty);
	}

	/**
	 * Sets z transition
	 * 
	 * @param tz
	 *            z transition
	 */
	public void setZTrans(double tz) {
		setCell(3, 2, tz);
	}

	/**
	 * Sets transitions for all coordinates
	 * 
	 * @param tx
	 *            x transition
	 * @param ty
	 *            y transition
	 * @param tz
	 *            z transition
	 */
	public void setTrans(double tx, double ty, double tz) {
		setXTrans(tx);
		setYTrans(ty);
		setZTrans(tz);
	}

	/**
	 * Sets transition to given transition vector
	 * 
	 * @param p
	 *            transition vector
	 */
	public void setTrans(Point p) {
		setTrans(p.getX(), p.getY(), p.getZ());
	}
}
