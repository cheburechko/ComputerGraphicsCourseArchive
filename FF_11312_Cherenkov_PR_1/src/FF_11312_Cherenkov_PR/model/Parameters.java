package FF_11312_Cherenkov_PR.model;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

/**
 * This class represents all rendering parameters.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Parameters extends Observable implements Observer {

	private int tSteps, phiSteps;
	private double minT, maxT, minPhi, maxPhi, zn, zf, sw, sh;
	private Material backMat, frontMat;
	private LightSource lights[];
	private Color ambient, background;
	private boolean wireframe = false;

	/**
	 * Get if need to draw wireframe
	 * 
	 * @return true if wireframe should be drawn
	 */
	public boolean isWireframe() {
		return wireframe;
	}

	/**
	 * Set wireframe rendering parameter
	 * 
	 * @param wireframe
	 *            true to draw wireframe, false otherwise
	 */
	public void setWireframe(boolean wireframe) {
		this.wireframe = wireframe;
		setChanged();
		notifyObservers();
	}

	/**
	 * Default constructor
	 * 
	 * @param tSteps
	 *            amount of segments on a single bspline
	 * @param phiSteps
	 *            amount of rotation segments
	 * @param minT
	 *            value of the first point on the spline
	 * @param maxT
	 *            value of the last point on the spline
	 * @param minPhi
	 *            value of the starting angle of the surface
	 * @param maxPhi
	 *            value of the end angle of the surface
	 * @param zn
	 *            distance to the front plane of visible volume
	 * @param zf
	 *            distance to the back plane of visible volume
	 * @param sw
	 *            viewport width
	 * @param sh
	 *            viewport height
	 * @param background
	 *            background color
	 * @param ambient
	 *            color of ambient light
	 * @param l1
	 *            first light source
	 * @param l2
	 *            second light source
	 * @param l3
	 *            third light source
	 * @param frontMat
	 *            front material
	 * @param backMat
	 *            back material
	 */

	public Parameters(int tSteps, int phiSteps, double minT, double maxT,
			double minPhi, double maxPhi, double zn, double zf, double sw,
			double sh, Color background, Color ambient, LightSource l1,
			LightSource l2, LightSource l3, Material frontMat, Material backMat) {
		super();
		update(tSteps, phiSteps, minT, maxT, minPhi, maxPhi, zn, zf, sw, sh);
		setAmbient(ambient);
		setBackground(background);
		setFrontMat(frontMat);
		setBackMat(backMat);
		lights = new LightSource[3];
		setLight(l1, 0);
		setLight(l2, 1);
		setLight(l3, 2);
	}

	/**
	 * Updates all rendering parameters
	 * 
	 * @param tSteps
	 *            amount of segments on a single bspline
	 * @param phiSteps
	 *            amount of rotation segments
	 * @param minT
	 *            value of the first point on the spline
	 * @param maxT
	 *            value of the last point on the spline
	 * @param minPhi
	 *            value of the starting angle of the surface
	 * @param maxPhi
	 *            value of the end angle of the surface
	 * @param zn
	 *            distance to the front plane of visible volume
	 * @param zf
	 *            distance to the back plane of visible volume
	 * @param sw
	 *            viewport width
	 * @param sh
	 *            viewport height
	 */
	public void update(int tSteps, int phiSteps, double minT, double maxT,
			double minPhi, double maxPhi, double zn, double zf, double sw,
			double sh) {
		this.tSteps = tSteps;
		this.phiSteps = phiSteps;
		this.minT = minT;
		this.maxT = maxT;
		this.minPhi = minPhi;
		this.maxPhi = maxPhi;
		this.zn = zn;
		this.zf = zf;
		this.sw = sw;
		this.sh = sh;
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets amount of segments on a single bspline
	 * 
	 * @return amount of segments on a single bspline
	 */
	public int gettSteps() {
		return tSteps;
	}

	/**
	 * Sets amount of segments on a single bspline
	 * 
	 * @param tSteps
	 *            amount of segments on a single bspline
	 */
	public void settSteps(int tSteps) {
		this.tSteps = tSteps;
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets amount of rotation segments
	 * 
	 * @return amount of rotation segments
	 */
	public int getPhiSteps() {
		return phiSteps;
	}

	/**
	 * Sets amount of rotation segments
	 * 
	 * @param phiSteps
	 *            amount of rotation segments
	 */
	public void setPhiSteps(int phiSteps) {
		this.phiSteps = phiSteps;
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets value of the first point on the spline
	 * 
	 * @return value of the first point on the spline
	 */
	public double getMinT() {
		return minT;
	}

	/**
	 * Sets value of the first point on the spline
	 * 
	 * @param minT
	 *            value of the first point on the spline
	 */
	public void setMinT(double minT) {
		this.minT = minT;
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets value of the last point on the spline
	 * 
	 * @return value of the last point on the spline
	 */
	public double getMaxT() {
		return maxT;
	}

	/**
	 * Sets value of the last point on the spline
	 * 
	 * @param maxT
	 *            value of the last point on the spline
	 */
	public void setMaxT(double maxT) {
		this.maxT = maxT;
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets value of the starting angle of the surface
	 * 
	 * @return value of the starting angle of the surface
	 */
	public double getMinPhi() {
		return minPhi;
	}

	/**
	 * Sets value of the starting angle of the surface
	 * 
	 * @param minPhi
	 *            value of the starting angle of the surface
	 */
	public void setMinPhi(double minPhi) {
		this.minPhi = minPhi;
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets value of the end angle of the surface
	 * 
	 * @return value of the end angle of the surface
	 */
	public double getMaxPhi() {
		return maxPhi;
	}

	/**
	 * Sets value of the end angle of the surface
	 * 
	 * @param maxPhi
	 *            value of the end angle of the surface
	 */
	public void setMaxPhi(double maxPhi) {
		this.maxPhi = maxPhi;
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets distance to the front plane of visible volume
	 * 
	 * @return distance to the front plane of visible volume
	 */
	public double getZn() {
		return zn;
	}

	/**
	 * Sets distance to the front plane of visible volume
	 * 
	 * @param zn
	 *            distance to the front plane of visible volume
	 */
	public void setZn(double zn) {
		this.zn = zn;
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets distance to the back plane of visible volume
	 * 
	 * @return distance to the back plane of visible volume
	 */
	public double getZf() {
		return zf;
	}

	/**
	 * Sets distance to the back plane of visible volume
	 * 
	 * @param zf
	 *            distance to the back plane of visible volume
	 */
	public void setZf(double zf) {
		this.zf = zf;
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets viewport width
	 * 
	 * @return viewport width
	 */
	public double getSw() {
		return sw;
	}

	/**
	 * Sets viewport width
	 * 
	 * @param sw
	 *            viewport width
	 */
	public void setSw(double sw) {
		this.sw = sw;
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets viewport height
	 * 
	 * @return viewport height
	 */
	public double getSh() {
		return sh;
	}

	/**
	 * Sets viewport height
	 * 
	 * @param sh
	 *            viewport height
	 */
	public void setSh(double sh) {
		this.sh = sh;
		setChanged();
		notifyObservers();
	}

	/**
	 * Get back material
	 * 
	 * @return back material
	 */
	public Material getBackMat() {
		return backMat;
	}

	/**
	 * Set back material
	 * 
	 * @param backMat
	 *            back material
	 */
	public void setBackMat(Material backMat) {
		if (this.backMat != null)
			this.backMat.deleteObserver(this);
		this.backMat = backMat;
		backMat.addObserver(this);
		setChanged();
		notifyObservers();
	}

	/**
	 * Get front material
	 * 
	 * @return front material
	 */
	public Material getFrontMat() {
		return frontMat;
	}

	/**
	 * Set front material
	 * 
	 * @param frontMat
	 *            front material
	 */
	public void setFrontMat(Material frontMat) {
		if (this.frontMat != null)
			this.frontMat.deleteObserver(this);
		this.frontMat = frontMat;
		frontMat.addObserver(this);
		setChanged();
		notifyObservers();
	}

	/**
	 * Get ambient color
	 * 
	 * @return ambient color
	 */
	public Color getAmbient() {
		return ambient;
	}

	/**
	 * Set ambient color
	 * 
	 * @param ambient
	 *            ambient color
	 */
	public void setAmbient(Color ambient) {
		this.ambient = ambient;
		setChanged();
		notifyObservers();
	}

	/**
	 * Get background color
	 * 
	 * @return background color
	 */
	public Color getBackground() {
		return background;
	}

	/**
	 * Set background color
	 * 
	 * @param background
	 *            background color
	 */
	public void setBackground(Color background) {
		this.background = background;
		setChanged();
		notifyObservers();
	}

	/**
	 * Set light source
	 * 
	 * @param l
	 *            new light source
	 * @param index
	 *            light source index (0 - 2)
	 */
	public void setLight(LightSource l, int index) {
		if (lights[index] != null)
			lights[index].deleteObserver(this);
		lights[index] = l;
		l.addObserver(this);
		setChanged();
		notifyObservers();
	}

	/**
	 * Get light source
	 * 
	 * @param index
	 *            light source index (0 - 2)
	 * @return light source
	 */
	public LightSource getLight(int index) {
		return lights[index];
	}

	@Override
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers();
	}
}
