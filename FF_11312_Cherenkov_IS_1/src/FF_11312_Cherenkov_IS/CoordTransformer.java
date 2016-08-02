package FF_11312_Cherenkov_IS;

/**
 * This class is used to transfer coordinates between image and function map
 * axes.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class CoordTransformer {

	private double deltaX, deltaY;
	private int imageXmax, imageYmax;
	private double xmin, ymin, xmax, ymax;

	/**
	 * Sets up transformer to convert coordinates betwenn given image an
	 * function map
	 * 
	 * @param map
	 *            function map
	 * @param image
	 *            image
	 */
	public void init(FuncMap map, BufferedImage image) {
		imageXmax = image.getWidth();
		imageYmax = image.getHeight();
		xmin = map.getXmin();
		xmax = map.getXmax();
		ymin = map.getYmin();
		ymax = map.getYmax();
		deltaX = (xmax - xmin) / imageXmax;
		deltaY = (ymax - ymin) / imageYmax;
	}

	/**
	 * Converts X coordinate from function map axes to image axes.
	 * 
	 * @param x
	 *            X coordinate in function map axes
	 * @return X coordinate in image axes
	 */
	public int imageX(double x) {
		return (int) ((x - xmin) / deltaX + 0.5);
	}

	/**
	 * Converts Y coordinate from function map axes to image axes.
	 * 
	 * @param y
	 *            Y coordinate in function map axes
	 * @return Y coordinate in image axes
	 */
	public int imageY(double y) {
		return (int) ((ymax - y) / deltaY + 0.5);
	}

	/**
	 * Converts X coordinate from image axes to function map axes.
	 * 
	 * @param x
	 *            X coordinate in image axes
	 * @return X coordinate in function map axes
	 */
	public double funcX(int x) {
		return x * deltaX + xmin;
	}

	/**
	 * Converts Y coordinate from image axes to function map axes.
	 * 
	 * @param y
	 *            Y coordinate in image axes
	 * @return Y coordinate in function map axes
	 */
	public double funcY(int y) {
		return ymax - y * deltaY;
	}
}
