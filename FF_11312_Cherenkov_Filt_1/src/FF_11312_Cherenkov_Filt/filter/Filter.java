package FF_11312_Cherenkov_Filt.filter;

import java.awt.image.BufferedImage;

/**
 * Interface for for image filters
 * 
 * @author Pavel Cherenkov
 * 
 */
public interface Filter {
	/**
	 * Draw source image with the filter applied on the destination image
	 * 
	 * @param src
	 *            source image
	 * @param dest
	 *            destination image
	 */
	public void filter(BufferedImage src, BufferedImage dest);
}
