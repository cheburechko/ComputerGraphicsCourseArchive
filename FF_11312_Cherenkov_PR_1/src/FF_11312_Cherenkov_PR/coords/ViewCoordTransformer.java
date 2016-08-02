package FF_11312_Cherenkov_PR.coords;

import FF_11312_Cherenkov_PR.gui.BigImage;

/**
 * CoordTransformer for transferring coordinates between camera front plane and
 * image
 * 
 * @author Pavel Cherenkov
 * 
 */
public class ViewCoordTransformer extends CoordTransformer {
	/**
	 * Initialize transformer with given image and margin
	 * 
	 * @param image
	 *            image
	 * @param margin
	 *            margin
	 */
	public void init(BigImage image, int margin) {
		super.init(margin, margin, image.getWidth() - margin, image.getHeight()
				- margin, -1, -1, 1, 1);
	}
}
