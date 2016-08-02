package FF_11312_Cherenkov_IS;

import java.awt.Color;
import java.util.Observable;

/**
 * This class is used to work with collections of colors.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Palette extends Observable {

	private int colors[][];
	private int colorNum;

	/**
	 * Gets amount of colors
	 * 
	 * @return amount of colors
	 */
	public int getColorNum() {
		return colorNum;
	}

	/**
	 * Sets amount of colors
	 * 
	 * @param colorNum
	 *            amount of colors
	 * 
	 */
	public void setColorNum(int colorNum) {
		this.colorNum = colorNum;
		colors = new int[colorNum][3];
	}

	/**
	 * Default constructor
	 * 
	 * @param colorNum
	 *            amount of colors
	 */
	public Palette(int colorNum) {
		setColorNum(colorNum);
	}

	/**
	 * Sets new color
	 * 
	 * @param color
	 *            color index
	 * @param r
	 *            red component
	 * @param g
	 *            green component
	 * @param b
	 *            blue component
	 */
	public void setColor(int color, int r, int g, int b) {
		colors[color][0] = b;
		colors[color][1] = g;
		colors[color][2] = r;
		setChanged();
		notifyObservers();
	}

	/**
	 * Sets new color
	 * 
	 * @param color
	 *            color index
	 * @param c
	 *            color object
	 */
	public void setColor(int color, Color c) {
		colors[color][0] = c.getBlue();
		colors[color][1] = c.getGreen();
		colors[color][2] = c.getRed();
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets color components
	 * 
	 * @param color
	 *            color index
	 * @return color components array: 2 - red, 1 - green, 0 - blue
	 */
	public int[] getColorComponents(int color) {
		return colors[color];
	}

	/**
	 * Gets color code
	 * 
	 * @param color
	 *            color index
	 * @return color code
	 */
	public int getColor(int color) {
		return getRGB(colors[color]);
	}

	/**
	 * Gets color object
	 * 
	 * @param color
	 *            color index
	 * @return color object
	 */
	public Color getColorObject(int color) {
		return new Color(getRGB(colors[color]));
	}

	/**
	 * Assembles color components into color code
	 * 
	 * @param color
	 *            color components (must be 3)
	 * @return color code
	 */
	public static int getRGB(int color[]) {
		return color[0] + (color[1] << 8) + (color[2] << 16);
	}

}
