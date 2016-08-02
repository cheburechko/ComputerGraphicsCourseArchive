package FF_11312_Cherenkov_Filt.filter.pixel;

import java.awt.image.BufferedImage;

import FF_11312_Cherenkov_Filt.filter.Filter;

/**
 * Abstract class for pixel filters
 * 
 * @author Pavel Cherenkov
 * 
 */
public abstract class PixelFilter implements Filter {

	@Override
	public void filter(BufferedImage src, BufferedImage dest) {
		for (int y = 0; y < src.getHeight(); y++) {
			for (int x = 0; x < src.getWidth(); x++) {
				dest.setRGB(x, y, convertRGB(src.getRGB(x, y)));
			}
		}
	}

	/**
	 * Applies filter to a single pixel
	 * 
	 * @param rgb
	 *            color of pixel from source image
	 * @return color of pixel in destination image
	 */
	public abstract int convertRGB(int rgb);

}
