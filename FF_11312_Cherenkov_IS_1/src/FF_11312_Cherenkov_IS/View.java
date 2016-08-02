package FF_11312_Cherenkov_IS;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * View class which is responsible for drawing color map and legend and for
 * mouse input
 * 
 * @author Pavel Cherenkov
 * 
 */
public class View extends JPanel implements Observer, ComponentListener {
	private BufferedImage image;
	private BufferedImage legendImage;

	private SimpleMapDrawer simpleDrawer;
	private InterpolationMapDrawer interpolationDrawer;
	private DitheringMapDrawer ditheringDrawer;
	private MapDrawer drawer;
	private IsolineDrawer isolineDrawer;
	private GridDrawer gridDrawer;

	private FuncMap funcMap;
	private FuncMap legendFuncMap;
	private CoordTransformer trans;

	private boolean gridMode;
	private boolean isolineMode;
	private boolean mouseIsoline;
	private Double mouseValue;

	private static final int defaultWidth = 600;
	// / Default width of the legend bar
	public static final int defaultLegendWidth = 50;
	// / Default width of the level marks bar
	public static final int defaultTickWidth = 80;
	private static final int defaultHeight = 600;

	private BigImageClip imageClip;
	private JPanel legendPanel;
	private BigImageClip legendClip;
	private JPanel tickPanel;
	private JLabel[] ticks;

	private JLabel status;
	private DecimalFormat formatter;

	/**
	 * Default constructor.
	 * 
	 * @param map
	 *            function map
	 * @param palette
	 *            color palette
	 * @param isolineColor
	 *            color of isolines
	 * @param gridColor
	 *            color of grid
	 */
	public View(FuncMap map, Palette palette, Palette isolineColor,
			Palette gridColor) {

		this.funcMap = map;

		// Modes
		gridMode = false;
		isolineMode = false;
		mouseIsoline = false;
		mouseValue = null;

		formatter = new DecimalFormat("0.0E0");
		formatter.setMaximumFractionDigits(5);
		formatter.setMinimumFractionDigits(5);

		// Drawers
		interpolationDrawer = new InterpolationMapDrawer(palette);
		simpleDrawer = new SimpleMapDrawer(palette);
		ditheringDrawer = new DitheringMapDrawer(palette);
		drawer = interpolationDrawer;
		isolineDrawer = new IsolineDrawer(isolineColor);
		gridDrawer = new GridDrawer(gridColor);

		// Legend grid function
		legendFuncMap = new FuncMap(0, 1, 0, 1, 2, 100, map.getLevels(),
				new LinearVerticalFunction());

		// Legend panel
		legendPanel = new JPanel(new BorderLayout());
		legendPanel.setPreferredSize(new Dimension(defaultLegendWidth
				+ defaultTickWidth, defaultHeight));

		// Panel with tick markers
		tickPanel = new JPanel(new GridLayout(0, 1));
		int margin = defaultHeight / (2 * funcMap.getLevels() + 2);
		tickPanel.setBorder(new EmptyBorder(margin, 0, margin, 0));
		tickPanel.setPreferredSize(new Dimension(defaultTickWidth,
				defaultHeight));

		ticks = new JLabel[funcMap.getLevels()];
		for (int i = funcMap.getLevels() - 1; i >= 0; i--) {
			ticks[i] = new JLabel(formatter.format(funcMap.getLevelValue(i)));
			tickPanel.add(ticks[i]);
		}

		// Legend color map
		legendImage = new BufferedImage(defaultLegendWidth, defaultHeight);
		legendClip = new BigImageClip(legendImage);
		legendClip.setSize(defaultLegendWidth, defaultHeight);
		legendClip.setPreferredSize(legendClip.getSize());

		legendPanel.add(tickPanel, BorderLayout.CENTER);
		legendPanel.add(legendClip, BorderLayout.LINE_END);

		// Actual color map
		image = new BufferedImage(defaultWidth, defaultHeight);
		imageClip = new BigImageClip(image);
		imageClip.setSize(defaultWidth, defaultHeight);
		imageClip.setPreferredSize(imageClip.getSize());

		// Transformation
		trans = new CoordTransformer();
		trans.init(funcMap, image);

		// Status bar
		status = new JLabel("Welcome!");

		setLayout(new BorderLayout());
		add(imageClip, BorderLayout.CENTER);
		add(legendPanel, BorderLayout.LINE_END);
		add(status, BorderLayout.PAGE_END);

		this.addComponentListener(this);

		funcMap.addObserver(this);
		gridColor.addObserver(this);
		isolineColor.addObserver(this);
		palette.addObserver(this);
		draw();

		imageClip.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (mouseIsoline) {
					Point p = e.getPoint();
					double x = trans.funcX(p.x);
					double y = trans.funcY(p.y);
					mouseValue = funcMap.getValue(x, y);
					repaint();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});

		imageClip.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				Point p = e.getPoint();
				double x = trans.funcX(p.x);
				double y = trans.funcY(p.y);
				double val = funcMap.getValue(x, y);
				status.setText("x=" + formatter.format(x) + " y="
						+ formatter.format(y) + " f=" + formatter.format(val));
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = e.getPoint();
				double x = trans.funcX(p.x);
				double y = trans.funcY(p.y);
				double value = funcMap.getValue(x, y);
				status.setText("x=" + formatter.format(x) + " y="
						+ formatter.format(y) + " f=" + formatter.format(value));
				if (mouseIsoline) {
					mouseValue = value;
					repaint();
				}
			}
		});
	}

	/**
	 * An implementation of {@link Observer#update(Observable, Object)} method.
	 * 
	 * @see Observer#update(Observable, Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o.equals(funcMap)) {
			for (int i = funcMap.getLevels() - 1; i >= 0; i--) {
				ticks[i].setText(formatter.format(funcMap.getLevelValue(i)));
			}
			trans.init(funcMap, image);
		}
		repaint();
	}

	/**
	 * Updates color map and legend
	 */
	public void draw() {
		drawer.draw(funcMap, image);
		drawer.draw(legendFuncMap, legendImage);
		isolineDrawer.draw(legendFuncMap, legendImage);
		if ((mouseValue != null) && (mouseIsoline))
			isolineDrawer.drawIsoline(funcMap, image, mouseValue);
		if (isolineMode)
			isolineDrawer.draw(funcMap, image);
		if (gridMode)
			gridDrawer.draw(funcMap, image);
	}

	/**
	 * Switches drawing mode to simple.
	 */
	public void setSimpleMap() {
		drawer = simpleDrawer;
		repaint();
	}

	/**
	 * Switches drawing mode to interpolation.
	 */
	public void setInterpolationMap() {
		drawer = interpolationDrawer;
		repaint();
	}

	/**
	 * Swithces drawing mode to Floyd-Steinberg dithering
	 */
	public void setDitheringMap() {
		drawer = ditheringDrawer;
		repaint();
	}

	/**
	 * Switches on/off isolines
	 */
	public void switchIsolines() {
		isolineMode = !isolineMode;
		repaint();
	}

	/**
	 * Switces on/off grid
	 */
	public void switchGrid() {
		gridMode = !gridMode;
		repaint();
	}

	/**
	 * Switches on/off drawing isloine at mouse location.
	 */
	public void switchMouseIsoline() {
		mouseIsoline = !mouseIsoline;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		draw();
		super.paintComponent(g);
	}

	/**
	 * An implementation of
	 * {@link ComponentListener#componentResized(ComponentEvent)} method.
	 * 
	 * @see ComponentListener#componentResized(ComponentEvent)
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		image.setWidth(imageClip.getWidth());
		image.setHeight(imageClip.getHeight());

		legendImage.setHeight(legendClip.getHeight());
		legendImage.setWidth(legendClip.getWidth());

		trans.init(funcMap, image);

		int margin = legendPanel.getHeight() / (2 * funcMap.getLevels() + 2);
		tickPanel.setBorder(new EmptyBorder(margin, 0, margin, 0));
	}

	/**
	 * An implementation of
	 * {@link ComponentListener#componentMoved(ComponentEvent)} method.
	 * 
	 * @see ComponentListener#componentMoved(ComponentEvent)
	 */
	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * An implementation of
	 * {@link ComponentListener#componentShown(ComponentEvent)} method.
	 * 
	 * @see ComponentListener#componentShown(ComponentEvent)
	 */
	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * An implementation of
	 * {@link ComponentListener#componentHidden(ComponentEvent)} method.
	 * 
	 * @see ComponentListener#componentHidden(ComponentEvent)
	 */
	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}
}
