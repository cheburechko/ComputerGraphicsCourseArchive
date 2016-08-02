package FF_11312_Cherenkov_WF.coords;

/**
 * Helper class for transfering coordinates between image and model axes
 * 
 * @author Pavel Cherenkov
 * 
 */
public class CoordTransformer {

	private double deltaX;
	private double deltaY;

	private int imageXmin;
	private int imageYmin;
	private int imageXmax;
	private int imageYmax;

	private double xmin;
	private double ymin;
	private double xmax;
	private double ymax;

	/**
	 * Initialize transformer
	 * 
	 * @param imageXmin
	 *            minimal x on image
	 * @param imageYmin
	 *            minimal y on image
	 * @param imageXmax
	 *            maximal x on image
	 * @param imageYmax
	 *            maximal y on image
	 * @param xmin
	 *            minimal x in model
	 * @param ymin
	 *            minimal y in model
	 * @param xmax
	 *            maximal x in model
	 * @param ymax
	 *            maximal y in model
	 */
	public void init(int imageXmin, int imageYmin, int imageXmax,
			int imageYmax, double xmin, double ymin, double xmax, double ymax) {
		this.imageXmin = imageXmin;
		this.imageYmin = imageYmin;
		this.imageXmax = imageXmax;
		this.imageYmax = imageYmax;
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
		deltaX = (xmax - xmin) / (imageXmax - imageXmin);
		deltaY = (ymax - ymin) / (imageYmax - imageYmin);
	}

	/**
	 * Default constructor
	 */
	public CoordTransformer() {
		super();
	}

	/**
	 * Converts X coordinate from model axes to image axes.
	 * 
	 * @param x
	 *            X coordinate in model axes
	 * @return X coordinate in image axes
	 */
	public int imageX(double x) {
		return (int) ((x - xmin) / deltaX + 0.5) + imageXmin;
	}

	/**
	 * Converts Y coordinate from model axes to image axes.
	 * 
	 * @param y
	 *            Y coordinate in model axes
	 * @return Y coordinate in image axes
	 */
	public int imageY(double y) {
		return (int) ((ymax - y) / deltaY + 0.5) + imageYmin;
	}

	/**
	 * Converts X coordinate from image axes to model axes.
	 * 
	 * @param x
	 *            X coordinate in image axes
	 * @return X coordinate in model axes
	 */
	public double funcX(int x) {
		return (x - imageXmin) * deltaX + xmin;
	}

	/**
	 * Converts Y coordinate from image axes to model axes.
	 * 
	 * @param y
	 *            Y coordinate in image axes
	 * @return Y coordinate in model axes
	 */
	public double funcY(int y) {
		return ymax - (y - imageYmin) * deltaY;
	}

}