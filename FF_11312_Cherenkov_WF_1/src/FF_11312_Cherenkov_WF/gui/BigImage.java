package FF_11312_Cherenkov_WF.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This class is a wrapper for BufferedImage. It is used to allocate bigger
 * buffer to avoid creating new BufferedImage every time it needs to be resized.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class BigImage {
	private BufferedImage image;
	private int width;
	private int height;
	private static final int defaultWidth = 5000;
	private static final int defaultHeight = 5000;

	/**
	 * Default constructor
	 * 
	 * @param width
	 *            the width of the visible part of the image
	 * @param height
	 *            the height of the visible paert of the image
	 */
	public BigImage(int width, int height) {
		image = new BufferedImage(defaultWidth, defaultHeight,
				BufferedImage.TYPE_INT_RGB);
		this.width = width;
		this.height = height;
	}

	/**
	 * Gets width of the visible image
	 * 
	 * @return width of the visible image in pixels
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gets height of the visible image
	 * 
	 * @return height of the visible image in pixels
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Gets width of the visible image
	 * 
	 * @param width
	 *            width of the visible image in pixels
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets height of the visible image
	 * 
	 * @param height
	 *            height of the visible image in pixels
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Gets Graphics object from underlying BufferedImage
	 * 
	 * @return Graphics object of underlying image
	 */
	public Graphics getGraphics() {
		return image.getGraphics();
	}

	/**
	 * Sets color of the pixel in the underlying image
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param color
	 *            RGB color
	 */
	public void setRGB(int x, int y, int color) {
		image.setRGB(x, y, color);
	}

	/**
	 * Gets underlying image
	 * 
	 * @return underlying image
	 */
	public BufferedImage getImage() {
		return image;
	}
}
