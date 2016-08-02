package FF_11312_Cherenkov_LE;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * View class which is responsible for drawing polylines and mouse input
 * 
 * @author Pavel Cherenkov
 * 
 */
public class View extends JPanel implements Observer {
	private boolean followingMode = false;
	private boolean drawingMode = false;
	private Polyline currentPolyline;
	private Point currentPoint;
	private Point lastPoint;
	private Canvas canvas;
	private Rectangle updateArea;

	/**
	 * Constructor for a given canvas.
	 * 
	 * @param c
	 *            canvas with predefined polylines.
	 */
	public View(Canvas c) {
		canvas = c;
		c.addObserver(this);

		currentPoint = new Point();
		updateArea = new Rectangle();

		addMouseListener(new MouseListener() {

			@Override
			public void mouseExited(MouseEvent e) {
				followingMode = false;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				followingMode = true;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (drawingMode) {
					// On LMB-release place next point
					if (SwingUtilities.isLeftMouseButton(e)) {
						// Add polyline to the list of polylines
						// if we placed the second point
						if (!currentPolyline.isLine())
							canvas.addPolyline(currentPolyline);

						lastPoint = new Point(e.getPoint());
						currentPolyline.addPoint(lastPoint);

					} else if (SwingUtilities.isRightMouseButton(e)) {
						// On RMB-release finish drawing
						finishDrawing();
					}
				} else {
					if (SwingUtilities.isLeftMouseButton(e)) {
						drawingMode = true;
						lastPoint = new Point(e.getPoint());
						currentPolyline = new Polyline(lastPoint);
					}
				}
			}
		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (drawingMode && followingMode) {
					updateArea.setFrameFromDiagonal(lastPoint, currentPoint);
					currentPoint.setLocation(e.getPoint());

					// Need to redraw both previous rectangle and the new one.
					updateArea.add(currentPoint);
					// Make sure there is no tearing
					updateArea.grow(1, 1);
					repaint(updateArea);

				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}
		});
	}

	/**
	 * Default constructor which creates its own canvas
	 */
	public View() {
		this(new Canvas());
	}

	/**
	 * Finishes drawing the last polyline
	 */
	public void finishDrawing() {
		if (drawingMode) {
			updateArea.setFrameFromDiagonal(lastPoint, currentPoint);
			updateArea.grow(1, 1);
			repaint(updateArea);
		}
		drawingMode = false;
	}

	/**
	 * Replace current canvas with another one
	 * 
	 * @param c
	 *            another canvas
	 */
	public void changeCanvas(Canvas c) {
		canvas.deleteObserver(this);
		this.canvas = c;
		canvas.addObserver(this);
		repaint();
	}

	/**
	 * An implementation of Observer.update() method.
	 * 
	 * @see Observer#update(Observable, Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg != null && Rectangle.class.isInstance(arg))
			repaint((Rectangle) arg);
		else
			repaint();
	}

	/**
	 * Draws polylines.
	 * 
	 * @param g
	 *            Graphics object to draw component to
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (Polyline polyline : canvas) {
			Point last_point = null;
			for (Point point : polyline) {
				if (last_point != null) {
					g.drawLine(last_point.x, last_point.y, point.x, point.y);
				}
				last_point = point;
			}
		}

		if (drawingMode) {
			g.drawLine(lastPoint.x, lastPoint.y, currentPoint.x, currentPoint.y);
		}
	}
}
