package FF_11312_Cherenkov_PR.matrix;

import java.util.Observable;

/**
 * This is general class for working with matrices
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Matrix extends Observable {
	private double matrix[][];
	private int xsize, ysize;

	/**
	 * Constructor of a matrix of given size
	 * 
	 * @param xsize
	 *            number of columns
	 * @param ysize
	 *            number of rows
	 */
	public Matrix(int xsize, int ysize) {
		this.xsize = xsize;
		this.ysize = ysize;
		matrix = new double[xsize][ysize];
	}

	/**
	 * Copy constuctor
	 * 
	 * @param src
	 *            source matrixs
	 */
	public Matrix(Matrix src) {
		xsize = src.xsize;
		ysize = src.ysize;
		matrix = new double[xsize][ysize];

		for (int x = 0; x < xsize; x++)
			for (int y = 0; y < ysize; y++)
				matrix[x][y] = src.matrix[x][y];
	}

	/**
	 * Constructor of matrix with given size and values. Values are copied into
	 * the matrix.
	 * 
	 * @param xsize
	 *            number of columns
	 * @param ysize
	 *            number of rows
	 * @param values
	 *            initial values of the matrix. Should be of size xsize*ysize.
	 */
	public Matrix(int xsize, int ysize, double values[][]) {
		this(xsize, ysize);
		for (int x = 0; x < xsize; x++)
			for (int y = 0; y < ysize; y++)
				matrix[x][y] = values[y][x];
	}

	/**
	 * Sets a specific cell of a matris to a specific value
	 * 
	 * @param x
	 *            column number
	 * @param y
	 *            row number
	 * @param value
	 *            value
	 */
	public void setCell(int x, int y, double value) {
		matrix[x][y] = value;
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets value of the specific cell of a matrix
	 * 
	 * @param x
	 *            column naumber
	 * @param y
	 *            row number
	 * @return value
	 */
	public double getCell(int x, int y) {
		return matrix[x][y];
	}

	/**
	 * Multiplies every element of the matrix by specified value
	 * 
	 * @param value
	 *            multiplier
	 */
	public void multInPlace(double value) {
		for (int x = 0; x < xsize; x++)
			for (int y = 0; y < ysize; y++)
				matrix[x][y] *= value;
		setChanged();
		notifyObservers();
	}

	/**
	 * Adds contents of another matrix element by element
	 * 
	 * @param a
	 *            another matrix
	 */
	public void addInPlace(Matrix a) {
		for (int x = 0; x < xsize; x++)
			for (int y = 0; y < ysize; y++)
				matrix[x][y] += a.matrix[x][y];
		setChanged();
		notifyObservers();
	}

	/**
	 * Multiplies this matrix by another matrix and store the result in given
	 * matrix
	 * 
	 * @param a
	 *            another matrix
	 * @param result
	 *            resulting matrix
	 */
	public void mult(Matrix a, Matrix result) {
		if ((xsize != a.ysize) || (result.xsize != a.xsize)
				|| (result.ysize != ysize))
			return;

		for (int x = 0; x < a.xsize; x++)
			for (int y = 0; y < ysize; y++) {
				result.matrix[x][y] = 0;
				for (int t = 0; t < xsize; t++)
					result.matrix[x][y] += matrix[t][y] * a.matrix[x][t];
			}
	}

	/**
	 * Multiplies all elements of a matrix by given value and stores result in
	 * another matrix
	 * 
	 * @param value
	 *            multiplier
	 * @param result
	 *            resulting matrix
	 */
	public void mult(double value, Matrix result) {
		if ((xsize != result.xsize) || (ysize != result.ysize))
			return;
		for (int x = 0; x < xsize; x++)
			for (int y = 0; y < ysize; y++)
				result.matrix[x][y] = matrix[x][y] * value;
	}

	/**
	 * Adds another matrix to this matrix, store the result in the different
	 * matrix.
	 * 
	 * @param a
	 *            another matrix
	 * @param result
	 *            resulting matrix
	 */
	public void add(Matrix a, Matrix result) {
		if (xsize != a.xsize || ysize != a.ysize || xsize != result.xsize
				|| ysize != result.ysize)
			return;

		for (int x = 0; x < xsize; x++)
			for (int y = 0; y < ysize; y++)
				result.matrix[x][y] = matrix[x][y] + a.matrix[x][y];
	}

	/**
	 * Multiplies elements of this matrix by given value. Returns new matrix.
	 * 
	 * @param value
	 *            multiplier
	 * @return resulting matrix
	 */
	public Matrix mult(double value) {
		Matrix result = new Matrix(xsize, ysize);
		mult(value, result);
		return result;
	}

	/**
	 * Multiplies this matrix by another matrix. Returns new matrix.
	 * 
	 * @param a
	 *            another matrix
	 * @return resulting matrix
	 */
	public Matrix mult(Matrix a) {
		if (xsize != a.ysize)
			return null;
		Matrix result = new Matrix(a.xsize, ysize);
		mult(a, result);
		return result;
	}

	/**
	 * Adds another matrix to this element by element, return new matrix.
	 * 
	 * @param a
	 *            another matrix
	 * @return resulting matrix
	 */
	public Matrix add(Matrix a) {
		if (xsize != a.xsize || ysize != a.ysize)
			return null;
		Matrix result = new Matrix(xsize, ysize);
		add(a, result);
		return result;
	}

	/**
	 * Returns sum of element by element products with another matrix.
	 * 
	 * @param a
	 *            another matrix
	 * @return scalar product
	 */
	public double scalarProduct(Matrix a) {
		double value = 0;
		for (int x = 0; x < xsize; x++)
			for (int y = 0; y < ysize; y++)
				value += matrix[x][y] * a.matrix[x][y];
		return value;
	}

	/**
	 * Prints matrix contents in console
	 */
	public void printInConsole() {
		for (int y = 0; y < ysize; y++) {
			for (int x = 0; x < xsize; x++) {
				System.out.print(matrix[x][y]);
				System.out.print(' ');
			}
			System.out.println();
		}
	}

	public static void test() {
		double[][] d = { { 1., -1. }, { -1., 1. } };

		Matrix a = new Matrix(2, 2, d);
		Matrix b = new Matrix(2, 2, d);
		Matrix c = a.mult(b);

		a.printInConsole();
		b.printInConsole();
		c.printInConsole();

		Matrix e = a.mult(3);
		a.multInPlace(4);
		Matrix f = new Matrix(a);
		Matrix g = a.add(b);
		double h = a.scalarProduct(b);

		e.printInConsole();
		a.printInConsole();
		f.printInConsole();
		g.printInConsole();
		System.out.println(h);
	}

}
