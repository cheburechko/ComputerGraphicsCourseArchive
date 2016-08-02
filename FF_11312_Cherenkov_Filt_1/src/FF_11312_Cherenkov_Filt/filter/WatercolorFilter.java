package FF_11312_Cherenkov_Filt.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import FF_11312_Cherenkov_Filt.filter.matrix.SharpenFilter;

/**
 * Watercolor filter
 * 
 * @author Pavel Cherenkov
 * 
 */
public class WatercolorFilter implements Filter {

	private ArrayList<Integer> red, green, blue;
	private SharpenFilter sharpen;

	/**
	 * Default constructor
	 */
	public WatercolorFilter() {
		red = new ArrayList<Integer>();
		green = new ArrayList<Integer>();
		blue = new ArrayList<Integer>();
		sharpen = new SharpenFilter();
	}

	@Override
	public void filter(BufferedImage src, BufferedImage dest) {
		BufferedImage img = new BufferedImage(src.getWidth(), src.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		for (int y = 0; y < src.getHeight(); y++)
			for (int x = 0; x < src.getWidth(); x++) {
				for (int ys = Math.max(0, y - 2); ys < Math.min(y + 3,
						src.getHeight()); ys++)
					for (int xs = Math.max(0, x - 2); xs < Math.min(x + 3,
							src.getWidth()); xs++) {
						Color color = new Color(src.getRGB(xs, ys));
						red.add(color.getRed());
						green.add(color.getGreen());
						blue.add(color.getBlue());
					}

				Collections.sort(red);
				Collections.sort(blue);
				Collections.sort(green);
				Color color = new Color(red.get(red.size() / 2),
						green.get(green.size() / 2), blue.get(blue.size() / 2));
				red.clear();
				blue.clear();
				green.clear();

				img.setRGB(x, y, color.getRGB());
			}

		sharpen.filter(img, dest);
	}

}
