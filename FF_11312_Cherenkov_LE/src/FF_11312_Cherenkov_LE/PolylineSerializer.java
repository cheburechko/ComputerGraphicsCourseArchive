package FF_11312_Cherenkov_LE;

import java.awt.Point;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

/**
 * Class for converting polylines into text representation and vice versa
 * 
 * @author Pavel Cherenkov
 * 
 */
public class PolylineSerializer {

	/**
	 * Default constructor
	 */
	public PolylineSerializer() {

	}

	/**
	 * Convert a polyline into its text representation
	 * 
	 * @param polyline
	 *            a polyline to convert
	 * @param output
	 *            write destination
	 * @throws IOException
	 *             if some kind of I/O error occurs
	 */
	public void write(Polyline polyline, Writer output) throws IOException {
		if (!polyline.isLine())
			return;

		output.write("Polyline\n");
		for (Point p : polyline) {
			output.write(p.x + " " + p.y + "\n");
		}
		output.write("\n");
	}

	/**
	 * Gets a polyline from its text representation
	 * 
	 * @param input
	 *            source of polyline
	 * @return polyline
	 * @throws IOException
	 *             if some kind of I/O error occurs or the data in source is in
	 *             invalid format
	 */
	public Polyline read(Scanner input) throws IOException {
		Polyline result = new Polyline();
		while (input.hasNextInt()) {
			result.addPoint(new Point(input.nextInt(), input.nextInt()));
		}

		return result;
	}
}
