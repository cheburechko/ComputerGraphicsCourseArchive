package FF_11312_Cherenkov_Filt.filter.pixel;

/**
 * Negative filter
 * 
 * @author Pavel Cherenkov
 * 
 */
public class NegativeFilter extends PixelFilter {

	@Override
	public int convertRGB(int rgb) {
		return ~rgb;
	}

}
