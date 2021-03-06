package FF_11312_Cherenkov_Span.serializers;

import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.regex.Pattern;

import FF_11312_Cherenkov_Span.model.Drawable;

/**
 * Abstract class for R/W drawables in text.
 * 
 * @author thepunchy
 * 
 */
public abstract class DrawableSerializer {
	private Pattern comments;

	/**
	 * Default constructor
	 */
	public DrawableSerializer() {
		comments = Pattern.compile("(\\s*//[^\n]*\n)*");
	}

	protected void skip(Scanner scanner) {
		scanner.skip(comments);
	}

	/**
	 * Convert a drawable into its text representation
	 * 
	 * @param drawable
	 *            a drawable to convert
	 * @param output
	 *            write destination
	 * @throws IOException
	 *             if some kind of I/O error occurs
	 */
	public abstract void write(Drawable drawable, Writer output)
			throws IOException;

	/**
	 * Gets a drawable from its text representation
	 * 
	 * @param input
	 *            source of drawable
	 * @return drawable
	 * @throws IOException
	 *             if some kind of I/O error occurs or the data in source is in
	 *             invalid format
	 */
	public abstract Drawable read(Scanner input) throws IOException;

}