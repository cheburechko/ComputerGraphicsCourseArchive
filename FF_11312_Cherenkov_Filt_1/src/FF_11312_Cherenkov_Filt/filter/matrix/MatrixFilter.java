package FF_11312_Cherenkov_Filt.filter.matrix;

import java.awt.image.BufferedImage;

import FF_11312_Cherenkov_Filt.filter.Filter;

/**
 * An abstract class for implementing matrix filters
 * 
 * @author Pavel Cherenkov
 * 
 */
public abstract class MatrixFilter implements Filter {

	private int matrix[][];
	private int matrixSize;
	private int offset;
	private int divisor;
	private int mOff;

	/**
	 * Get offset value added to all color components
	 * 
	 * @return offset value
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Set offset value added to all color components
	 * 
	 * @param offset
	 *            offset value
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * Get divisor value by which the resulting color components are divided
	 * 
	 * @return divisor value
	 */
	public int getDivisor() {
		return divisor;
	}

	/**
	 * Set divisor value by which the resulting color components are divided
	 * 
	 * @param divisor
	 *            divisor value
	 */
	public void setDivisor(int divisor) {
		this.divisor = divisor;
	}

	/**
	 * Get matrix size
	 * 
	 * @return matrix size
	 */
	public int getMatrixSize() {
		return matrixSize;
	}

	/**
	 * Default constructor
	 */
	public MatrixFilter() {
		init();
	}

	/**
	 * Initialize all parameters of the matrix
	 */
	public abstract void init();

	/**
	 * Initialize matrix of the given size
	 * 
	 * @param size
	 *            size
	 */
	public void initMatrix(int size) {
		matrixSize = size;
		matrix = new int[size][size];
		mOff = size / 2;
	}

	/**
	 * Set value of the cell at given coordinates. (0, 0) corresponds to the
	 * center of the matrix
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param value
	 *            cell value
	 */
	public void setCell(int x, int y, int value) {
		matrix[x + mOff][y + mOff] = value;
	}

	/**
	 * Get value of the cell at given coordinates. (0, 0) corresponds to the
	 * center of the matrix
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return cell value
	 */
	public int getCell(int x, int y) {
		return matrix[x + mOff][y + mOff];
	}

	@Override
	public void filter(BufferedImage src, BufferedImage dest) {
		int rgb[] = new int[3];
		int newColor;
		for (int y = 0; y < src.getHeight(); y++)
			for (int x = 0; x < src.getWidth(); x++) {
				newColor = rgb[0] = rgb[1] = rgb[2] = 0;

				for (int ys = Math.max(0, y - mOff); ys < Math.min(
						y + mOff + 1, src.getHeight()); ys++)
					for (int xs = Math.max(0, x - mOff); xs < Math.min(x + 1
							+ mOff, src.getWidth()); xs++) {
						int color = src.getRGB(xs, ys) & 0xffffff;
						for (int i = 0; i < 3; i++)
							rgb[i] += getCell(xs - x, ys - y)
									* ((color >> i * 8) % 256);
					}

				for (int i = 0; i < 3; i++) {
					rgb[i] = Math.max(0,
							Math.min(255, rgb[i] / divisor + offset));
					newColor += rgb[i] << 8 * i;
				}

				dest.setRGB(x, y, newColor);
			}
	}
}
