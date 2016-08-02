package FF_11312_Cherenkov_PR.model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import FF_11312_Cherenkov_PR.matrix.Point;
import FF_11312_Cherenkov_PR.matrix.TPoint;
import FF_11312_Cherenkov_PR.matrix.transformation.Transformation;

/**
 * This class represents a bspline drawn in xz plane
 * 
 * @author Pavel Cherenkov
 * 
 */
public class BSpline extends Observable implements Observer {
	private ArrayList<Point> points;
	private ArrayList<Transformation> transformations;
	private Parameters params;
	private TPoint tvec, nvec;

	private static Transformation Ms, orthogonal;

	static {
		double d[][] = { { -1, 3, -3, 1 }, { 3, -6, 3, 0 }, { -3, 0, 3, 0 },
				{ 1, 4, 1, 0 } };
		Ms = new Transformation(d);
		Ms.multInPlace(1. / 6.);
		orthogonal = new Transformation(new double[][] { { 0, 0, 1, 0 },
				{ 0, 0, 0, 0 }, { -1, 0, 0, 0 }, { 0, 0, 0, 1 }, });
	}

	/**
	 * Returns rendering parameters
	 * 
	 * @return rendering parameters
	 */
	public Parameters getParams() {
		return params;
	}

	/**
	 * Sets rendering parameters
	 * 
	 * @param params
	 *            rendering parameters
	 */
	public void setParams(Parameters params) {
		this.params = params;
	}

	/**
	 * Default constructor
	 * 
	 * @param params
	 *            rendering parameters
	 */
	public BSpline(Parameters params) {
		points = new ArrayList<>();
		transformations = new ArrayList<>();
		this.params = params;
		params.addObserver(this);
		tvec = new TPoint();
		nvec = new TPoint();
	};

	/**
	 * Copy constructor
	 * 
	 * @param orig
	 *            original spline
	 */
	public BSpline(BSpline orig) {
		this(orig.params);
		for (Point p : orig.points) {
			points.add(new Point(p));
		}
		compile();
	}

	/**
	 * Get point of the spline
	 * 
	 * @param index
	 *            number of the point
	 * @return point
	 */
	public Point getPoint(int index) {
		return points.get(index);
	}

	/**
	 * Remove point from the spline
	 * 
	 * @param index
	 *            number of the point
	 */
	public void removePoint(int index) {
		points.remove(index).deleteObserver(this);
		compile();
	}

	/**
	 * Adds a new point to the spline at the end of the point list
	 * 
	 * @param p
	 *            new point
	 */
	public void addPoint(Point p) {
		p.addObserver(this);
		points.add(p);
		compile();
	}

	/**
	 * Deletes all points from the spline
	 */
	public void clearPoints() {
		points.clear();
		compile();
	}

	/**
	 * Gets amount of point in the spline
	 * 
	 * @return point count
	 */
	public int getPointCount() {
		return points.size();
	}

	/**
	 * Computes matrices which represent the spline
	 */
	public void compile() {
		transformations.clear();
		for (int i = 1; i < points.size() - 2; i++) {
			double m[][] = {
					{ points.get(i - 1).getX(), 0, points.get(i - 1).getZ(), 1 },
					{ points.get(i).getX(), 0, points.get(i).getZ(), 1 },
					{ points.get(i + 1).getX(), 0, points.get(i + 1).getZ(), 1 },
					{ points.get(i + 2).getX(), 0, points.get(i + 2).getZ(), 1 } };
			transformations.add(Ms.mult(new Transformation(m)));
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets the point of the spline at given parameter t
	 * 
	 * @param t
	 *            parameter t
	 * @return point on the spline
	 */
	public Vertex getSplinePoint(double t) {
		double realt = t * transformations.size();
		int index = (int) realt;
		if (index == transformations.size())
			index--;
		realt -= index;
		tvec.setX(realt * realt * realt);
		tvec.setY(realt * realt);
		tvec.setZ(realt);
		nvec.setX(3 * realt * realt);
		nvec.setY(2 * realt);
		nvec.setZ(1);
		nvec.setW(0);
		Point p = new Point(tvec.mult(transformations.get(index)));
		Point n = new Point(nvec.mult(transformations.get(index)).mult(
				orthogonal));
		n.multInPlace(1. / n.length());
		return new Vertex(p, n);
	}

	/**
	 * Gets point of the spline according to rendering parameters
	 * 
	 * @return list of points in the spline
	 */
	public ArrayList<Vertex> getSplinePoints() {
		if (transformations.isEmpty())
			return new ArrayList<>();
		double delta = (params.getMaxT() - params.getMinT())
				/ params.gettSteps();
		ArrayList<Vertex> result = new ArrayList<>();
		for (int i = 0; i <= params.gettSteps(); i++) {
			result.add(getSplinePoint(params.getMinT() + i * delta));
		}

		return result;
	}

	@Override
	public void update(Observable o, Object arg) {
		compile();
	}
}
