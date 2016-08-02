package FF_11312_Cherenkov_IS;

import java.awt.Graphics;

/**
 * This class draws isolines of function map
 * 
 * @author Pavel Cherenkov
 * 
 */
public class IsolineDrawer {

	private Palette color;
	private CoordTransformer trans;

	/**
	 * Default constructor
	 * 
	 * @param color
	 *            color of isolines
	 */
	public IsolineDrawer(Palette color) {
		this.color = color;
		trans = new CoordTransformer();
	}

	/**
	 * Draws isolines at all limiting levels of a function map
	 * 
	 * @param map
	 *            function map
	 * @param image
	 *            destination buffer
	 */
	public void draw(FuncMap map, BufferedImage image) {
		int levels = map.getLevels();
		for (int i = 0; i < levels; i++) {
			drawIsoline(map, image, map.getLevelValue(i));
		}
	}

	/**
	 * Helper function that draws a line between 2 points
	 * 
	 * @param g
	 *            Graphics object to draw with
	 * @param point1
	 *            1st point (array of 2 doubles)
	 * @param point2
	 *            2nd point (array of 2 doubles)
	 */
	private void drawLine(Graphics g, double[] point1, double[] point2) {
		// Transformer is assumed to be set up
		g.drawLine(trans.imageX(point1[0]), trans.imageY(point1[1]),
				trans.imageX(point2[0]), trans.imageY(point2[1]));
	}

	/**
	 * Draws isoline for given value
	 * 
	 * @param map
	 *            function map
	 * @param image
	 *            destination buffer
	 * @param value
	 *            isoline value
	 */
	public void drawIsoline(FuncMap map, BufferedImage image, double value) {
		int xgrid = map.getXgrid();
		int ygrid = map.getYgrid();

		trans.init(map, image);

		double f[] = new double[4];
		int crosspointsCount = 0;
		double crosspoints[][] = new double[4][2];
		double cellPoints[][] = new double[4][2];
		int balance = 0;
		int lastCross = 0;
		int zeroCount = 0;
		int lastZero = 0;

		Graphics graphics = image.getGraphics();
		graphics.setColor(color.getColorObject(0));

		for (int y = 0; y < ygrid - 1; y++)
			for (int x = 0; x < xgrid - 1; x++) {
				f[0] = map.getGridValue(x, y);
				map.transformGridToReal(x, y, cellPoints[0]);
				f[1] = map.getGridValue(x + 1, y);
				map.transformGridToReal(x + 1, y, cellPoints[1]);
				f[2] = map.getGridValue(x + 1, y + 1);
				map.transformGridToReal(x + 1, y + 1, cellPoints[2]);
				f[3] = map.getGridValue(x, y + 1);
				map.transformGridToReal(x, y + 1, cellPoints[3]);

				crosspointsCount = 0;
				balance = 0;
				zeroCount = 0;

				for (int i = 0; i < 4; i++) {
					// check crossing of the side
					if ((f[i] - value) * (f[(i + 1) % 4] - value) < 0) {
						crosspointsCount++;
						// computing a crosspoint
						for (int j = 0; j < 2; j++) {
							if (cellPoints[i][j] != cellPoints[(i + 1) % 4][j]) {
								double f1 = f[i];
								double f2 = f[(i + 1) % 4];
								crosspoints[i][j] = (cellPoints[(i + 1) % 4][j] + cellPoints[i][j]
										* (f2 - value) / (value - f1))
										* (value - f1) / (f2 - f1);
							} else
								crosspoints[i][j] = cellPoints[i][j];

						}
						lastCross = i;
					} else if (f[i] == value) {
						// unique situation: take first point if it equals to
						// value
						zeroCount++;
						crosspoints[i][0] = cellPoints[i][0];
						crosspoints[i][1] = cellPoints[i][1];
						lastZero = i;
					}

					// This will help finding out if line goes through adjacent
					// or opposite sides
					if (f[i] < value)
						balance--;
					else if (f[i] > value)
						balance++;
				}
				// 2 parallel lines crossing
				if (crosspointsCount == 4) {
					// check value in center
					double centerX = (cellPoints[0][0] + cellPoints[1][0]) / 2.;
					double centerY = (cellPoints[1][1] + cellPoints[2][1]) / 2.;
					double val = map.getValue(centerX, centerY);
					if ((val - value) * (f[0] - value) < 0) {
						drawLine(graphics, crosspoints[0], crosspoints[3]);
						drawLine(graphics, crosspoints[1], crosspoints[2]);

					} else {
						drawLine(graphics, crosspoints[0], crosspoints[1]);
						drawLine(graphics, crosspoints[2], crosspoints[3]);
					}
				}
				// single line
				else if ((crosspointsCount == 2)
						|| (zeroCount + crosspointsCount == 2)) {
					// line crosses opposite sides
					if ((balance == 0) || (zeroCount == 1)
							&& (crosspointsCount == 1)
							&& (Math.abs(lastZero - lastCross) == 2)) {
						// vertical slice
						if ((value - f[0]) * (value - f[1]) < 0
								|| (value == f[0])) {
							drawLine(graphics, crosspoints[0], crosspoints[2]);
							;
						}
						// horizontal slice
						else {
							drawLine(graphics, crosspoints[1], crosspoints[3]);
						}
					}
					// line crosses adjacent sides
					else {
						// Special case with zeroes on vertices
						if (crosspointsCount != 2) {
							if ((lastCross < lastZero)
									|| (crosspointsCount == 0))
								lastCross = lastZero;
						}

						// SPecial case of the first vertex
						if ((lastCross == 3)
								&& ((value - f[0]) * (value - f[1]) <= 0)) {
							drawLine(graphics, crosspoints[0], crosspoints[3]);
							;
						}
						// other cases
						else {
							drawLine(graphics, crosspoints[lastCross],
									crosspoints[lastCross - 1]);
						}
					}
				}
				// a corner
				else if (zeroCount == 3) {
					int i = 0;
					while (f[i] == value)
						i++;
					drawLine(graphics, crosspoints[(i + 1) % 4],
							crosspoints[(i + 2) % 4]);
					drawLine(graphics, crosspoints[(i + 2) % 4],
							crosspoints[(i + 3) % 4]);
				}
				// the most weird case
				else if (zeroCount == 2 && crosspointsCount == 1) {
					if (lastZero == 3 && f[0] == value)
						lastZero = 0;
					drawLine(graphics, crosspoints[lastZero],
							crosspoints[(lastZero + 3) % 4]);

					double centerX = (cellPoints[0][0] + cellPoints[1][0]) / 2.;
					double centerY = (cellPoints[1][1] + cellPoints[2][1]) / 2.;
					double val = map.getValue(centerX, centerY);

					// Leave central point and vertex of the same sign on
					// the same side of the new line
					if ((val - value) * (f[(lastZero + 1) % 4] - value) > 0) {
						drawLine(graphics, crosspoints[(lastZero + 2) % 4],
								crosspoints[lastCross]);
					} else {
						drawLine(graphics, crosspoints[(lastZero + 1) % 4],
								crosspoints[lastCross]);
					}

				}
			}

	}
}
