package FF_11312_Cherenkov_PR.coords;

import FF_11312_Cherenkov_PR.gui.BigImage;
import FF_11312_Cherenkov_PR.gui.editor.BSplineRenderer;

/**
 * This class is used to transfer coordinates between image and model axes in
 * BSplineEditor.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class SplineCoordTransformer extends CoordTransformer {

	/**
	 * Initialize transformer with given image and renderer
	 * 
	 * @param renderer
	 *            bspline renderer
	 * @param image
	 *            destination image
	 */
	public void init(BSplineRenderer renderer, BigImage image) {
		super.init(0, 0, image.getWidth(), image.getHeight(),
				renderer.getMinx(), renderer.getMinz(), renderer.getMaxx(),
				renderer.getMaxz());
	}
}
