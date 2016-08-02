package FF_11312_Cherenkov_WF.model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import FF_11312_Cherenkov_WF.matrix.Point;
import FF_11312_Cherenkov_WF.matrix.transformation.Transformation;

/**
 * This class represent a solid of the resolution formed by the rotation of the
 * spline around z axis
 * 
 * @author Pavel Cherenkov
 * 
 */
public class SolidOfRevolution extends Observable implements Observer {
	private BSpline spline;
	private Parameters params;

	private ArrayList<Segment> segments;

	/**
	 * Default constructor
	 * 
	 * @param spline
	 *            basis spline
	 */
	public SolidOfRevolution(BSpline spline) {
		super();
		this.spline = spline;
		segments = new ArrayList<>();
		this.params = spline.getParams();

		spline.addObserver(this);
		compile();
	}

	/**
	 * Gets the spline
	 * 
	 * @return basis spline
	 */
	public BSpline getSpline() {
		return spline;
	}

	/**
	 * Sets the spline
	 * 
	 * @param spline
	 *            spline
	 */
	public void setSpline(BSpline spline) {
		this.spline.deleteObserver(this);
		this.spline = spline;
		spline.addObserver(this);
		compile();
	}

	/**
	 * Gets rotation transformation around z axis
	 * 
	 * @param phi
	 *            rotation angle
	 * @return rotation transformation
	 */
	public Transformation getPhiTransformation(double phi) {
		double p[][] = { { Math.cos(phi), 0, 0, 0 },
				{ Math.sin(phi), 0, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		return new Transformation(p);
	}

	/**
	 * Computes all of the segments for this body
	 */
	public void compile() {
		ArrayList<Point> curve = spline.getSplinePoints();
		segments.clear();
		if (curve.isEmpty()) {
			setChanged();
			notifyObservers();
			return;
		}

		int tSteps = params.gettSteps();
		int scale = params.getScale();
		double minPhi = params.getMinPhi();
		double maxPhi = params.getMaxPhi();
		int phiSteps = params.getPhiSteps();

		Transformation t = getPhiTransformation(minPhi);
		Point lastPoint = null;

		double step = (maxPhi - minPhi) / phiSteps / scale;
		ArrayList<Point> oldCurve = new ArrayList<>();
		for (int k = 0; k <= tSteps; k++)
			oldCurve.add(t.mult(curve.get(k * scale)));

		for (int i = 0; i <= phiSteps; i++) {
			// build segments for phi = const
			for (int j = 0; j < tSteps; j++) {
				lastPoint = oldCurve.get(j);
				for (int k = 1; k < scale; k++) {
					Point newPoint = t.mult(curve.get(j * scale + k));
					segments.add(new Segment(lastPoint, newPoint));
					lastPoint = newPoint;
				}
				segments.add(new Segment(lastPoint, oldCurve.get(j + 1)));
			}

			// build segments for t = const
			if (i < phiSteps) {
				for (int j = 1; j <= scale; j++) {
					t = getPhiTransformation(minPhi + (i * scale + j) * step);
					ArrayList<Point> newCurve = new ArrayList<>();
					for (int k = 0; k <= tSteps; k++) {
						Point p = t.mult(curve.get(k * scale));
						segments.add(new Segment(p, oldCurve.get(k)));
						newCurve.add(p);
					}
					oldCurve = newCurve;
				}
			}
		}

		setChanged();
		notifyObservers();
	}

	/**
	 * Gets all of the body's segments
	 * 
	 * @return segments of the body
	 */
	public ArrayList<Segment> getSegments() {
		return segments;
	}

	@Override
	public void update(Observable o, Object arg) {
		compile();

	}

}
