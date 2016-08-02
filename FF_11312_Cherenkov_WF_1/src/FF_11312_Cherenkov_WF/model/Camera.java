package FF_11312_Cherenkov_WF.model;

import java.util.Observable;
import java.util.Observer;

import FF_11312_Cherenkov_WF.matrix.Point;
import FF_11312_Cherenkov_WF.matrix.transformation.Transformation;
import FF_11312_Cherenkov_WF.matrix.transformation.TransitionTransform;

/**
 * This class represents a camera with its position and axes
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Camera extends Observable implements Observer {
	private static final Point eye;
	private static final Point ref;
	private static final Point up;
	private static final Transformation VCS;

	private Parameters params;
	private Transformation mk;

	static {
		eye = new Point(10, 0, 0);
		ref = new Point(-10, 0, 0);
		up = new Point(0, 1, 0);

		Point k = eye.add(ref.mult(-1));
		k.multInPlace(1. / k.length());
		Point i = up.vectorProduct(k);
		i.multInPlace(1. / i.length());
		Point j = k.vectorProduct(i);
		j.multInPlace(1. / j.length());

		double d[][] = { { i.getX(), i.getY(), i.getZ(), 0 },
				{ j.getX(), j.getY(), j.getZ(), 0 },
				{ k.getX(), k.getY(), k.getZ(), 0 }, { 0, 0, 0, 1 } };
		VCS = new Transformation(d).mult(new TransitionTransform(ref.mult(-1)));
	}

	/**
	 * Default constructor
	 * 
	 * @param params
	 *            rendering parameters
	 */
	public Camera(Parameters params) {
		super();
		this.params = params;
		params.addObserver(this);
		compile();
	}

	/**
	 * Compute matrix for transferring model coordinates to half-cube
	 */
	public void compile() {
		double zn = params.getZn();
		double zf = params.getZf();
		double sw = params.getSw();
		double sh = params.getSh();

		double d[][] = { { 2 * zn / sw, 0, 0, 0 }, { 0, 2 * zn / sh, 0, 0 },
				{ 0, 0, zf / (zf - zn), -zn * zf / (zf - zn) }, { 0, 0, 1, 0 } };
		Transformation proj = new Transformation(d);
		mk = proj.mult(VCS);
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets the transformation from model coordinates to coordinates of visible
	 * volume
	 * 
	 * @return transformation
	 */
	public Transformation getTransform() {
		return mk;
	}

	@Override
	public void update(Observable o, Object arg) {
		compile();
	}
}
