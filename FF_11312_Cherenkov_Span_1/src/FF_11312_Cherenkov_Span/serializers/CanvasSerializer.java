package FF_11312_Cherenkov_Span.serializers;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Scanner;

import FF_11312_Cherenkov_Span.model.Canvas;
import FF_11312_Cherenkov_Span.model.Drawable;
import FF_11312_Cherenkov_Span.model.DrawableType;

/**
 * Class for converting canvas into text representation and vice versa.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class CanvasSerializer extends DrawableSerializer {
	private HashMap<String, DrawableSerializer> inputSerializers;
	private HashMap<DrawableType, DrawableSerializer> outputSerializers;

	/**
	 * Default constructor
	 */
	public CanvasSerializer() {
		super();
		outputSerializers = new HashMap<>();
		outputSerializers.put(DrawableType.POLYLINE, new PolylineSerializer());
		outputSerializers.put(DrawableType.FILL, new FillSerializer());

		inputSerializers = new HashMap<>();
		inputSerializers.put("POLYLINE",
				outputSerializers.get(DrawableType.POLYLINE));
		inputSerializers.put("FILL", outputSerializers.get(DrawableType.FILL));

	}

	/**
	 * Convert a canvas to its text representation
	 * 
	 * @param drawable
	 *            canvas to convert
	 * @param output
	 *            write destination
	 * @throws IOException
	 *             if some kind of I/O error occurs
	 */
	public void write(Drawable drawable, Writer output) throws IOException {
		Canvas canvas = (Canvas) drawable;
		output.write(canvas.getSize() + "\n");
		for (Drawable d : canvas) {
			outputSerializers.get(d.getType()).write(d, output);
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
	public Drawable read(Scanner input) throws IOException {
		if (!input.hasNextInt()) {
			throw new IOException("Input does not contain polylline count");
		}

		Canvas canvas = new Canvas();

		int count = input.nextInt();
		for (int i = 0; i < count; i++) {
			this.skip(input);
			String type = input.next();
			DrawableSerializer serializer = inputSerializers.get(type);
			if (serializer == null) {
				throw new IOException("Unknown type: " + type);
			}

			canvas.addDrawable(serializer.read(input));
		}

		return canvas;
	}

}
