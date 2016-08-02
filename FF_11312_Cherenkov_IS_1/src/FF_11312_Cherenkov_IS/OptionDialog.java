package FF_11312_Cherenkov_IS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * This is a dialog for changing function map options.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class OptionDialog extends JDialog implements ActionListener {
	private JSpinner xmin, xmax, ymin, ymax, xgrid, ygrid, levelColor;
	private JLabel xminLabel, xmaxLabel, yminLabel, ymaxLabel, xgridLabel,
			ygridLabel, levelColorLabel;
	private FuncMap map;
	private Palette gridColor;
	private Palette isolineColor;
	private Palette palette;

	/**
	 * Default constructor
	 * 
	 * @param frame
	 *            parent frame
	 * @param map
	 *            function map
	 * @param palette
	 *            colors of levels
	 * @param gridColor
	 *            grid color
	 * @param isolineColor
	 *            color of isolines
	 */
	public OptionDialog(Frame frame, final FuncMap map, Palette palette,
			Palette gridColor, Palette isolineColor) {
		super(frame);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Settings");
		// setSize(new Dimension(200, 200));

		NumberFormat areaFormat = NumberFormat.getNumberInstance();
		NumberFormat gridFormat = NumberFormat.getIntegerInstance();
		SpinnerNumberModel xminModel = new SpinnerNumberModel(map.getXmin(),
				Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.);
		SpinnerNumberModel xmaxModel = new SpinnerNumberModel(map.getXmax(),
				Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.);
		SpinnerNumberModel yminModel = new SpinnerNumberModel(map.getYmin(),
				Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.);
		SpinnerNumberModel ymaxModel = new SpinnerNumberModel(map.getYmax(),
				Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.);
		SpinnerNumberModel xgridModel = new SpinnerNumberModel(map.getXgrid(),
				2, 2000, 1);
		SpinnerNumberModel ygridModel = new SpinnerNumberModel(map.getYgrid(),
				2, 2000, 1);
		SpinnerNumberModel colorLevelModel = new SpinnerNumberModel(0, 0,
				map.getLevels(), 1);

		int width = 10;
		xmin = new JSpinner(xminModel);
		xmax = new JSpinner(xmaxModel);
		ymin = new JSpinner(yminModel);
		ymax = new JSpinner(ymaxModel);
		xgrid = new JSpinner(xgridModel);
		ygrid = new JSpinner(ygridModel);
		levelColor = new JSpinner(colorLevelModel);
		((JSpinner.DefaultEditor) xmin.getEditor()).getTextField().setColumns(
				width);
		((JSpinner.DefaultEditor) xmax.getEditor()).getTextField().setColumns(
				width);
		((JSpinner.DefaultEditor) ymin.getEditor()).getTextField().setColumns(
				width);
		((JSpinner.DefaultEditor) ymax.getEditor()).getTextField().setColumns(
				width);
		((JSpinner.DefaultEditor) xgrid.getEditor()).getTextField().setColumns(
				width);
		((JSpinner.DefaultEditor) ygrid.getEditor()).getTextField().setColumns(
				width);
		((JSpinner.DefaultEditor) levelColor.getEditor()).getTextField()
				.setColumns(width);

		xminLabel = new JLabel("Xmin");
		xmaxLabel = new JLabel("Xmax");
		yminLabel = new JLabel("Ymin");
		ymaxLabel = new JLabel("Ymax");
		xgridLabel = new JLabel("Xgrid");
		ygridLabel = new JLabel("Ygrid");
		levelColorLabel = new JLabel("Color level");

		xminLabel.setLabelFor(xmin);
		xmaxLabel.setLabelFor(xmax);
		yminLabel.setLabelFor(ymin);
		ymaxLabel.setLabelFor(ymax);
		xgridLabel.setLabelFor(xgrid);
		ygridLabel.setLabelFor(ygrid);
		levelColorLabel.setLabelFor(levelColor);

		int gap = 5;
		JPanel labelPanel = new JPanel(new GridLayout(0, 1, gap, gap));
		labelPanel.setBorder(new EmptyBorder(gap, gap, gap, gap));
		labelPanel.add(xminLabel);
		labelPanel.add(xmaxLabel);
		labelPanel.add(yminLabel);
		labelPanel.add(ymaxLabel);
		labelPanel.add(xgridLabel);
		labelPanel.add(ygridLabel);
		labelPanel.add(levelColorLabel);

		JPanel fieldPanel = new JPanel(new GridLayout(0, 1, gap, gap));
		fieldPanel.setBorder(new EmptyBorder(gap, gap, gap, gap));
		fieldPanel.add(xmin);
		fieldPanel.add(xmax);
		fieldPanel.add(ymin);
		fieldPanel.add(ymax);
		fieldPanel.add(xgrid);
		fieldPanel.add(ygrid);
		fieldPanel.add(levelColor);

		JButton setButton = new JButton("Set bounds and grid");
		setButton.setActionCommand("set");
		setButton.addActionListener(this);

		JButton closeButton = new JButton("Close");
		closeButton.setActionCommand("close");
		closeButton.addActionListener(this);

		JButton gridButton = new JButton("Set grid color");
		gridButton.setActionCommand("grid");
		gridButton.addActionListener(this);

		JButton isolineButton = new JButton("Set isoline color");
		isolineButton.setActionCommand("isoline");
		isolineButton.addActionListener(this);

		JButton levelButton = new JButton("Set level color");
		levelButton.setActionCommand("level");
		levelButton.addActionListener(this);

		JPanel buttonPanel = new JPanel(new GridLayout(0, 1, gap, gap));

		buttonPanel.add(setButton);
		buttonPanel.add(isolineButton);
		buttonPanel.add(gridButton);
		buttonPanel.add(levelButton);
		buttonPanel.add(closeButton);

		add(labelPanel, BorderLayout.CENTER);
		add(fieldPanel, BorderLayout.LINE_END);
		add(buttonPanel, BorderLayout.PAGE_END);

		this.map = map;
		this.palette = palette;
		this.gridColor = gridColor;
		this.isolineColor = isolineColor;

		this.setModalityType(ModalityType.APPLICATION_MODAL);

		pack();
	}

	/**
	 * An implementation of {@link ActionListener#actionPerformed(ActionEvent)}
	 * method.
	 * 
	 * @see ActionListener#actionPerformed(ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("set")) {
			int newXgrid = ((Number) xgrid.getValue()).intValue();
			int newYgrid = ((Number) ygrid.getValue()).intValue();
			double newXmin = ((Number) xmin.getValue()).doubleValue();
			double newXmax = ((Number) xmax.getValue()).doubleValue();
			double newYmin = ((Number) ymin.getValue()).doubleValue();
			double newYmax = ((Number) ymax.getValue()).doubleValue();
			if (newXgrid < 2 || newYgrid < 2)
				JOptionPane.showMessageDialog(this,
						"Grid values should be bigger than 0", "Error",
						JOptionPane.ERROR_MESSAGE);
			else if (newXmin >= newXmax || newYmin >= newYmax)
				JOptionPane.showMessageDialog(this,
						"Min values should be less than max values", "Error",
						JOptionPane.ERROR_MESSAGE);
			else {
				map.setNewParameters(newXmin, newXmax, newYmin, newYmax,
						newXgrid, newYgrid);
			}
		} else if (e.getActionCommand().equals("close")) {
			dispose();
		} else if (e.getActionCommand().equals("grid")) {
			Color c = JColorChooser.showDialog(this, "Set grid color",
					gridColor.getColorObject(0));
			if (c != null)
				gridColor.setColor(0, c);
		} else if (e.getActionCommand().equals("isoline")) {
			Color c = JColorChooser.showDialog(this, "Set isoline color",
					isolineColor.getColorObject(0));
			if (c != null)
				isolineColor.setColor(0, c);
		} else if (e.getActionCommand().equals("level")) {
			int level = ((Number) levelColor.getValue()).intValue();
			if (level < 0 || level > map.getLevels())
				JOptionPane.showMessageDialog(this,
						"Level should be between 0 and " + map.getLevels()
								+ " inclusively", "Error",
						JOptionPane.ERROR_MESSAGE);
			else {
				Color c = JColorChooser.showDialog(this, "Set level color",
						palette.getColorObject(level));
				if (c != null)
					palette.setColor(level, c);
			}
		}

	}
}
