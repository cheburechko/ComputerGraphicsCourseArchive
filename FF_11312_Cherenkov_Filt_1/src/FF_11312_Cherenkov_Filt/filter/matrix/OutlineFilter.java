package FF_11312_Cherenkov_Filt.filter.matrix;

/**
 * Outline filter
 * 
 * @author Pavel Cherenkov
 * 
 */
public class OutlineFilter extends MatrixFilter {

	@Override
	public void init() {
		initMatrix(3);
		setCell(-1, -1, 0);
		setCell(-1, 0, -1);
		setCell(-1, 1, 0);
		setCell(0, -1, -1);
		setCell(0, 0, 4);
		setCell(0, 1, -1);
		setCell(1, -1, 0);
		setCell(1, 0, -1);
		setCell(1, 1, 0);
		setOffset(128);
		setDivisor(1);
	}

}
