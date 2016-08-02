package FF_11312_Cherenkov_Filt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * This class is used to render image at the center of the panel and paint
 * background with blue.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class ImageClip extends JPanel {
	private BufferedImage image;

	/**
	 * Default constructor
	 * 
	 * @param image
	 *            image to render
	 */
	public ImageClip(BufferedImage image) {
		this.image = image;
	}

	/**
	 * Paints image within visible frame centered at the center of the panel
	 * with blue background
	 * 
	 * @param g
	 *            Graphics object to draw with
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = Math.max((this.getWidth() - image.getWidth()) / 2, 0);
		int y = Math.max((this.getHeight() - image.getHeight()) / 2, 0);
		int startx = Math.max((image.getWidth() - getWidth()) / 2, 0);
		int starty = Math.max((image.getHeight() - getHeight()) / 2, 0);
		int width = Math.min(this.getWidth(), image.getWidth());
		int height = Math.min(this.getHeight(), image.getHeight());
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, x, y, x + width, y + height, startx, starty, startx
				+ width, starty + height, this);
	}
}
