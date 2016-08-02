package FF_11312_Cherenkov_WF.matrix;

import FF_11312_Cherenkov_WF.matrix.transformation.Transformation;

/**
 * This class represents a transposed point (1 row, 4 columns)
 * 
 * @author Pavel Cherenkov
 * 
 */
public class TPoint extends Matrix {
	/**
	 * Default constructor
	 */
	public TPoint() {
		super(4, 1);
		setCell(3, 0, 1);
	}

	/**
	 * Constructor with initial coordinates
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @param z
	 *            z
	 */
	public TPoint(double x, double y, double z) {
		this();
		setX(x);
		setY(y);
		setZ(z);
	}

	/**
	 * Copy constructor
	 * 
	 * @param p
	 *            source point
	 */
	public TPoint(TPoint p) {
		super(p);
	}

	/**
	 * Sets x coordinate
	 * 
	 * @param x
	 *            x
	 */
	public void setX(double x) {
		setCell(0, 0, x);
	}

	/**
	 * Sets y coordinate
	 * 
	 * @param y
	 *            y
	 */
	public void setY(double y) {
		setCell(1, 0, y);
	}

	/**
	 * Sets z coordinate
	 * 
	 * @param z
	 *            z
	 */
	public void setZ(double z) {
		setCell(2, 0, z);
	}

	/**
	 * Gets x coordinate
	 * 
	 * @return x
	 */
	public double getX() {
		return getCell(0, 0);
	}

	/**
	 * Gets y coordinate
	 * 
	 * @return y
	 */
	public double getY() {
		return getCell(1, 0);
	}

	/**
	 * Gets z coordinate
	 * 
	 * @return z
	 */
	public double getZ() {
		return getCell(2, 0);
	}

	/**
	 * Gets homogeneous coordinate
	 * 
	 * @return homogeneous coordinate
	 */
	public double getW() {
		return getCell(3, 0);
	}

	/**
	 * Sets homogeneous coordinate
	 * 
	 * @param w
	 *            homogeneous coordinate
	 */
	public void setW(double w) {
		setCell(3, 0, w);
	}

	/**
	 * Applies transformation to this point, return new point
	 * 
	 * @param a
	 *            transformation
	 * @return transformed point
	 */
	public TPoint mult(Transformation a) {
		TPoint result = new TPoint();
		super.mult(a, result);
		return result;
	}

	public TPoint mult(double value) {
		TPoint result = new TPoint();
		super.mult(value, result);
		return result;
	}

	public TPoint add(TPoint a) {
		TPoint result = new TPoint();
		super.add(a, result);
		return result;
	}
}
