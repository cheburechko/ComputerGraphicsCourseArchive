package FF_11312_Cherenkov_WF.matrix;

/**
 * This is matrix which represents a point (or a vector). It has 4 rows and 1
 * column
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Point extends Matrix {
	/**
	 * Default constructor
	 */
	public Point() {
		super(1, 4);
		setCell(0, 3, 1);
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
	public Point(double x, double y, double z) {
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
	public Point(Point p) {
		super(p);
	}

	/**
	 * Constructor from transposed point. (basically a transposition operation)
	 * 
	 * @param p
	 *            transposed point
	 */
	public Point(TPoint p) {
		this();
		setX(p.getX());
		setY(p.getY());
		setZ(p.getZ());
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
		setCell(0, 1, y);
	}

	/**
	 * Sets z coordinate
	 * 
	 * @param z
	 *            z
	 */
	public void setZ(double z) {
		setCell(0, 2, z);
	}

	/**
	 * Gets x coordinate
	 * 
	 * @return x coordinate
	 */
	public double getX() {
		return getCell(0, 0);
	}

	/**
	 * Gets y coordinate
	 * 
	 * @return y coordinate
	 */
	public double getY() {
		return getCell(0, 1);
	}

	/**
	 * Gets z coordinate
	 * 
	 * @return z coordinate
	 */
	public double getZ() {
		return getCell(0, 2);
	}

	/**
	 * Gets homogeneous coordinate
	 * 
	 * @return homogeneous coordinate
	 */
	public double getW() {
		return getCell(0, 3);
	}

	/**
	 * Sets homogeneous coordinate
	 * 
	 * @param w
	 *            homogeneous coordinate
	 */
	public void setW(double w) {
		setCell(0, 3, w);
	}

	public Point mult(double value) {
		Point result = new Point();
		super.mult(value, result);
		return result;
	}

	public Point add(Point a) {
		Point result = new Point();
		super.add(a, result);
		return result;
	}

	/**
	 * Returns vector product of 2 vectors
	 * 
	 * @param a
	 *            another vector
	 * @return vector product
	 */
	public Point vectorProduct(Point a) {
		Point result = new Point();
		result.setZ(getX() * a.getY() - getY() * a.getX());
		result.setY(getZ() * a.getX() - getX() * a.getZ());
		result.setX(getY() * a.getZ() - getZ() * a.getY());
		return result;
	}

	/**
	 * Returns length of the vector
	 * 
	 * @return length of the vector
	 */
	public double length() {
		return Math.sqrt(getX() * getX() + getY() * getY() + getZ() * getZ());
	}
}
