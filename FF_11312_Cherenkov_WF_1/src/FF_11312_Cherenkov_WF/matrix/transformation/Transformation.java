package FF_11312_Cherenkov_WF.matrix.transformation;

import FF_11312_Cherenkov_WF.matrix.Matrix;
import FF_11312_Cherenkov_WF.matrix.Point;

/**
 * This class represents transformation that can be applied to points It is a
 * 4x4 matrix
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Transformation extends Matrix {

	/**
	 * Default constructor
	 */
	public Transformation() {
		super(4, 4);
	}

	/**
	 * Default constructor with initial values
	 * 
	 * @param values
	 *            initial values (should have 4x4 size)
	 */
	public Transformation(double values[][]) {
		super(4, 4, values);
	}

	/**
	 * Combines this transformation with another, returns new one
	 * 
	 * @param a
	 *            another transformation
	 * @return resulting transformation
	 */
	public Transformation mult(Transformation a) {
		Transformation result = new Transformation();
		super.mult(a, result);
		return result;
	}

	/**
	 * Applies transformation to a point, returns resulting point
	 * 
	 * @param a
	 *            point
	 * @return resulting point
	 */
	public Point mult(Point a) {
		Point result = new Point();
		super.mult(a, result);
		return result;
	}

	public Transformation mult(double value) {
		Transformation result = new Transformation();
		super.mult(value, result);
		return result;
	}

	public Transformation add(Transformation a) {
		Transformation result = new Transformation();
		super.add(a, result);
		return result;
	}
}
