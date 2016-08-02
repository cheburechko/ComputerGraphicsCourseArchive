package FF_11312_Cherenkov_PR.model;

import java.util.Observable;
import java.util.Observer;

import FF_11312_Cherenkov_PR.matrix.Point;

/**
 * Triangle
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Triangle implements Comparable<Triangle>, Observer {
	private Vertex vertices[];
	private double z[];
	private double avgz;
	private int material;

	/**
	 * Default constructor
	 * 
	 * @param p1
	 *            first vertex
	 * @param p2
	 *            second vertex
	 * @param p3
	 *            third vertex
	 * @param material
	 *            surface material facing camera
	 */
	public Triangle(Vertex p1, Vertex p2, Vertex p3, int material) {
		super();
		vertices = new Vertex[3];
		vertices[0] = p1;
		vertices[1] = p2;
		vertices[2] = p3;
		z = new double[3];
		for (int i = 0; i < 3; i++)
			vertices[i].addObserver(this);
		update(p1, null);

		this.material = material;
	}

	/**
	 * Get point
	 * 
	 * @param i
	 *            point index
	 * @return point
	 */
	public Point get(int i) {
		return vertices[i].getPoint();
	}

	/**
	 * Get vertex
	 * 
	 * @param i
	 *            vertex index
	 * @return vertex
	 */
	public Vertex getVertex(int i) {
		return vertices[i];
	}

	/**
	 * Get material
	 * 
	 * @return material (0 - front, 1 - back)
	 */
	public int getMaterial() {
		return material;
	}

	@Override
	public int compareTo(Triangle o) {
		if (avgz < o.avgz)
			return 1;
		else
			return -1;

	}

	@Override
	public void update(Observable o, Object arg) {
		avgz = 0;
		for (int i = 0; i < 3; i++)
			avgz += vertices[i].getPoint().getZ();
		avgz /= 3;
	}
}
