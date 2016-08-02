package FF_11312_Cherenkov_Filt.filter.pixel;

/**
 * Identical filter
 * 
 * @author Pavel Cherenkov
 * 
 */
public class IdenticalFilter extends PixelFilter {

	@Override
	public int convertRGB(int rgb) {
		return rgb;
	}

}
