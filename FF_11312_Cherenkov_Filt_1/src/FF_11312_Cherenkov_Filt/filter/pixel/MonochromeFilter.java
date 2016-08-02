package FF_11312_Cherenkov_Filt.filter.pixel;

import java.awt.Color;

/**
 * Monochrome filter
 * 
 * @author Pavel Cherenkov
 * 
 */
public class MonochromeFilter extends PixelFilter {

	public int convertRGB(int rgb) {
		Color orig = new Color(rgb);
		int newc = Math.min(255, (int) (orig.getRed() * 0.299 + orig.getGreen()
				* 0.587 + orig.getBlue() * 0.114));
		return newc * ((1 << 16) + (1 << 8) + 1);
	}

}
