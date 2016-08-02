package FF_11312_Cherenkov_WF.model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import FF_11312_Cherenkov_WF.matrix.Point;
import FF_11312_Cherenkov_WF.matrix.transformation.EulerRotationTransform;
import FF_11312_Cherenkov_WF.matrix.transformation.ScaleTransform;
import FF_11312_Cherenkov_WF.matrix.transformation.Transformation;
import FF_11312_Cherenkov_WF.matrix.transformation.TransitionTransform;

/**
 * This class represents the whole scene that must be rendered
 * 
 * @author Pavle Cherenkov
 * 
 */
public class Scene extends Observable implements Observer {
	private ArrayList<Segment> segments;
	private ArrayList<SolidOfRevolution> bodies;
	private ScaleTransform scale;
	private TransitionTransform transition;
	private Transformation rotation;
	private Transformation m1;
	private Point corner1, corner2;
	private static final Point sceneCorner1, sceneCorner2;

	static {
		sceneCorner1 = new Point(-1, -1, 0);
		sceneCorner2 = new Point(1, 1, 1);
	}

	/**
	 * Default constructor
	 */
	public Scene() {
		segments = new ArrayList<>();
		bodies = new ArrayList<>();
		corner1 = new Point(-1, -1, 0);
		corner2 = new Point(1, 1, 1);
		transition = new TransitionTransform(0, 0, 0);
		scale = new ScaleTransform(1, 1, 1);
		rotation = new ScaleTransform(1, 1, 1);
	}

	/**
	 * Adds new body to the scene
	 * 
	 * @param body
	 *            new body
	 */
	public void addBody(SolidOfRevolution body) {
		bodies.add(body);
		body.addObserver(this);
		compile();
	}

	/**
	 * Removes a body from the scene
	 * 
	 * @param i
	 *            index of the body
	 */
	public void removeBody(int i) {
		bodies.remove(i).deleteObserver(this);
		compile();
	}

	/**
	 * Gets a body from the scene
	 * 
	 * @param i
	 *            index of the body
	 * @return body
	 */
	public SolidOfRevolution getBody(int i) {
		return bodies.get(i);
	}

	/**
	 * Get amount of bodies in the scene
	 * 
	 * @return body count
	 */
	public int getBodyConut() {
		return bodies.size();
	}

	/**
	 * Rotates the scene with given transformation
	 * 
	 * @param t
	 *            rotation transformation
	 */
	public void rotate(EulerRotationTransform t) {
		rotation = t.mult(rotation);
		m1 = t.mult(m1);
		setChanged();
		notifyObservers();
	}

	/**
	 * Computes a scene transformation so that bounding box lies in
	 * [-1,1]x[-1,1]x[0,1] and is rotated to some applied angles.
	 */
	public void compile() {
		segments.clear();
		for (SolidOfRevolution s : bodies) {
			segments.addAll(s.getSegments());
		}
		if (segments.isEmpty()) {
			setChanged();
			notifyObservers();
			return;
		}

		// Get bounding box
		corner1 = new Point(segments.get(0).getMinCorner());
		corner2 = new Point(segments.get(0).getMaxCorner());
		for (Segment s : segments) {
			corner1.setX(Math.min(corner1.getX(), s.minX()));
			corner2.setX(Math.max(corner2.getX(), s.maxX()));
			corner1.setY(Math.min(corner1.getY(), s.minY()));
			corner2.setY(Math.max(corner2.getY(), s.minY()));
			corner1.setZ(Math.min(corner1.getZ(), s.minZ()));
			corner2.setZ(Math.max(corner2.getZ(), s.minZ()));
		}

		// Get transformations
		Point scales = corner2.add(corner1.mult(-1));
		scale.setScale(2. / scales.getX(), 2. / scales.getY(),
				1. / scales.getZ());

		Point transitions = sceneCorner1.add(scale.mult(corner1).mult(-1));
		transition.setTrans(transitions);

		m1 = rotation.mult(transition.mult(scale));

		setChanged();
		notifyObservers();
	}

	/**
	 * Get all of the segments in the scene
	 * 
	 * @return list of segments
	 */
	public ArrayList<Segment> getSegments() {
		return segments;
	}

	/**
	 * Gets transformation for the scene
	 * 
	 * @return transformation for the segments of the scene
	 */
	public Transformation getTransformation() {
		return m1;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		compile();
	}
}
