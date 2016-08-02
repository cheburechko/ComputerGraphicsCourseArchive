package FF_11312_Cherenkov_IS;

/**
 * This class is used draw function color map with interpolation of color
 * between limiting levels.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class InterpolationMapDrawer extends MapDrawer {
	private int newColor[];
	private double cellCoords[];

	/**
	 * Default constructor
	 * 
	 * @param palette
	 *            color palette
	 */
	public InterpolationMapDrawer(Palette palette) {
		super(palette);
		newColor = new int[3];
		cellCoords = new double[4];
	}

	/**
	 * Gets an interpolated color based on function value at given point
	 * 
	 * @param map
	 *            function map
	 * @param x
	 *            x coordinate in function domain
	 * @param y
	 *            y coordinate in function domain
	 * @param newColor
	 *            output array of color components (size must be at least 3)
	 */
	protected void interpolateColor(FuncMap map, double x, double y,
			int[] newColor) {
		int level = map.getLevel(x, y);
		if (level == 0) {
			int[] color1 = palette.getColorComponents(level);
			for (int i = 0; i < 3; i++)
				newColor[i] = color1[i];
			return;
		}

		double value1 = map.getLevelValue(level - 1);
		int[] color1 = palette.getColorComponents(level - 1);

		if (level == map.getLevels()) {
			for (int i = 0; i < 3; i++)
				newColor[i] = color1[i];
			return;
		}

		double value2 = map.getValue(x, y);
		double value3 = map.getLevelValue(level);

		int color2[] = palette.getColorComponents(level);
		double dist = (value2 - value1) / (value3 - value1);
		for (int i = 0; i < 3; i++) {
			newColor[i] = (int) (color1[i] * (1 - dist) + color2[i] * dist + 0.5);
		}

	}

	/**
	 * Draws a color map of a function
	 * 
	 * @param map
	 *            function map
	 * @param image
	 *            destination drawing buffer
	 */
	@Override
	public void draw(FuncMap map, BufferedImage image) {
		trans.init(map, image);
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				interpolateColor(map, trans.funcX(x), trans.funcY(y), newColor);

				image.setRGB(x, y, Palette.getRGB(newColor));
			}
		}
	}
}
