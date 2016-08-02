package FF_11312_Cherenkov_PR.rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.TreeSet;

import FF_11312_Cherenkov_PR.coords.ViewCoordTransformer;
import FF_11312_Cherenkov_PR.gui.BigImage;
import FF_11312_Cherenkov_PR.matrix.Point;
import FF_11312_Cherenkov_PR.matrix.transformation.ScaleTransform;
import FF_11312_Cherenkov_PR.model.Camera;
import FF_11312_Cherenkov_PR.model.Parameters;
import FF_11312_Cherenkov_PR.model.Scene;
import FF_11312_Cherenkov_PR.model.Surface;
import FF_11312_Cherenkov_PR.model.Triangle;
import FF_11312_Cherenkov_PR.model.Vertex;

/**
 * Renders polygons on screen
 * 
 * @author Pavel Cherenkov
 * 
 */
public class PolygonRenderer {
	private static final double minx = -1;
	private static final double miny = -1;
	private static final double minz = 0;
	private static final double maxx = 1;
	private static final double maxy = 1;
	private static final double maxz = 1;

	private ViewCoordTransformer trans;
	private LightTracer tracer;

	/**
	 * Default constructor
	 */
	public PolygonRenderer() {
		trans = new ViewCoordTransformer();
		tracer = new LightTracer();
	}

	/**
	 * Clips the triangle.
	 * 
	 * @param t
	 *            given triangle
	 * @return false if any vertex of triangle lies outside visible volume, true
	 *         otherwise
	 */
	private boolean clipTriangle(Triangle t) {
		for (int i = 0; i < 3; i++) {
			Point p = t.get(i);
			if (p.getX() < minx || p.getX() > maxx || p.getY() < miny
					|| p.getY() > maxy || p.getZ() < minz || p.getZ() > maxz)
				return false;
		}
		return true;
	}

	/**
	 * Creates a triangle from 3 vertices, computes which material faces camera
	 * 
	 * @param v1
	 * @param v2
	 * @param v3
	 * @param polygons
	 */
	private void createTriangle(Vertex v1, Vertex v2, Vertex v3,
			ArrayList<Triangle> polygons) {
		Point p1 = v1.getPoint().mult(-1);
		Point surfNormal = v3.getPoint().add(p1)
				.vectorProduct(v2.getPoint().add(p1));
		Point avgNormal = v1.getNormal()
				.add(v2.getNormal().add(v3.getNormal()));
		int material;
		if (surfNormal.scalarProduct(avgNormal) < 0)
			surfNormal.multInPlace(-1);

		if (Camera.eye.add(p1).scalarProduct(surfNormal) > 0)
			material = 0;
		else
			material = 1;

		polygons.add(new Triangle(v1, v2, v3, material));
	}

	/**
	 * Draws triangle, interpolating vertices' colors
	 * 
	 * @param t
	 *            triangle to render
	 * @param image
	 *            target image
	 * @param wf
	 *            if true, encircles vertex with white border
	 */
	private void drawTriangle(Triangle t, BigImage image, boolean wf) {
		// Determine points position
		Vertex maxy = t.getVertex(0), miny = maxy, mid = miny;
		for (int i = 1; i < 3; i++) {
			Vertex p = t.getVertex(i);
			if (p.getPoint().getY() > maxy.getPoint().getY())
				maxy = p;
			if (p.getPoint().getY() < miny.getPoint().getY())
				miny = p;
		}
		for (int i = 0; i < 3; i++)
			if (t.getVertex(i) != miny && t.getVertex(i) != maxy) {
				mid = t.getVertex(i);
				break;
			}

		int offset = trans.imageY(maxy.getPoint().getY());
		int height = trans.imageY(miny.getPoint().getY())
				- trans.imageY(maxy.getPoint().getY());
		int[] border1 = new int[height + 1];
		int[] border2 = new int[height + 1];
		int[] leftBorder = border1;
		int[] rightBorder = border2;

		boolean swap = false;

		Point negMiny = miny.getPoint().mult(-1);
		if (mid.getPoint().add(negMiny)
				.vectorProduct(maxy.getPoint().add(negMiny)).getZ() < 0) {
			swap = true;
			leftBorder = border2;
			rightBorder = border1;
		}

		// drawLine(maxy.getPoint(), miny.getPoint(), border1, offset, swap);
		// drawLine(maxy.getPoint(), mid.getPoint(), border2, offset, !swap);
		// drawLine(mid.getPoint(), miny.getPoint(), border2, offset, !swap);

		Vertex midNeighbour = maxy;
		int midy = trans.imageY(mid.getPoint().getY());
		int color;
		int material = t.getMaterial();

		for (int y = 0; y <= height; y++) {
			if (y + offset == midy)
				midNeighbour = miny;

			double ka = (maxy.getPoint().getY() - trans.funcY(y + offset))
					/ (maxy.getPoint().getY() - miny.getPoint().getY());
			double kb = (mid.getPoint().getY() - trans.funcY(y + offset))
					/ (mid.getPoint().getY() - midNeighbour.getPoint().getY());
			if (ka < 0)
				ka = 0;
			if (ka > 1)
				ka = 1;
			if (kb < 0)
				kb = 0;
			if (kb > 1)
				kb = 1;

			double x1 = ka * miny.getPoint().getX() + (1 - ka)
					* maxy.getPoint().getX();
			double x2 = kb * midNeighbour.getPoint().getX() + (1 - kb)
					* mid.getPoint().getX();

			int minx = trans.imageX(x1);
			int maxx = trans.imageX(x2);
			if (minx > maxx) {
				int buf = maxx;
				maxx = minx;
				minx = buf;
			}
			for (int x = minx; x <= maxx; x++) {
				double kc = (trans.funcX(x) - x1) / (x2 - x1);
				if (kc < 0)
					kc = 0;
				if (kc > 1)
					kc = 1;
				color = 0;
				for (int col = 0; col < 3; col++) {
					color = color << 8;
					double Ia = ka * miny.getColor(material, col) + (1 - ka)
							* maxy.getColor(material, col);
					double Ib = kb * midNeighbour.getColor(material, col)
							+ (1 - kb) * mid.getColor(material, col);
					color += (int) (kc * Ib + (1 - kc) * Ia + 0.5);
				}

				image.setRGB(x, y + offset, color);

			}
		}

		if (wf) {
			image.getGraphics().setColor(Color.WHITE);
			image.getGraphics().drawLine(trans.imageX(maxy.getPoint().getX()),
					trans.imageY(maxy.getPoint().getY()),
					trans.imageX(miny.getPoint().getX()),
					trans.imageY(miny.getPoint().getY()));
			image.getGraphics().drawLine(trans.imageX(maxy.getPoint().getX()),
					trans.imageY(maxy.getPoint().getY()),
					trans.imageX(mid.getPoint().getX()),
					trans.imageY(mid.getPoint().getY()));
			image.getGraphics().drawLine(trans.imageX(mid.getPoint().getX()),
					trans.imageY(mid.getPoint().getY()),
					trans.imageX(miny.getPoint().getX()),
					trans.imageY(miny.getPoint().getY()));
		}
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
		tracer.computeLights(scene, camera);

		trans.init(image, margin);
		Graphics2D g = (Graphics2D) image.getGraphics();
		Parameters params = camera.getParams();

		g.setBackground(params.getBackground());
		g.setColor(new Color(~params.getBackground().getRGB()));

		g.clearRect(0, 0, image.getWidth(), image.getHeight());
		g.drawRect(margin, margin, image.getWidth() - 2 * margin,
				image.getHeight() - 2 * margin);

		ArrayList<Surface> surfaces = scene.getSurfaces();
		if (surfaces.isEmpty())
			return;

		TreeSet<Triangle> polygons = new TreeSet<>();
		for (Surface original : surfaces) {
			ArrayList<Triangle> unsorted_triangles = new ArrayList<>();
			Surface surface = original.apply(scene.getTransformation(),
					scene.getNormalTransformation());

			for (int i = 0; i < surface.getN() - 1; i++)
				for (int j = 0; j < surface.getM() - 1; j++) {
					createTriangle(surface.get(i, j), surface.get(i + 1, j),
							surface.get(i, j + 1), unsorted_triangles);
					createTriangle(surface.get(i + 1, j + 1),
							surface.get(i + 1, j), surface.get(i, j + 1),
							unsorted_triangles);
				}

			surface.applyInPlace(camera.getTransform(), new ScaleTransform(1.,
					1., 1.));

			for (int i = 0; i < surface.getN(); i++)
				for (int j = 0; j < surface.getM(); j++) {
					Point p = surface.get(i, j).getPoint();
					p.multInPlace(1. / p.getW());
				}

			polygons.addAll(unsorted_triangles);
			int width = 4;

			int x[] = new int[3];
			int y[] = new int[3];
			Polygon p = new Polygon(x, y, 3);
			p.xpoints = x;
			p.ypoints = y;
			p.npoints = 3;

			for (Triangle t : polygons) {
				if (clipTriangle(t))
					drawTriangle(t, image, params.isWireframe());
			}

		}

	}
}
