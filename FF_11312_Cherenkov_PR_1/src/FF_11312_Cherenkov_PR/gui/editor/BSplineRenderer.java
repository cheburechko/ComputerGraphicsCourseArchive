package FF_11312_Cherenkov_PR.gui.editor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import FF_11312_Cherenkov_PR.coords.SplineCoordTransformer;
import FF_11312_Cherenkov_PR.gui.BigImage;
import FF_11312_Cherenkov_PR.matrix.Point;
import FF_11312_Cherenkov_PR.model.BSpline;
import FF_11312_Cherenkov_PR.model.Vertex;

/**
 * This class renders bspline on the image.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class BSplineRenderer {
	private double minz, maxz, minx, maxx;
	private SplineCoordTransformer transform;
	public static final int pSize = 4;

	/**
	 * Get minimal z of the rendered area
	 * 
	 * @return minimal z
	 */
	public double getMinz() {
		return minz;
	}

	/**
	 * Set minimal z of the rendered area
	 * 
	 * @param minz
	 *            minimal z
	 */
	public void setMinz(double minz) {
		this.minz = minz;
	}

	/**
	 * Get maximal z of the rendered area
	 * 
	 * @return maximal z
	 */
	public double getMaxz() {
		return maxz;
	}

	/**
	 * Set maximal z of the rendered area
	 * 
	 * @param maxz
	 *            maximal z
	 */
	public void setMaxz(double maxz) {
		this.maxz = maxz;
	}

	/**
	 * Get minimal x of the rendered area
	 * 
	 * @return minimal x
	 */
	public double getMinx() {
		return minx;
	}

	/**
	 * Set minimal x of the rendered area
	 * 
	 * @param minx
	 *            minimal x
	 */
	public void setMinx(double minx) {
		this.minx = minx;
	}

	/**
	 * Get maximal x of the rendered area
	 * 
	 * @return mimal x
	 */
	public double getMaxx() {
		return maxx;
	}

	/**
	 * Set maximal x of the rendered area
	 * 
	 * @param maxx
	 *            maximal x
	 */
	public void setMaxx(double maxx) {
		this.maxx = maxx;
	}

	/**
	 * Default constructor
	 * 
	 * @param minz
	 *            minimal z of the rendered area
	 * @param maxz
	 *            maximal z of the rendered area
	 * @param minx
	 *            minimal x of the rendered area
	 * @param maxx
	 *            maximal x of the rendered area
	 */
	public BSplineRenderer(double minz, double maxz, double minx, double maxx) {
		super();
		this.minz = minz;
		this.maxz = maxz;
		this.minx = minx;
		this.maxx = maxx;
		transform = new SplineCoordTransformer();
	}

	/**
	 * Returns coordinate transformer
	 * 
	 * @return transformer
	 */
	public SplineCoordTransformer getTransform() {
		return transform;
	}

	/**
	 * Draws bspline, axes and point markers on the image
	 * 
	 * @param spline
	 *            bspline
	 * @param image
	 *            destination image
	 */
	public void draw(BSpline spline, BigImage image) {
		Graphics2D g = (Graphics2D) image.getGraphics();

		g.setBackground(Color.WHITE);
		g.setColor(Color.BLACK);
		g.clearRect(0, 0, image.getWidth(), image.getHeight());

		// draw axes
		g.drawLine(transform.imageX(0), 0, transform.imageX(0),
				image.getHeight());
		g.drawLine(0, transform.imageY(0), image.getWidth(),
				transform.imageY(0));

		// draw spline
		Point lastPoint = null;
		ArrayList<Vertex> points = spline.getSplinePoints();
		for (Vertex p : points) {
			if (lastPoint != null)
				g.drawLine(transform.imageX(lastPoint.getX()),
						transform.imageY(lastPoint.getZ()),
						transform.imageX(p.getPoint().getX()),
						transform.imageY(p.getPoint().getZ()));
			lastPoint = p.getPoint();
		}

		// draw points
		for (int i = 0; i < spline.getPointCount(); i++) {
			Point p = spline.getPoint(i);
			g.drawRect(transform.imageX(p.getX()) - pSize,
					transform.imageY(p.getZ()) - pSize, pSize * 2 + 1,
					pSize * 2 + 1);
		}
	}
}
