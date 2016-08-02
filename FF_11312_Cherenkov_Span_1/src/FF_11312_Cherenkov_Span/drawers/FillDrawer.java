package FF_11312_Cherenkov_Span.drawers;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Stack;

import FF_11312_Cherenkov_Span.model.Fill;

/**
 * Class responsible for drawing fills on screen
 * 
 * @author Pavel Cherenkov
 * 
 */
public class FillDrawer {

	private class Span {
		public int y, x1, x2;

		public Span(int y, int x1, int x2) {
			this.y = y;
			this.x1 = x1;
			this.x2 = x2;
		}
	};

	private Span findSpan(BufferedImage image, int x, int y) {
		int color = image.getRGB(x, y);
		int x1 = x, x2 = x;

		while (x1 >= 0 && image.getRGB(x1, y) == color)
			x1--;
		while (x2 < image.getWidth() && image.getRGB(x2, y) == color)
			x2++;
		x1++;
		x2--;

		return new Span(y, x1, x2);
	}

	private Span findSpan(BufferedImage image, Point p) {
		return findSpan(image, p.x, p.y);
	}

	/**
	 * Draws a fill using span algorithm
	 * 
	 * @param fill
	 *            a fill to draw
	 * @param image
	 *            destination image
	 */
	public void draw(Fill fill, BufferedImage image) {
		int newColor;
		if (fill.getColor() == 0)
			newColor = Color.RED.getRGB();
		else
			newColor = Color.BLUE.getRGB();

		Point p = fill.getPoint();
		int coherence = fill.getConnectivity();

		int color = image.getRGB(p.x, p.y);

		if (color == newColor)
			return;

		Stack<Span> spans = new Stack<>();
		spans.push(findSpan(image, fill.getPoint()));

		while (!spans.empty()) {
			Span span = spans.pop();
			// Fill span with new color
			for (int x = span.x1; x <= span.x2; x++)
				image.setRGB(x, span.y, newColor);

			// For coherence == 8 include diagonal neighbors
			if (coherence == 8) {
				span.x1 = span.x1 > 0 ? span.x1 - 1 : 0;
				span.x2 = span.x2 + 1 < image.getWidth() ? span.x2 + 1
						: span.x2;
			}

			// Add new spans
			for (int y = span.y > 0 ? span.y - 1 : 0; y <= (span.y + 1 < image
					.getHeight() ? span.y + 1 : span.y); y++) {
				if (y != span.y) {
					for (int x = span.x1; x <= span.x2; x++) {
						if (image.getRGB(x, y) == color) {
							Span newSpan = findSpan(image, x, y);
							x = newSpan.x2;
							spans.push(newSpan);
						}
					}
				}
			}
		}
	}
}
