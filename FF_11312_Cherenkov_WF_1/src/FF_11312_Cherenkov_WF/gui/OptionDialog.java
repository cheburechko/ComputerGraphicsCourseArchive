package FF_11312_Cherenkov_WF.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import FF_11312_Cherenkov_WF.model.Parameters;

/**
 * This is dialog for changing some rendering options.
 * 
 * @author Pavel Cherenkov
 * 
 */
public class OptionDialog extends JDialog implements ActionListener {
	private JFormattedTextField tStepsField, phiStepsField, scaleField,
			minTField, maxTField, minPhiField, maxPhiField, znField, zfField,
			swField, shField;
	private Parameters params;

	/**
	 * Helper function for inserting labels and fields
	 * 
	 * @param label
	 *            field description
	 * @param value
	 *            initial value
	 * @return JFormattedTextField object associated with this label
	 */
	private JFormattedTextField addField(String label, Object value) {
		add(new JLabel(label));
		JFormattedTextField field = new JFormattedTextField(value);
		add(field);
		return field;
	}

	/**
	 * Default constructor
	 * 
	 * @param params
	 *            rendering parameters
	 */
	public OptionDialog(Parameters params) {
		this.params = params;
		setLayout(new GridLayout(0, 2));

		tStepsField = addField("t steps", new Integer(params.gettSteps()));
		phiStepsField = addField("phi steps", new Integer(params.getPhiSteps()));
		scaleField = addField("scale", new Integer(params.getScale()));
		minTField = addField("min t", new Double(params.getMinT()));
		maxTField = addField("max t", new Double(params.getMaxT()));
		minPhiField = addField("min phi", new Double(params.getMinPhi()));
		maxPhiField = addField("max phi", new Double(params.getMaxPhi()));
		znField = addField("zn", new Double(params.getZn()));
		zfField = addField("zf", new Double(params.getZf()));
		swField = addField("sw", new Double(params.getSw()));
		shField = addField("sh", new Double(params.getSh()));

		JButton btn = new JButton("Apply");
		btn.setActionCommand("apply");
		btn.addActionListener(this);
		add(btn);

		btn = new JButton("Close");
		btn.setActionCommand("close");
		btn.addActionListener(this);
		add(btn);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("apply")) {
			int tSteps = ((Number) tStepsField.getValue()).intValue();
			int phiSteps = ((Number) phiStepsField.getValue()).intValue();
			int scale = ((Number) scaleField.getValue()).intValue();
			double minT = ((Number) minTField.getValue()).doubleValue();
			double maxT = ((Number) maxTField.getValue()).doubleValue();
			double minPhi = ((Number) minPhiField.getValue()).doubleValue();
			double maxPhi = ((Number) maxPhiField.getValue()).doubleValue();
			double zn = ((Number) znField.getValue()).doubleValue();
			double zf = ((Number) zfField.getValue()).doubleValue();
			double sw = ((Number) swField.getValue()).doubleValue();
			double sh = ((Number) shField.getValue()).doubleValue();

			if (tSteps < 1 || phiSteps < 1 || scale < 1) {
				JOptionPane.showMessageDialog(this,
						"Amount of steps and scale should be at least 1",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			params.update(tSteps, phiSteps, scale, minT, maxT, minPhi, maxPhi,
					zn, zf, sw, sh);
		} else if (e.getActionCommand().equals("close")) {
			dispose();
		}

	}
}
