package FF_11312_Cherenkov_IS;

/**
 * This class draws grid from FuncMap on the BigImage as a set of points
 * 
 * @author Pavel Cherenkov
 * 
 */
public class GridDrawer {
	private Palette color;

	/**
	 * Defaul constructor
	 * 
	 * @param color
	 *            color of the grid points
	 */
	public GridDrawer(Palette color) {
		this.color = color;
	}

	/**
	 * Draws grid on the image
	 * 
	 * @param map
	 *            function map whose grid is drawn
	 * @param image
	 *            destination drawing buffer
	 */
	public void draw(FuncMap map, BufferedImage image) {
		int xgrid = map.getXgrid();
		int ygrid = map.getYgrid();

		double xmin = map.getXmin();
		double xmax = map.getXmax();
		double ymin = map.getYmin();
		double ymax = map.getYmax();

		int imageXmax = image.getWidth();
		int imageYmax = image.getHeight();

		double deltaX = (imageXmax - 1) / (xmax - xmin);
		double deltaY = (imageYmax - 1) / (ymax - ymin);
		double coords[] = new double[2];

		for (int y = 0; y < ygrid; y++) {
			for (int x = 0; x < xgrid; x++) {
				map.transformGridToReal(x, y, coords);
				image.setRGB((int) ((coords[0] - xmin) * deltaX + 0.5),
						(int) ((coords[1] - ymin) * deltaY + 0.5),
						color.getColor(0));
			}
		}
	}
}
