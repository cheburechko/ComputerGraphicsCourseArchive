package FF_11312_Cherenkov_PR.model;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Scanner;

import FF_11312_Cherenkov_PR.matrix.Point;

/**
 * Light source
 * 
 * @author Pavel Cherenkov
 * 
 */
public class LightSource extends Observable {
	private Color color;
	private Point position;
	private double intensity;

	/**
	 * Get intensity
	 * 
	 * @return intensity
	 */
	public double getIntensity() {
		return intensity;
	}

	/**
	 * Set intensity
	 * 
	 * @param intensity
	 *            intensity
	 */
	public void setIntensity(double intensity) {
		this.intensity = intensity;
		setChanged();
	}

	/**
	 * Get lights color
	 * 
	 * @return lights color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set lights color
	 * 
	 * @param color
	 *            lights color
	 */
	public void setColor(Color color) {
		this.color = color;
		setChanged();
		notifyObservers();
	}

	/**
	 * Get lights position in world coordinates
	 * 
	 * @return lights position in world coordinates
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * Set lights in world coordinates
	 * 
	 * @param position
	 *            lights position in world coordinates
	 */
	public void setPosition(Point position) {
		this.position = position;
		setChanged();
	}

	/**
	 * Default constructor (default intensity is 10)
	 * 
	 * @param color
	 *            lights color
	 * @param position
	 *            lights position in world coordinates
	 */
	public LightSource(Color color, Point position) {
		super();
		this.color = color;
		this.position = position;
		this.intensity = 10;
	}

	/**
	 * Checks if light is on
	 * 
	 * @return true if light is on, false otherwise
	 */
	public boolean isOn() {
		return color.getRGB() != 0;
	}

	/**
	 * Reads light from scanner
	 * 
	 * @param scanner
	 *            scanner
	 * @return new light source
	 */
	public static LightSource readLight(Scanner scanner) {
		return new LightSource(new Color(scanner.nextInt(), scanner.nextInt(),
				scanner.nextInt()), new Point(scanner.nextDouble(),
				scanner.nextDouble(), scanner.nextDouble()));
	}

	/**
	 * Writes light source to file
	 * 
	 * @param writer
	 *            write destination
	 * @throws IOException
	 *             FileWriter write exception
	 */
	public void write(FileWriter writer) throws IOException {
		writer.write(Integer.toString(color.getRed()));
		writer.write(' ');
		writer.write(Integer.toString(color.getGreen()));
		writer.write(' ');
		writer.write(Integer.toString(color.getBlue()));
		writer.write(' ');
		writer.write(Double.toString(position.getX()));
		writer.write(' ');
		writer.write(Double.toString(position.getY()));
		writer.write(' ');
		writer.write(Double.toString(position.getZ()));
		writer.write('\n');
	}
}
