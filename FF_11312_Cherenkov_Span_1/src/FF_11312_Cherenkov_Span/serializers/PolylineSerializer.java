package FF_11312_Cherenkov_Span.serializers;

import java.awt.Point;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

import FF_11312_Cherenkov_Span.model.Drawable;
import FF_11312_Cherenkov_Span.model.Polyline;

/**
 * Class for converting polylines into text representation and vice versa
 * 
 * @author Pavel Cherenkov
 * 
 */
public class PolylineSerializer extends DrawableSerializer {

	/**
	 * Default constructor
	 */
	public PolylineSerializer() {
		super();
	}

	/**
	 * Convert a polyline into its text representation
	 * 
	 * @param drawable
	 *            a polyline to convert
	 * @param output
	 *            write destination
	 * @throws IOException
	 *             if some kind of I/O error occurs
	 */
	@Override
	public void write(Drawable drawable, Writer output) throws IOException {
		Polyline polyline = (Polyline) drawable;

		if (!polyline.isLine())
			return;

		output.write("POLYLINE\n");
		output.write(polyline.getLength() + " " + polyline.getWidth() + "\n");
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
	 * @return fill
	 * @throws IOException
	 *             if some kind of I/O error occurs or the data in source is in
	 *             invalid format
	 */
	@Override
	public Drawable read(Scanner input) throws IOException {
		Polyline result = new Polyline();
		int length;

		this.skip(input);
		if (input.hasNextInt())
			length = input.nextInt();
		else
			throw new IOException("Polyline length missing");

		this.skip(input);
		if (input.hasNextInt())
			result.setWidth(input.nextInt());
		else
			throw new IOException("Polyline width missing");

		while (length > 0) {
			int x, y;

			this.skip(input);
			if (input.hasNextInt())
				x = input.nextInt();
			else
				throw new IOException("Polyline point X coordinate missing");

			this.skip(input);
			if (input.hasNextInt())
				y = input.nextInt();
			else
				throw new IOException("Polyline point Y coordinate missing");

			result.addPoint(new Point(x, y));
			length--;
		}

		return result;
	}
}
