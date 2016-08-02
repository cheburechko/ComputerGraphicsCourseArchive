package FF_11312_Cherenkov_WF.matrix.transformation;

import FF_11312_Cherenkov_WF.matrix.Point;

/**
 * This class represents rotation of a vector with euler angles
 * 
 * @author Pavel Cherenkov
 * 
 */
public class EulerRotationTransform extends Transformation {
	/**
	 * Default constructor
	 * 
	 * @param ex
	 *            angle between x axis and N - intersection between old and new
	 *            xy plane
	 * @param ey
	 *            angle between old and new z axes
	 * @param ez
	 *            angle between N and new x axis
	 */
	public EulerRotationTransform(double ex, double ey, double ez) {
		super();
		Point N = new Point(Math.cos(ex), Math.sin(ex), 0);
		Point z = new Point(0, 0, 1);
		Point newz = N.vectorProduct(z).mult(Math.sin(ey))
				.add(z.mult(Math.cos(ey)));

		Transformation t1 = new AxisRotationTransform(z, ex);
		Transformation t2 = new AxisRotationTransform(N, ey);
		Transformation t3 = new AxisRotationTransform(newz, ez);
		addInPlace(t3.mult(t2.mult(t1)));
	}
}
