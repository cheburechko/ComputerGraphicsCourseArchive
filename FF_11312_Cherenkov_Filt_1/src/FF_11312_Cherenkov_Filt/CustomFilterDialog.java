package FF_11312_Cherenkov_Filt;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import FF_11312_Cherenkov_Filt.filter.matrix.CustomMatrixFilter;

/**
 * Dialog for configuring custom filter
 * 
 * @author Pavel Cherenkov
 * 
 */
public class CustomFilterDialog extends JDialog implements ActionListener {
	private CustomMatrixFilter generic;
	private View view;
	private JFormattedTextField matrixCells[];
	private JFormattedTextField offset;
	private JFormattedTextField divisor;

	/**
	 * Default constructor
	 * 
	 * @param parent
	 *            parent frame
	 * @param view
	 *            view wiht images
	 */
	public CustomFilterDialog(JFrame parent, View view) {
		super(parent);
		setTitle("Custom filter");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.view = view;
		generic = view.getCustomMatrixFilter();
		setLayout(new GridLayout(0, 1));

		JPanel matrixPane = new JPanel(new GridLayout(3, 3));

		matrixCells = new JFormattedTextField[9];
		for (int i = 0; i < 9; i++) {
			matrixCells[i] = new JFormattedTextField(new Integer(
					generic.getCell(i % 3 - 1, i / 3 - 1)));
			matrixPane.add(matrixCells[i]);
		}

		JPanel paramPane = new JPanel(new GridLayout(0, 2));
		paramPane.add(new JLabel("Offset:"));
		offset = new JFormattedTextField(new Integer(generic.getOffset()));
		paramPane.add(offset);

		paramPane.add(new JLabel("Divisor"));
		divisor = new JFormattedTextField(new Integer(generic.getDivisor()));
		paramPane.add(divisor);

		JPanel btnPane = new JPanel(new GridLayout(0, 1));
		JButton computeDivisor = new JButton("Compute divisor");
		computeDivisor.setActionCommand("divisor");
		computeDivisor.addActionListener(this);
		btnPane.add(computeDivisor);

		JButton apply = new JButton("Apply");
		apply.setActionCommand("apply");
		apply.addActionListener(this);
		btnPane.add(apply);

		JButton close = new JButton("Close");
		close.setActionCommand("close");
		close.addActionListener(this);
		btnPane.add(close);

		add(matrixPane);
		add(paramPane);
		add(btnPane);
		pack();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("divisor")) {
			int answer = 0;
			for (int i = 0; i < 9; i++) {
				answer += ((Number) (matrixCells[i].getValue())).intValue();
			}
			if (answer == 0)
				answer = 1;
			divisor.setValue(new Integer(answer));
		} else if (e.getActionCommand().equals("apply")) {
			int divisorValue = ((Number) divisor.getValue()).intValue();
			int offsetValue = ((Number) offset.getValue()).intValue();
			if (offsetValue < 0 || offsetValue > 255) {
				JOptionPane.showMessageDialog(this,
						"Offset must be in range between 0 and 255", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (divisorValue <= 0) {
				JOptionPane.showMessageDialog(this,
						"Divisor must be a positive number", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			for (int i = 0; i < 9; i++) {
				generic.setCell(i % 3 - 1, i / 3 - 1,
						((Number) matrixCells[i].getValue()).intValue());
			}
			generic.setDivisor(divisorValue);
			generic.setOffset(offsetValue);
			view.applyCustomFilter();
		} else if (e.getActionCommand().equals("close")) {
			dispose();
		}

	}
}
