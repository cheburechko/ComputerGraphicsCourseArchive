package FF_11312_Cherenkov_Span;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import FF_11312_Cherenkov_Span.drawers.CanvasDrawer;
import FF_11312_Cherenkov_Span.drawers.FillDrawer;
import FF_11312_Cherenkov_Span.drawers.PolylineDrawer;
import FF_11312_Cherenkov_Span.model.Canvas;
import FF_11312_Cherenkov_Span.model.Drawable;
import FF_11312_Cherenkov_Span.model.Fill;
import FF_11312_Cherenkov_Span.model.Polyline;

/**
 * View class which is responsible for drawing polylines and mouse input
 * 
 * @author Pavel Cherenkov
 * 
 */
public class View extends JPanel implements Observer {
	// Controller state variables
	private boolean followingMode = false;
	private boolean drawingMode = false;
	private boolean fillingMode = false;
	private int color = 0;
	private int connectivity = 4;
	private int strokeWidth = 1;
	// Drawable stuff
	private Polyline currentPolyline;
	private Point currentPoint;
	private Point lastPoint;
	private Canvas canvas;
	private Rectangle updateArea;
	private BufferedImage image;
	// Drawers
	private FillDrawer fillDrawer;
	private PolylineDrawer polylineDrawer;
	private CanvasDrawer canvasDrawer;

	private final int defaultWidth = 1900;
	private final int defaultHeight = 1200;

	/**
	 * Constructor for a given canvas.
	 * 
	 * @param c
	 *            canvas with predefined drawables.
	 */
	public View(Canvas c) {
		image = new BufferedImage(defaultWidth, defaultHeight,
				BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(defaultWidth, defaultHeight));

		currentPoint = new Point();
		updateArea = new Rectangle();

		polylineDrawer = new PolylineDrawer();
		fillDrawer = new FillDrawer();
		canvasDrawer = new CanvasDrawer();

		changeCanvas(c);

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
							canvas.addDrawable(currentPolyline);

						lastPoint = new Point(e.getPoint());
						currentPolyline.addPoint(lastPoint);

					} else if (SwingUtilities.isRightMouseButton(e)) {
						// On RMB-release finish drawing
						finishDrawing();
					}
				} else {
					if (fillingMode) {
						if (SwingUtilities.isLeftMouseButton(e))
							canvas.addDrawable(new Fill(e.getPoint(), color,
									connectivity));
					} else {
						if (SwingUtilities.isLeftMouseButton(e)) {
							drawingMode = true;
							lastPoint = new Point(e.getPoint());
							currentPolyline = new Polyline(lastPoint,
									strokeWidth);
						}
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
					updateArea.grow(strokeWidth, strokeWidth);
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
	 * Switches fill color
	 * 
	 * @param color
	 *            0 - red, 1 - blue
	 */
	public void switchColor(int color) {
		this.color = color;
	}

	/**
	 * Switches fill connectivity
	 * 
	 * @param connectivity
	 *            either 4 or 8
	 */
	public void switchConnectivity(int connectivity) {
		this.connectivity = connectivity;
	}

	/**
	 * Switches between drawing polylines and filling
	 * 
	 * @param mode
	 *            true - filling, false - drwaing polylines
	 */
	public void switchFilling(boolean mode) {
		fillingMode = mode;
	}

	/**
	 * Switches polyline width between 3 and 1
	 */
	public void switchWidth() {
		strokeWidth = 4 - strokeWidth;
	}

	/**
	 * Replace current canvas with another one
	 * 
	 * @param c
	 *            another canvas
	 */
	public void changeCanvas(Canvas c) {
		if (canvas != null)
			canvas.deleteObserver(this);

		this.canvas = c;
		canvas.addObserver(this);

		Graphics2D g = image.createGraphics();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, defaultWidth, defaultHeight);
		g.setStroke(new BasicStroke(strokeWidth));
		g.setColor(Color.BLACK);

		canvasDrawer.draw(canvas, image);

		repaint();
	}

	/**
	 * An implementation of Observer.update() method.
	 * 
	 * @see Observer#update(Observable, Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg != null && Drawable.class.isInstance(arg))
			switch (((Drawable) arg).getType()) {
			case FILL:
				fillDrawer.draw((Fill) arg, image);
				break;
			case POLYLINE:
				polylineDrawer.draw((Polyline) arg, image);
				break;
			case CANVAS:
				break;
			}

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
		g.drawImage(image, 0, 0, this);

		if (drawingMode) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(strokeWidth));
			g2d.drawLine(lastPoint.x, lastPoint.y, currentPoint.x,
					currentPoint.y);

		}
	}
}
