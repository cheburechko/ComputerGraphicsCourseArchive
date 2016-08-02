package FF_11312_Cherenkov_Span.model;

import java.awt.Point;

/**
 * A fill represented by starting point, color and connectivity
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Fill extends Drawable {

	private Point point;
	private int color;
	private int connectivity;

	/**
	 * Constructor with all needed parameters
	 * 
	 * @param p
	 *            starting point for fill
	 * @param color
	 *            0 - red, 1 - blue
	 * @param connectivity
	 *            4 or 8
	 */
	public Fill(Point p, int color, int connectivity) {
		this.point = p;
		this.color = color;
		this.connectivity = connectivity;
	}

	/**
	 * Get starting point
	 * 
	 * @return starting point
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * Set starting point
	 * 
	 * @param point
	 *            starting point
	 */
	public void setPoint(Point point) {
		this.point = point;
	}

	/**
	 * Get color
	 * 
	 * @return 0 - red, 1 - blue
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Set color
	 * 
	 * @param color
	 *            0 - red, 1 - blue
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * Get connectivity
	 * 
	 * @return 4 or 8
	 */
	public int getConnectivity() {
		return connectivity;
	}

	/**
	 * Set connectivity
	 * 
	 * @param connectivity
	 *            4 or 8
	 */
	public void setConnectivity(int connectivity) {
		this.connectivity = connectivity;
	}

	/**
	 * An implementation of Drawable.getType() method
	 * 
	 * @see Drawable#getType()
	 */
	@Override
	public DrawableType getType() {
		// TODO Auto-generated method stub
		return DrawableType.FILL;
	}

}
