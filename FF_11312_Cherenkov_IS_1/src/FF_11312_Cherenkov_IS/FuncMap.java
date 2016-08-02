package FF_11312_Cherenkov_IS;

import java.util.Observable;

/**
 * This class represents a function map. It automatically computes limiting
 * levels and function values on given grid.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class FuncMap extends Observable {
	private double xmin, xmax, ymin, ymax;
	private int xgrid, ygrid;
	private int levels;
	private Function function;

	private double deltaX, deltaY;

	private double map[][];
	private double levelMap[];

	/**
	 * Gets minimal x
	 * 
	 * @return minimal x
	 */
	public double getXmin() {
		return xmin;
	}

	/**
	 * Gets maximal x
	 * 
	 * @return maximal x
	 */
	public double getXmax() {
		return xmax;
	}

	/**
	 * Gets minimal y
	 * 
	 * @return minimal y
	 */
	public double getYmin() {
		return ymin;
	}

	/**
	 * Gets maximal y
	 * 
	 * @return maximal y
	 */
	public double getYmax() {
		return ymax;
	}

	/**
	 * Gets amount of nodes on x axis
	 * 
	 * @return amount of nodes on x axis
	 */
	public int getXgrid() {
		return xgrid;
	}

	/**
	 * Gets amount of nodes on y axis
	 * 
	 * @return amount of nodes on y axis
	 */
	public int getYgrid() {
		return ygrid;
	}

	/**
	 * Gets amount of levels
	 * 
	 * @return amount of levels
	 */
	public int getLevels() {
		return levels;
	}

	/**
	 * Updates function map with new bounds and grid
	 * 
	 * @param xmin
	 *            minimal x
	 * @param xmax
	 *            maximal x
	 * @param ymin
	 *            minimal y
	 * @param ymax
	 *            maximal y
	 * @param xgrid
	 *            amount of nodes on x axis
	 * @param ygrid
	 *            amount of nodes on y axis
	 */
	public void setNewParameters(double xmin, double xmax, double ymin,
			double ymax, int xgrid, int ygrid) {
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
		this.xgrid = xgrid;
		this.ygrid = ygrid;
		compute();
	}

	/**
	 * Default constructor
	 * 
	 * @param xmin
	 *            minimal x
	 * @param xmax
	 *            maximal x
	 * @param ymin
	 *            minimal y
	 * @param ymax
	 *            maximal y
	 * @param xgrid
	 *            amount of nodes on x axis
	 * @param ygrid
	 *            amount of nodes on y axis
	 * @param levels
	 *            amount of levels
	 * @param function
	 *            function for which this map is computed
	 */
	public FuncMap(double xmin, double xmax, double ymin, double ymax,
			int xgrid, int ygrid, int levels, Function function) {
		this.levels = levels;
		this.function = function;
		setNewParameters(xmin, xmax, ymin, ymax, xgrid, ygrid);
	}

	/**
	 * Computes level values and values at nodes
	 */
	public void compute() {
		map = new double[ygrid][xgrid];
		deltaX = (xmax - xmin) / (xgrid - 1);
		deltaY = (ymax - ymin) / (ygrid - 1);
		double minVal = function.compute(xmin, ymin);
		double maxVal = minVal;
		for (int y = 0; y < ygrid; y++) {
			for (int x = 0; x < xgrid; x++) {
				map[y][x] = function.compute(deltaX * x + xmin, deltaY * y
						+ ymin);

				if (map[y][x] < minVal)
					minVal = map[y][x];

				if (map[y][x] > maxVal)
					maxVal = map[y][x];
			}
		}

		levelMap = new double[levels + 1];
		for (int i = 0; i <= levels; i++)
			levelMap[i] = minVal + (maxVal - minVal) / (levels + 1) * (i + 1);

		setChanged();
		notifyObservers();
	}

	/**
	 * Gets a value at particular node
	 * 
	 * @param x
	 *            x coordinate in grid axes
	 * @param y
	 *            y coordinate in grid axes
	 * @return value at requested node
	 */
	public double getGridValue(int x, int y) {
		return map[y][x];
	}

	/**
	 * Computes a value at given point
	 * 
	 * @param x
	 *            x coordinate in function domain
	 * @param y
	 *            y coordinate in function domain
	 * @return value at requested point
	 */
	public int getLevel(double x, double y) {
		double val = function.compute(x, y);
		for (int i = 0; i < levels; i++) {
			if (val < levelMap[i])
				return i;
		}
		return levels;
	}

	/**
	 * Gets value for particular limiting level
	 * 
	 * @param level
	 *            limiting level
	 * @return value for requested limiting level
	 */
	public double getLevelValue(int level) {
		return levelMap[level];
	}

	/**
	 * Helper function for converting node coordinates to function domain
	 * 
	 * @param x
	 *            x coordinate in grid axes
	 * @param y
	 *            x coordinate in grid axes
	 * @param coords
	 *            output coordinates in function domain (must have at least size
	 *            of 2)
	 */
	public void transformGridToReal(int x, int y, double[] coords) {
		coords[0] = xmin + deltaX * x;
		coords[1] = ymin + deltaY * y;
	}

	/**
	 * Get value at coordinates in function domain
	 * 
	 * @param x
	 *            x coordinate in function domain
	 * @param y
	 *            y coordinate in function domain
	 * @return value at given point
	 */
	public double getValue(double x, double y) {
		return function.compute(x, y);
	}
}
