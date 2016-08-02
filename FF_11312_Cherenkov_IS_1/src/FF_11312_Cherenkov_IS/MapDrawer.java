package FF_11312_Cherenkov_IS;

/**
 * This is an abstract class for all classes that draw color maps.
 * 
 * @author Pavel Cherenkov
 * 
 */
public abstract class MapDrawer {

	protected Palette palette;
	protected CoordTransformer trans;

	/**
	 * Draws a color map of a function
	 * 
	 * @param map
	 *            function map
	 * @param image
	 *            destination drawing buffer
	 */
	public abstract void draw(FuncMap map, BufferedImage image);

	/**
	 * Default constructor
	 * 
	 * @param palette
	 *            color palette
	 */
	public MapDrawer(Palette palette) {
		this.palette = palette;
		trans = new CoordTransformer();
	}
}