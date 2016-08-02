package FF_11312_Cherenkov_Filt.filter.pixel;

/**
 * Gamma correction filter
 * 
 * @author Pavel Cherenkov
 * 
 */
public class GammaCorrectionFilter extends PixelFilter {

	private double power;

	/**
	 * Get power
	 * 
	 * @return power
	 */
	public double getPower() {
		return power;
	}

	/**
	 * Set power
	 * 
	 * @param power
	 *            power
	 */
	public void setPower(double power) {
		this.power = power;
	}

	/**
	 * Default constructor
	 */
	public GammaCorrectionFilter() {
		super();
		power = 1.;
	}

	@Override
	public int convertRGB(int rgb) {
		rgb = rgb & 0xffffff;
		int newColor = 0;
		for (int i = 0; i < 3; i++) {
			newColor += (int) (Math.min(255, Math.pow(rgb % 256, power))) << 8 * i;
			rgb = rgb >> 8;
		}
		return newColor;
	}

}
