package FF_11312_Cherenkov_PR.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JColorChooser;
import javax.swing.JFormattedTextField;

import FF_11312_Cherenkov_PR.model.Material;

/**
 * Dialog for editing materials
 * 
 * @author Pavel Cherenkov
 * 
 */
public class MaterialDialog extends AbstractOptionDialog {
	private JFormattedTextField kPhongField;
	private Material mat;

	/**
	 * Default constructor
	 * 
	 * @param m
	 *            material
	 */
	public MaterialDialog(Material m) {
		super();
		mat = m;

		kPhongField = addField("Reflection power", new Double(m.getkPhong()));

		addButton("Apply", "apply");
		addButton("Close", "close");
		addButton("Set diffusion color", "diff");
		addButton("Set specular color", "spec");
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("apply")) {

			double kPhong = ((Number) kPhongField.getValue()).doubleValue();
			mat.setkPhong(kPhong);

		} else if (e.getActionCommand().equals("close")) {
			dispose();
		} else if (e.getActionCommand().equals("diff")) {
			Color c = JColorChooser.showDialog(this, "Set diffusion color",
					mat.getDiffusion());
			if (c != null)
				mat.setDiffusion(c);

		} else if (e.getActionCommand().equals("spec")) {
			Color c = JColorChooser.showDialog(this, "Set specular color",
					mat.getSpecular());
			if (c != null)
				mat.setSpecular(c);
		}

	}
}
