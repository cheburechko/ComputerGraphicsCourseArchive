package FF_11312_Cherenkov_PR.model;

import FF_11312_Cherenkov_PR.matrix.Point;
import FF_11312_Cherenkov_PR.matrix.transformation.Transformation;

/**
 * Surface of a 3D body
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Surface {
	private Vertex surface[][];
	private int n, m;
	private Point minP, maxP;

	/**
	 * Get point with surface minimal coordinates
	 * 
	 * @return point with surface minimal coordinates
	 */
	public Point getMinP() {
		return minP;
	}

	/**
	 * Get point with surface maximal coordinates
	 * 
	 * @return point with surface maximal coordinates
	 */
	public Point getMaxP() {
		return maxP;
	}

	/**
	 * Get size of the first dimension of the surface
	 * 
	 * @return size of the first dimension of the surface
	 */
	public int getN() {
		return n;
	}

	/**
	 * Get size of the second dimension of the surface
	 * 
	 * @return size of the second dimension of the surface
	 */
	public int getM() {
		return m;
	}

	/**
	 * Default constructor
	 * 
	 * @param n
	 *            size of the first dimension
	 * @param m
	 *            size of the second dimension
	 */
	public Surface(int n, int m) {
		this.n = n;
		this.m = m;
		surface = new Vertex[n][m];
	}

	/**
	 * Sets a vertex at given position
	 * 
	 * @param x
	 *            position in first dimension
	 * @param y
	 *            position in second dimension
	 * @param v
	 *            new vertex
	 */
	public void set(int x, int y, Vertex v) {
		surface[x][y] = v;
		if (minP == null) {
			minP = new Point(v.getPoint());
			maxP = new Point(v.getPoint());
		} else {
			minP.setX(Math.min(v.getPoint().getX(), minP.getX()));
			minP.setY(Math.min(v.getPoint().getY(), minP.getY()));
			minP.setZ(Math.min(v.getPoint().getZ(), minP.getZ()));
			maxP.setX(Math.max(v.getPoint().getX(), maxP.getX()));
			maxP.setY(Math.max(v.getPoint().getY(), maxP.getY()));
			maxP.setZ(Math.max(v.getPoint().getZ(), maxP.getZ()));
		}
	}

	/**
	 * Get a vertex at given position
	 * 
	 * @param x
	 *            position in first dimension
	 * @param y
	 *            position in second dimension
	 * @return vertex
	 */
	public Vertex get(int x, int y) {
		return surface[x][y];
	}

	/**
	 * Apply transformation to all vertices, return new surface
	 * 
	 * @param pt
	 *            point transformation
	 * @param nt
	 *            normal transformation
	 * @return new surface with transformed vertices
	 */
	public Surface apply(Transformation pt, Transformation nt) {
		Surface result = new Surface(n, m);
		for (int x = 0; x < n; x++)
			for (int y = 0; y < m; y++)
				result.surface[x][y] = surface[x][y].applyAndCopy(pt, nt);
		return result;
	}

	/**
	 * Apply transformation to all vertices in place
	 * 
	 * @param pt
	 *            point transformation
	 * @param nt
	 *            normal transformation
	 */
	public void applyInPlace(Transformation pt, Transformation nt) {
		for (int x = 0; x < n; x++)
			for (int y = 0; y < m; y++)
				surface[x][y].apply(pt, nt);
	}

}
