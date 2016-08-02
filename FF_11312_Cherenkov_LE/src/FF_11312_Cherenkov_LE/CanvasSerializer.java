package FF_11312_Cherenkov_LE;

import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

/**
 * Class for converting canvas into text representation and vice versa.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class CanvasSerializer {
	private PolylineSerializer serializer;

	/**
	 * Default constructor
	 */
	public CanvasSerializer() {
		serializer = new PolylineSerializer();
	}

	/**
	 * Convert a canvas to its text representation
	 * 
	 * @param canvas
	 *            canvas to convert
	 * @param output
	 *            write destination
	 * @throws IOException
	 *             if some kind of I/O error occurs
	 */
	public void write(Canvas canvas, Writer output) throws IOException {
		output.write(canvas.getSize() + "\n");
		for (Polyline p : canvas) {
			serializer.write(p, output);
		}
	}

	/**
	 * Get a canvas from its text representation
	 * 
	 * @param input
	 *            source
	 * @return canvas
	 * @throws IOException
	 *             if some kind of I/O error occurs or the data in source is in
	 *             invalid format
	 */
	public Canvas read(Scanner input) throws IOException {
		if (!input.hasNextInt()) {
			throw new IOException("Input does not contain polylline count");
		}

		Canvas canvas = new Canvas();

		int count = input.nextInt();
		input.nextLine();
		for (int i = 0; i < count; i++) {
			String type = input.nextLine();
			if (!type.equals("Polyline")) {
				throw new IOException("Expected: Polyline. Got: " + type);
			}

			canvas.addPolyline(serializer.read(input));
			input.nextLine();
			input.nextLine();
		}

		return canvas;
	}

}
