package FF_11312_Cherenkov_IS;

/**
 * This class is used to draw function color map using Floyd-Steinberg dithering
 * algorithm to render interpolated colors.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class DitheringMapDrawer extends InterpolationMapDrawer {
	private int[] newColor;

	/**
	 * Default constructor
	 * 
	 * @param palette
	 *            color palette
	 */
	public DitheringMapDrawer(Palette palette) {
		super(palette);
		newColor = new int[3];
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
		int imageXmax = image.getWidth();
		int imageYmax = image.getHeight();
		trans.init(map, image);
		int levels = map.getLevels();

		int errors1[][] = new int[imageXmax][3];
		int errors2[][] = new int[imageXmax][3];
		int nextErrors[][] = errors1;
		int thisErrors[][] = errors2;
		int distances[] = new int[levels + 1];

		for (int x = 0; x < imageXmax; x++) {
			for (int i = 0; i < 3; i++)
				thisErrors[x][i] = 0;
		}

		int nextError[] = new int[3];
		int newColor[] = new int[3];

		for (int y = 0; y < imageYmax; y++) {
			nextError[0] = 0;
			nextError[1] = 0;
			nextError[2] = 0;
			for (int x = 0; x < imageXmax; x++) {
				interpolateColor(map, trans.funcX(x), trans.funcY(y), newColor);

				for (int i = 0; i < 3; i++) {
					// Add errors
					newColor[i] += (7 * nextError[i] + thisErrors[x][i]) / 16;

					// Compute distances for all colors
					for (int j = 0; j <= levels; j++) {
						distances[j] += (newColor[i] - palette
								.getColorComponents(j)[i])
								* (newColor[i] - palette.getColorComponents(j)[i]);
					}
				}

				// Get the closest color
				int closest = 0;
				int closestDist = distances[0];
				distances[0] = 0;
				for (int j = 1; j <= levels; j++) {
					if (closestDist > distances[j]) {
						closest = j;
						closestDist = distances[j];
					}
					distances[j] = 0;
				}

				image.setRGB(x, y, palette.getColor(closest));

				// Get errors
				for (int i = 0; i < 3; i++) {
					int delta = newColor[i]
							- palette.getColorComponents(closest)[i];
					if (x > 0)
						nextErrors[x - 1][i] += 3 * delta;
					nextErrors[x][i] += 5 * delta;
					if (x < imageXmax - 1)
						nextErrors[x + 1][i] += delta;
					nextError[i] = delta;
				}
			}

			// Swap and clear errors
			if (thisErrors.equals(errors2)) {
				thisErrors = errors1;
				nextErrors = errors2;
			} else {
				thisErrors = errors2;
				nextErrors = errors1;
			}

			for (int x = 0; x < imageXmax; x++) {
				for (int i = 0; i < 3; i++)
					nextErrors[x][i] = 0;
			}
		}
	}

}
