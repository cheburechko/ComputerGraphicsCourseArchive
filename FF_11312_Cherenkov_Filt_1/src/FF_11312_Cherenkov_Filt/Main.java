package FF_11312_Cherenkov_Filt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ru.nsu.cg.FileUtils;
import ru.nsu.cg.MainFrame;

/**
 * Main window class
 * 
 * @author Pavel Cherenkov
 */
public class Main extends MainFrame {
	final private String extension = "png";
	final private String fileDescription = "PNG image";
	private View view;

	/**
	 * Default constructor
	 */
	public Main() {
		super(800, 600, "Ultimate utility for applying filters to images");

		try {
			addSubMenu("File", KeyEvent.VK_F);
			addMenuItem("File/Open", "Open image", KeyEvent.VK_O, "Folder.gif",
					"onOpen");
			addMenuItem("File/Save", "Save destination image", KeyEvent.VK_S,
					"Save.gif", "onSave");
			addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X,
					"Exit.gif", "onExit");

			addSubMenu("Copy", KeyEvent.VK_C);
			addMenuItem("Copy/Source -> Destination",
					"Copy image from left to right", KeyEvent.VK_U,
					"Go forward.gif", "onSrcToDst");
			addMenuItem("Copy/Destination -> Source",
					"Copy image from right to left", KeyEvent.VK_D,
					"Go back.gif", "onDstToSrc");

			addSubMenu("Filter", KeyEvent.VK_F);
			addMenuItem("Filter/Identical", "Apply identical filter",
					KeyEvent.VK_M, "identical.png", "onIdentical");
			addMenuItem("Filter/Monochrome", "Apply monochrome filter",
					KeyEvent.VK_M, "monochrome.png", "onMonochrome");
			addMenuItem("Filter/Outline", "Apply outline filter",
					KeyEvent.VK_L, "outline.png", "onOutline");
			addMenuItem("Filter/Blur", "Apply blur filter", KeyEvent.VK_B,
					"blur.png", "onBlur");
			addMenuItem("Filter/Sharpen", "Apply sharpen filter",
					KeyEvent.VK_M, "sharpen.png", "onSharpen");
			addMenuItem("Filter/Negative", "Apply negative filter",
					KeyEvent.VK_M, "negative.png", "onNegative");
			addMenuItem("Filter/Embossing", "Apply embossing filter",
					KeyEvent.VK_M, "embossing.png", "onEmbossing");
			addMenuItem("Filter/Watercolor", "Apply watercolor filter",
					KeyEvent.VK_M, "watercolor.png", "onWatercolor");
			addMenuItem("Filter/Gamma correction",
					"Apply gamma correction filter", KeyEvent.VK_M,
					"gamma.png", "onGamma");
			addMenuItem("Filter/Custom", "Apply custom filter", KeyEvent.VK_M,
					"Tune.gif", "onCustom");

			addSubMenu("Help", KeyEvent.VK_H);
			addMenuItem("Help/About...",
					"Shows program version and copyright information",
					KeyEvent.VK_A, "About.gif", "onAbout");

			addToolBarButton("File/Open");
			addToolBarButton("File/Save");
			addToolBarButton("File/Exit");
			addToolBarSeparator();
			addToolBarButton("Copy/Destination -> Source");
			addToolBarButton("Copy/Source -> Destination");
			addToolBarSeparator();
			addToolBarButton("Filter/Identical");
			addToolBarButton("Filter/Monochrome");
			addToolBarButton("Filter/Outline");
			addToolBarButton("Filter/Blur");
			addToolBarButton("Filter/Sharpen");
			addToolBarButton("Filter/Negative");
			addToolBarButton("Filter/Embossing");
			addToolBarButton("Filter/Watercolor");
			addToolBarButton("Filter/Gamma correction");
			addToolBarButton("Filter/Custom");
			addToolBarSeparator();
			addToolBarButton("Help/About...");

			view = new View(ImageIO.read(new File(FileUtils.getDataDirectory(),
					"default.png")));
			add(view, BorderLayout.CENTER);
			pack();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Exception",
					JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass()
							.getResource("resources/Error.gif")));
			System.exit(1);
		}
	}

	/**
	 * File/Open - opens a .png file
	 */
	public void onOpen() {
		File file = this.getOpenFileName(extension, fileDescription);
		if (file == null)
			return;
		try {
			BufferedImage image = ImageIO.read(file);
			remove(view);
			view = new View(image);
			add(view, BorderLayout.CENTER);
			pack();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Exception",
					JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass()
							.getResource("resources/Error.gif")));
		}
	}

	/**
	 * File/Save - saves right image as .png
	 */
	public void onSave() {
		File file = this.getSaveFileName(extension, fileDescription);
		if (file == null)
			return;
		try {
			ImageIO.write(view.getDestinationImage(), extension, file);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Exception",
					JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass()
							.getResource("resources/Error.gif")));
		}
	}

	/**
	 * Copy/Source -&gt; Destination - copies image from left to right
	 */
	public void onSrcToDst() {
		view.srcToDst();
	}

	/**
	 * Copy/Destination -&gt; Source - copies image from right to left
	 */
	public void onDstToSrc() {
		view.dstToSrc();
	}

	/**
	 * Filter/Monochrome -applies black and white filter
	 */
	public void onMonochrome() {
		view.applyMonochromeFilter();
	}

	/**
	 * Filter/Outline - shows offset slider and applies outline filter
	 */
	public void onOutline() {
		JSlider slider = new JSlider();
		slider.setMajorTickSpacing(15);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setPreferredSize(new Dimension(500, 50));
		slider.setMaximum(255);
		slider.setMinimum(0);
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				view.applyOutlineFilter(slider.getValue());
			}
		});
		slider.setValue(0);

		JOptionPane pane = new JOptionPane();
		pane.setMessage(new Object[] { "Select background value: ", slider });
		pane.setMessageType(JOptionPane.QUESTION_MESSAGE);
		pane.setOptionType(JOptionPane.DEFAULT_OPTION);
		JDialog dialog = pane.createDialog(this, "Outline filter");
		dialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
	}

	/**
	 * Filter/Blur - applies blur filter
	 */
	public void onBlur() {
		view.applyBlurFilter();
	}

	/**
	 * Filter/Sharpen - applies sharpening filter
	 */
	public void onSharpen() {
		view.applySharpenFilter();
	}

	/**
	 * Filter/Identical - applies identical filter
	 */
	public void onIdentical() {
		view.applyIdenticalFilter();
	}

	/**
	 * Filter/Negative - applies negative filter
	 */
	public void onNegative() {
		view.applyNegativeFilter();
	}

	/**
	 * Filter/Embossing - applies embossing filter
	 */
	public void onEmbossing() {
		view.applyEmbossingFilter();
	}

	/**
	 * Filter/Watercolor - applies watercolor filter
	 */
	public void onWatercolor() {
		view.applyWatercolorFilter();
	}

	/**
	 * Filter/Gamma - shows gamma slider and applies gamma filter
	 */
	public void onGamma() {
		JSlider slider = new JSlider();
		// slider.setMajorTickSpacing(10);
		// slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setPreferredSize(new Dimension(500, 50));
		slider.setMaximum(100);
		slider.setMinimum(1);

		Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
		labelTable.put(1, new JLabel("0.1"));
		for (int i = 1; i <= 10; i++) {
			labelTable.put(i * 10, new JLabel(Double.toString(i)));
		}
		slider.setLabelTable(labelTable);

		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				view.applyGammaCorrectionFilter(slider.getValue() / 10.);
			}
		});
		slider.setValue(10);

		JOptionPane pane = new JOptionPane();
		pane.setMessage(new Object[] { "Select gamma value: ", slider });
		pane.setMessageType(JOptionPane.QUESTION_MESSAGE);
		pane.setOptionType(JOptionPane.DEFAULT_OPTION);
		JDialog dialog = pane.createDialog(this, "Gamma correction filter");
		dialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
		dialog.setVisible(true);
	}

	/**
	 * Filter/Custom - shows dialog for constructing custom 3x3 matrix filter
	 */
	public void onCustom() {
		CustomFilterDialog dialog = new CustomFilterDialog(this, view);
		dialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	/**
	 * File/Exit - closes application
	 */
	public void onExit() {
		System.exit(0);
	}

	/**
	 * Help/About... - shows some info
	 */
	public void onAbout() {
		JOptionPane
				.showMessageDialog(
						this,
						"Filt, version 1.0\nCopyright � 2014 Pavel Cherenkov, FF, group 11312",
						"About Filt", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Application main entry point
	 * 
	 * @param args
	 *            command line arguments (unused)
	 */
	public static void main(String[] args) {
		Main mainFrame = new Main();
		mainFrame.setVisible(true);
	}
}
