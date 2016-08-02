package FF_11312_Cherenkov_PR.model;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Scanner;

/**
 * Material
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Material extends Observable {
	private double kPhong;
	private Color specular, diffusion;

	/**
	 * Get reflection power
	 * 
	 * @return reflection power
	 */
	public double getkPhong() {
		return kPhong;
	}

	/**
	 * Set reflection power
	 * 
	 * @param kPhong
	 *            reflection power
	 */
	public void setkPhong(double kPhong) {
		this.kPhong = kPhong;
		setChanged();
		notifyObservers();
	}

	/**
	 * Default constructor
	 * 
	 * @param diffusion
	 *            diffusion color
	 * @param specular
	 *            specular color
	 * @param kPhong
	 *            reflection power
	 */
	public Material(Color diffusion, Color specular, double kPhong) {
		super();
		this.diffusion = diffusion;
		this.specular = specular;
		this.kPhong = kPhong;
	}

	/**
	 * Get diffusion color
	 * 
	 * @return diffusion color
	 */
	public Color getDiffusion() {
		return diffusion;
	}

	/**
	 * Get specular color
	 * 
	 * @return specular color
	 */
	public Color getSpecular() {
		return specular;
	}

	/**
	 * Set specular color
	 * 
	 * @param specular
	 *            specular color
	 */
	public void setSpecular(Color specular) {
		this.specular = specular;
		setChanged();
		notifyObservers();
	}

	/**
	 * Set diffusion color
	 * 
	 * @param diffusion
	 *            diffusion color
	 */
	public void setDiffusion(Color diffusion) {
		this.diffusion = diffusion;
		setChanged();
		notifyObservers();
	}

	/**
	 * Read material from scanner
	 * 
	 * @param scanner
	 *            scanner
	 * @return new material
	 */
	public static Material readMaterial(Scanner scanner) {
		return new Material(new Color(scanner.nextInt(), scanner.nextInt(),
				scanner.nextInt()), new Color(scanner.nextInt(),
				scanner.nextInt(), scanner.nextInt()), scanner.nextDouble());
	}

	public void write(FileWriter writer) throws IOException {
		writer.write(Integer.toString(diffusion.getRed()));
		writer.write(' ');
		writer.write(Integer.toString(diffusion.getGreen()));
		writer.write(' ');
		writer.write(Integer.toString(diffusion.getBlue()));
		writer.write(' ');
		writer.write(Integer.toString(specular.getRed()));
		writer.write(' ');
		writer.write(Integer.toString(specular.getGreen()));
		writer.write(' ');
		writer.write(Integer.toString(specular.getBlue()));
		writer.write(' ');
		writer.write(Double.toString(kPhong));
		writer.write('\n');
	}
}
