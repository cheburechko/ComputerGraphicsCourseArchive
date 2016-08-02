package FF_11312_Cherenkov_Span.drawers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import FF_11312_Cherenkov_Span.model.Polyline;

/**
 * Class responsible for drawing polylines on screen
 * 
 * @author Pavel Cherenkov
 * 
 */
public class PolylineDrawer {
	/**
	 * Draws a polyline using Bresenham's line algorithm
	 * 
	 * @param polyline
	 *            a polyline to draw
	 * @param image
	 *            destination image
	 */
	public void draw(Polyline polyline, BufferedImage image) {
		Point lastPoint = null;
		int color = Color.BLACK.getRGB();
		Graphics2D g = image.createGraphics();
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(polyline.getWidth()));

		for (Point p : polyline) {
			if (lastPoint != null) {
				if (polyline.getWidth() == 1.) {
					int x = 0;
					int y = 0;
					int dy = p.y - lastPoint.y;
					int dx = p.x - lastPoint.x;

					// Translate line to 1st quarter, dy<dx
					int orientX = 1;
					int orientY = 1;
					int swap = 0;

					if (dy < 0) {
						orientY = -1;
						dy = -dy;
					}

					if (dx < 0) {
						orientX = -1;
						dx = -dx;
					}

					if (dy > dx) {
						swap = 1;
						dx = dx + dy;
						dy = dx - dy;
						dx = dx - dy;
					}

					// Bresenham algorithm.
					int err = 0;
					while (x < dx) {
						// Backwards transformation.
						int realx = lastPoint.x + orientX
								* (x * (1 - swap) + y * swap);
						int realy = lastPoint.y + orientY
								* (y * (1 - swap) + x * swap);
						image.setRGB(realx, realy, color);

						x++;
						err += 2 * dy;
						if (err > dx) {
							err -= 2 * dx;
							y++;
						}
					}
				} else {
					g.drawLine(lastPoint.x, lastPoint.y, p.x, p.y);
				}

			}
			lastPoint = p;
		}
		image.setRGB(lastPoint.x, lastPoint.y, color);
	}
}
