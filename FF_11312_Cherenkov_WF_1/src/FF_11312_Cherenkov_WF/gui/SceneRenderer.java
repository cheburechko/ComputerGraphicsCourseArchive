package FF_11312_Cherenkov_WF.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import FF_11312_Cherenkov_WF.coords.ViewCoordTransformer;
import FF_11312_Cherenkov_WF.matrix.Point;
import FF_11312_Cherenkov_WF.matrix.transformation.Transformation;
import FF_11312_Cherenkov_WF.model.Camera;
import FF_11312_Cherenkov_WF.model.Scene;
import FF_11312_Cherenkov_WF.model.Segment;

/**
 * This class renders 3D scene on the screen
 * 
 * @author Pavel Cherenkov
 * 
 */
public class SceneRenderer {
	private static final double minx = -1;
	private static final double miny = -1;
	private static final double minz = 0;
	private static final double maxx = 1;
	private static final double maxy = 1;
	private static final double maxz = 1;

	private ViewCoordTransformer trans;

	/**
	 * Default constructor
	 */
	public SceneRenderer() {
		trans = new ViewCoordTransformer();
	}

	/**
	 * Clips the segment
	 * 
	 * @param given
	 *            segment
	 * @return false is segment does not lie in visible volume, true otherwise
	 */
	private boolean clipSegment(Segment s) {
		if (s.maxX() <= maxx && s.minX() >= minx && s.minY() >= miny
				&& s.maxY() <= maxy && s.minZ() >= minz && s.maxZ() <= maxz)
			return true;
		// Find point inside the box
		Point inside = s.getFirstPoint();
		Point outside = s.getSecondPoint();
		if (inside.getX() > maxx || inside.getX() < minx
				|| inside.getY() > maxy || inside.getY() < miny
				|| inside.getZ() > maxz || inside.getZ() < minz) {
			inside = s.getSecondPoint();
			if (inside.getX() > maxx || inside.getX() < minx
					|| inside.getY() > maxy || inside.getY() < miny
					|| inside.getZ() > maxz || inside.getZ() < minz)
				return false;
			outside = s.getFirstPoint();
		}
		// Determine the plane with which our segment intersects
		Point delta = outside.add(inside.mult(-1));

		double tx = (maxx - inside.getX()) / delta.getX();
		if (tx < 0)
			tx = (minx - inside.getX()) / delta.getX();

		double ty = (maxy - inside.getY()) / delta.getY();
		if (ty < 0)
			ty = (miny - inside.getY()) / delta.getY();

		double tz = (maxz - inside.getZ()) / delta.getZ();
		if (tz < 0)
			tz = (minz - inside.getZ()) / delta.getZ();

		double t = Math.min(Math.min(tx, ty), tz);
		outside.addInPlace(delta.mult(t - 1));
		return true;
	}

	/**
	 * Renders 3D scene on screen with given camera
	 * 
	 * @param scene
	 *            3D scene
	 * @param camera
	 *            camera
	 * @param image
	 *            screen area
	 * @param margin
	 *            margin on the screen
	 */
	public void render(Scene scene, Camera camera, BigImage image, int margin) {
		trans.init(image, margin);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setBackground(Color.WHITE);
		g.setColor(Color.BLACK);

		g.clearRect(0, 0, image.getWidth(), image.getHeight());
		g.drawRect(margin, margin, image.getWidth() - 2 * margin,
				image.getHeight() - 2 * margin);

		ArrayList<Segment> segments = scene.getSegments();
		if (segments.isEmpty())
			return;
		Transformation m = camera.getTransform()
				.mult(scene.getTransformation());

		for (Segment s : segments) {
			// Transform to half-cube
			Point p1 = m.mult(s.getFirstPoint());
			p1.multInPlace(1. / p1.getW());
			Point p2 = m.mult(s.getSecondPoint());
			p2.multInPlace(1. / p2.getW());

			Segment news = new Segment(p1, p2);
			// Clip and render
			if (clipSegment(news))
				g.drawLine(trans.imageX(p1.getX()), trans.imageY(p1.getY()),
						trans.imageX(p2.getX()), trans.imageY(p2.getY()));
		}

	}
}
