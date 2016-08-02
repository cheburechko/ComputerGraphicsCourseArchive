package FF_11312_Cherenkov_Filt.filter.matrix;

/**
 * Custom 3x3 filter
 * 
 * @author Pavel Cherenkov
 * 
 */
public class CustomMatrixFilter extends MatrixFilter {

	@Override
	public void init() {
		initMatrix(3);
		setCell(-1, -1, 0);
		setCell(-1, 0, 0);
		setCell(-1, 1, 0);
		setCell(0, -1, 0);
		setCell(0, 0, 0);
		setCell(0, 1, 0);
		setCell(1, -1, 0);
		setCell(1, 0, 0);
		setCell(1, 1, 0);
		setOffset(0);
		setDivisor(1);
	}

}
