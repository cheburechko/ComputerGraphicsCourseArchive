package FF_11312_Cherenkov_Filt.filter.matrix;

/**
 * Blur filter
 * 
 * @author Pavel Cherenkov
 * 
 */
public class BlurFilter extends MatrixFilter {

	@Override
	public void init() {
		initMatrix(3);
		setCell(-1, -1, 0);
		setCell(-1, 0, 1);
		setCell(-1, 1, 0);
		setCell(0, -1, 1);
		setCell(0, 0, 2);
		setCell(0, 1, 1);
		setCell(1, -1, 0);
		setCell(1, 0, 1);
		setCell(1, 1, 0);
		setOffset(0);
		setDivisor(6);

	}

}
