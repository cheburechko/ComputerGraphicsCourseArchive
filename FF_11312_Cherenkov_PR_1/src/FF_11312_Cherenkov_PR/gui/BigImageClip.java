package FF_11312_Cherenkov_PR.gui;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * This class is used to render BigImage
 * 
 * @author Pavel Cherenkov
 * 
 */
public class BigImageClip extends JPanel {
	private BigImage image;

	/**
	 * Default constructor
	 * 
	 * @param image
	 *            image to render
	 */
	public BigImageClip(BigImage image) {
		this.image = image;
	}

	/**
	 * Paints BigImage within visible frame
	 * 
	 * @param g
	 *            Graphics object to draw with
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image.getImage(), 0, 0, image.getWidth(),
				image.getHeight(), 0, 0, image.getWidth(), image.getHeight(),
				this);
	}
}
