package FF_11312_Cherenkov_WF.matrix.transformation;

import FF_11312_Cherenkov_WF.matrix.Point;

/**
 * This class represents rotation around given axis.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class AxisRotationTransform extends Transformation {

	/**
	 * Default constructor
	 * 
	 * @param axis
	 *            rotation axis
	 * @param angle
	 *            rotation angle in radians.
	 */
	public AxisRotationTransform(Point axis, double angle) {
		super();
		double a[][] = {
				{ axis.getX() * axis.getX(), axis.getY() * axis.getX(),
						axis.getZ() * axis.getX(), 0 },
				{ axis.getY() * axis.getX(), axis.getY() * axis.getY(),
						axis.getY() * axis.getZ(), 0 },
				{ axis.getZ() * axis.getX(), axis.getY() * axis.getZ(),
						axis.getZ() * axis.getZ(), 0 }, { 0, 0, 0, 1 } };
		Transformation vv = new Transformation(a);
		Transformation i = new ScaleTransform(1., 1., 1.);
		Transformation second = i.add(vv.mult(-1.)).mult(Math.cos(angle));
		double b[][] = { { 0, -axis.getZ(), axis.getY(), 0 },
				{ axis.getZ(), 0, -axis.getX(), 0 },
				{ -axis.getY(), axis.getX(), 0, 0 }, { 0, 0, 0, 1 } };
		Transformation vstar = new Transformation(b);
		Transformation third = vstar.mult(Math.sin(angle));
		addInPlace(vv.add(second).add(third));
		setCell(3, 3, 1.);
	}
}
