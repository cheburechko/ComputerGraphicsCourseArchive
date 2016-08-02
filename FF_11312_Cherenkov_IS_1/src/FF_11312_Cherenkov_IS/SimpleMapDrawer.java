package FF_11312_Cherenkov_IS;

/**
 * This class draws function color map with constant colors between function
 * limiting levels.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class SimpleMapDrawer extends MapDrawer {
	/**
	 * Default constructor
	 * 
	 * @param palette
	 *            color palette
	 */
	public SimpleMapDrawer(Palette palette) {
		super(palette);
	}

	/**
	 * Draws a simple color map on the image
	 * 
	 * @param map
	 *            function map
	 * @param image
	 *            destination buffer
	 */
	@Override
	public void draw(FuncMap map, BufferedImage image) {
		trans.init(map, image);
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int level = map.getLevel(trans.funcX(x), trans.funcY(y));
				image.setRGB(x, y, palette.getColor(level));
			}
		}
	}
}
