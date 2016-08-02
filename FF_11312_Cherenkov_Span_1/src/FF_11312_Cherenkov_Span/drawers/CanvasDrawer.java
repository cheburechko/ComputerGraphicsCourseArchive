package FF_11312_Cherenkov_Span.drawers;

import java.awt.image.BufferedImage;

import FF_11312_Cherenkov_Span.model.Canvas;
import FF_11312_Cherenkov_Span.model.Drawable;
import FF_11312_Cherenkov_Span.model.Fill;
import FF_11312_Cherenkov_Span.model.Polyline;

/**
 * Class responsible for drawing canvas on screen
 * 
 * @author Pavel Cherenkov
 * 
 */
public class CanvasDrawer {
	private PolylineDrawer polylineDrawer;
	private FillDrawer fillDrawer;

	/**
	 * Default constructor
	 */
	public CanvasDrawer() {
		polylineDrawer = new PolylineDrawer();
		fillDrawer = new FillDrawer();
	}

	/**
	 * Draws all contents of canvas into image
	 * 
	 * @param canvas
	 *            drawables container
	 * @param image
	 *            image to draw on
	 */
	public void draw(Canvas canvas, BufferedImage image) {
		for (Drawable d : canvas) {
			switch (d.getType()) {
			case FILL:
				fillDrawer.draw((Fill) d, image);
				break;
			case POLYLINE:
				polylineDrawer.draw((Polyline) d, image);
				break;
			case CANVAS:
				break;
			}
		}
	}
}
