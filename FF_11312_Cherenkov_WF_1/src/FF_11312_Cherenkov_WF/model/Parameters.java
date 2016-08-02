package FF_11312_Cherenkov_WF.model;

import java.util.Observable;

/**
 * This class represents all rendering parameters.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class Parameters extends Observable {

	private int tSteps, phiSteps, scale;
	private double minT, maxT, minPhi, maxPhi, zn, zf, sw, sh;

	/**
	 * Default constructor
	 * 
	 * @param tSteps
	 *            amount of segments on a single bspline
	 * @param phiSteps
	 *            amount of rotation segments
	 * @param scale
	 *            amount of sub-segments on every segment
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
	public Parameters(int tSteps, int phiSteps, int scale, double minT,
			double maxT, double minPhi, double maxPhi, double zn, double zf,
			double sw, double sh) {
		super();
		this.tSteps = tSteps;
		this.phiSteps = phiSteps;
		this.scale = scale;
		this.minT = minT;
		this.maxT = maxT;
		this.minPhi = minPhi;
		this.maxPhi = maxPhi;
		this.zn = zn;
		this.zf = zf;
		this.sw = sw;
		this.sh = sh;
	}

	/**
	 * Updates all rendering parameters
	 * 
	 * @param tSteps
	 *            amount of segments on a single bspline
	 * @param phiSteps
	 *            amount of rotation segments
	 * @param scale
	 *            amount of sub-segments on every segment
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
	public void update(int tSteps, int phiSteps, int scale, double minT,
			double maxT, double minPhi, double maxPhi, double zn, double zf,
			double sw, double sh) {
		this.tSteps = tSteps;
		this.phiSteps = phiSteps;
		this.scale = scale;
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
	 * Gets amount of sub-segments on every segment
	 * 
	 * @return amount of sub-segments on every segment
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * Sets amount of sub-segments on every segment
	 * 
	 * @param scale
	 *            amount of sub-segments on every segment
	 */
	public void setScale(int scale) {
		this.scale = scale;
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
}
