package FF_11312_Cherenkov_WF.gui.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import FF_11312_Cherenkov_WF.coords.CoordTransformer;
import FF_11312_Cherenkov_WF.coords.SplineCoordTransformer;
import FF_11312_Cherenkov_WF.gui.BigImage;
import FF_11312_Cherenkov_WF.gui.BigImageClip;
import FF_11312_Cherenkov_WF.matrix.Point;
import FF_11312_Cherenkov_WF.model.BSpline;
import FF_11312_Cherenkov_WF.model.SolidOfRevolution;

/**
 * This is Bspline editor dialog
 * 
 * @author Pavel Cherenkov
 * 
 */
public class BSplineEditor extends JDialog implements ActionListener,
		ComponentListener {
	private double minx, maxx, minz, maxz;
	private JFormattedTextField minxField, maxxField, minzField, maxzField;
	private JFormattedTextField pointField, xField, zField;
	private DecimalFormat formatter;
	private JLabel[] zticks;
	private JLabel[] xticks;

	private BigImage image;
	private JPanel ztickPanel;
	private JPanel xtickPanel;
	private JPanel controlPanel;
	private JPanel windowPanel;
	private BSplineRenderer renderer;

	private Point controlledPoint;

	private BSpline spline;
	private SolidOfRevolution body;

	private final static int defaultHeight = 400;
	private final static int defaultWidth = 400;
	private final static int defaultZTickWidth = 100;
	private final static int defaultXTickHeight = 20;
	private final static int defaultControlWidth = 200;
	private final static int zticksAmount = 5;
	private final static int xticksAmount = 5;

	/**
	 * Default constructor
	 * 
	 * @param body
	 *            solid of revolution which spline will be edited
	 * */
	public BSplineEditor(SolidOfRevolution body) {
		setLayout(new BorderLayout());

		// formatter
		formatter = new DecimalFormat("0.0E0");
		formatter.setMaximumFractionDigits(5);
		formatter.setMinimumFractionDigits(5);

		// z ticks
		ztickPanel = new JPanel(new GridLayout(0, 1));
		int margin = defaultHeight / (2 * zticksAmount + 2);
		ztickPanel.setBorder(new EmptyBorder(margin, 0, margin, 0));
		ztickPanel.setPreferredSize(new Dimension(defaultZTickWidth,
				defaultHeight));

		zticks = new JLabel[zticksAmount];
		for (int i = zticksAmount - 1; i >= 0; i--) {
			zticks[i] = new JLabel(formatter.format(minz + (maxz - minz)
					/ (zticksAmount + 1) * (i + 1)));
			ztickPanel.add(zticks[i]);
		}

		// x ticks
		xtickPanel = new JPanel(new GridLayout(1, 0));
		margin = defaultWidth / (2 * xticksAmount + 2);
		xtickPanel.setBorder(new EmptyBorder(0, margin + defaultZTickWidth, 0,
				margin + defaultControlWidth));
		xtickPanel.setPreferredSize(new Dimension(defaultWidth,
				defaultXTickHeight));

		xticks = new JLabel[xticksAmount];
		for (int i = 0; i < xticksAmount; i++) {
			xticks[i] = new JLabel(formatter.format(minx + (maxx - minx)
					/ (xticksAmount + 1) * (i + 1)));
			xtickPanel.add(xticks[i]);
		}

		minx = 0;
		maxx = 1;
		minz = -1;
		maxz = 1;

		// control panel
		controlPanel = new JPanel(new GridLayout(0, 1));
		JPanel paramPane = new JPanel(new GridLayout(0, 2));

		paramPane.add(new JLabel("minX"));
		minxField = new JFormattedTextField(new Double(minx));
		paramPane.add(minxField);

		paramPane.add(new JLabel("maxX"));
		maxxField = new JFormattedTextField(new Double(maxx));
		paramPane.add(maxxField);

		paramPane.add(new JLabel("minZ"));
		minzField = new JFormattedTextField(new Double(minz));
		paramPane.add(minzField);

		paramPane.add(new JLabel("maxZ"));
		maxzField = new JFormattedTextField(new Double(maxz));
		paramPane.add(maxzField);

		paramPane.add(new JLabel("Point"));
		pointField = new JFormattedTextField(new Integer(0));
		paramPane.add(pointField);

		paramPane.add(new JLabel("x"));
		xField = new JFormattedTextField(new Double(0));
		paramPane.add(xField);

		paramPane.add(new JLabel("z"));
		zField = new JFormattedTextField(new Double(0));
		paramPane.add(zField);

		JPanel btnPane = new JPanel(new GridLayout(0, 1));

		JButton btn = new JButton("Apply boundaries");
		btn.setActionCommand("bounds");
		btn.addActionListener(this);
		btnPane.add(btn);

		btn = new JButton("Get point coordinates");
		btn.setActionCommand("getPoint");
		btn.addActionListener(this);
		btnPane.add(btn);

		btn = new JButton("Set point coordinates");
		btn.setActionCommand("setPoint");
		btn.addActionListener(this);
		btnPane.add(btn);

		btn = new JButton("Delete point");
		btn.setActionCommand("delete");
		btn.addActionListener(this);
		btnPane.add(btn);

		btn = new JButton("Add point");
		btn.setActionCommand("add");
		btn.addActionListener(this);
		btnPane.add(btn);

		btn = new JButton("Clear");
		btn.setActionCommand("clear");
		btn.addActionListener(this);
		btnPane.add(btn);

		btn = new JButton("Close");
		btn.setActionCommand("close");
		btn.addActionListener(this);
		btnPane.add(btn);

		controlPanel.add(paramPane);
		controlPanel.add(btnPane);
		controlPanel.setPreferredSize(new Dimension(defaultControlWidth,
				defaultHeight));

		// window
		image = new BigImage(defaultWidth, defaultHeight);
		windowPanel = new BigImageClip(image);
		windowPanel
				.setPreferredSize(new Dimension(defaultWidth, defaultHeight));
		renderer = new BSplineRenderer(minz, maxz, minx, maxx);

		windowPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				java.awt.Point p = e.getPoint();
				SplineCoordTransformer t = renderer.getTransform();
				t.init(renderer, image);
				controlledPoint = null;

				int i = 0;
				for (i = 0; i < spline.getPointCount(); i++) {
					Point point = spline.getPoint(i);
					int px = t.imageX(point.getX());
					int pz = t.imageY(point.getZ());
					if (Math.abs(px - p.x) <= BSplineRenderer.pSize
							&& Math.abs(pz - p.y) <= BSplineRenderer.pSize) {
						controlledPoint = point;
						break;
					}
				}
				if (controlledPoint == null) {
					controlledPoint = new Point(t.funcX(p.x), 0, t.funcY(p.y));
					spline.addPoint(controlledPoint);
				}
				xField.setValue(new Double(controlledPoint.getX()));
				zField.setValue(new Double(controlledPoint.getZ()));
				pointField.setValue(new Integer(i));
				draw();
			}

		});

		windowPanel.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				CoordTransformer t = renderer.getTransform();
				java.awt.Point p = e.getPoint();
				controlledPoint.setX(t.funcX(p.x));
				controlledPoint.setZ(t.funcY(p.y));
				xField.setValue(new Double(controlledPoint.getX()));
				zField.setValue(new Double(controlledPoint.getZ()));
				draw();
			}
		});

		add(windowPanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.EAST);
		add(ztickPanel, BorderLayout.WEST);
		add(xtickPanel, BorderLayout.SOUTH);

		spline = body.getSpline();
		this.body = body;
		this.addComponentListener(this);
		pack();
		draw();
	}

	/**
	 * Render bspline
	 */
	public void draw() {
		spline.compile();
		renderer.draw(spline, image);
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("bounds")) {
			double bufminx = ((Number) (minxField.getValue())).doubleValue();
			double bufmaxx = ((Number) (maxxField.getValue())).doubleValue();
			double bufminz = ((Number) (minzField.getValue())).doubleValue();
			double bufmaxz = ((Number) (maxzField.getValue())).doubleValue();
			if ((bufminx >= bufmaxx) || (bufminz >= bufmaxz)) {
				JOptionPane.showMessageDialog(this,
						"Min should be less than max", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			renderer.setMaxx(bufmaxx);
			renderer.setMaxz(bufmaxz);
			renderer.setMinx(bufminx);
			renderer.setMinz(bufminz);
			minz = bufminz;
			maxz = bufmaxz;
			minx = bufminx;
			maxx = bufmaxx;
			componentResized(null);
		} else if (command.equals("getPoint")) {
			int point = ((Number) (pointField.getValue())).intValue();
			if (point < 0 || point >= spline.getPointCount()) {
				JOptionPane.showMessageDialog(this,
						"Such point does not exist", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Point p = spline.getPoint(point);
			xField.setValue(new Double(p.getX()));
			zField.setValue(new Double(p.getZ()));
		} else if (command.equals("setPoint")) {
			int point = ((Number) (pointField.getValue())).intValue();
			if (point < 0 || point >= spline.getPointCount()) {
				JOptionPane.showMessageDialog(this,
						"Such point does not exist", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			double bufx = ((Number) xField.getValue()).doubleValue();
			double bufz = ((Number) zField.getValue()).doubleValue();
			if (bufz < renderer.getMinz() || bufz > renderer.getMaxz()
					|| bufx < renderer.getMinx() || bufx > renderer.getMaxx()) {
				JOptionPane.showMessageDialog(this, "Point out of bounds",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Point p = spline.getPoint(point);
			p.setZ(bufz);
			p.setX(bufx);
			draw();
		} else if (command.equals("add")) {
			double bufx = ((Number) xField.getValue()).doubleValue();
			double bufz = ((Number) zField.getValue()).doubleValue();
			if (bufz < renderer.getMinz() || bufz > renderer.getMaxz()
					|| bufx < renderer.getMinx() || bufx > renderer.getMaxx()) {
				JOptionPane.showMessageDialog(this, "Point out of bounds",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Point p = new Point(bufx, 0, bufz);
			spline.addPoint(p);
			draw();
		} else if (command.equals("delete")) {
			int point = ((Number) (pointField.getValue())).intValue();
			if (point < 0 || point >= spline.getPointCount()) {
				JOptionPane.showMessageDialog(this,
						"Such point does not exist", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			spline.removePoint(point);
			draw();
		} else if (command.equals("clear")) {
			spline.clearPoints();
			draw();
		} else if (command.equals("close")) {
			dispose();
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		image.setHeight(windowPanel.getHeight());
		image.setWidth(windowPanel.getWidth());

		int margin = image.getHeight() / (2 * zticksAmount + 2);
		ztickPanel.setBorder(new EmptyBorder(margin, 0, margin, 0));
		for (int i = zticksAmount - 1; i >= 0; i--) {
			zticks[i].setText(formatter.format(minz + (maxz - minz)
					/ (zticksAmount + 1) * (i + 1)));
		}

		margin = image.getWidth() / (2 * xticksAmount + 2);
		xtickPanel.setBorder(new EmptyBorder(0, margin + ztickPanel.getWidth(),
				0, margin + controlPanel.getWidth()));
		for (int i = 0; i < xticksAmount; i++) {
			xticks[i].setText(formatter.format(minx + (maxx - minx)
					/ (xticksAmount + 1) * (i + 1)));
		}

		SplineCoordTransformer t = renderer.getTransform();
		t.init(renderer, image);

		draw();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}
}
