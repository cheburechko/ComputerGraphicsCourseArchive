package FF_11312_Cherenkov_IS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import ru.nsu.cg.FileUtils;
import ru.nsu.cg.MainFrame;

/**
 * Main window class
 * 
 * @author Pavel Cherenkov
 */
public class Main extends MainFrame {
	final private String extension = "is";
	final private String fileDescription = "Isoline configuration";
	private View view;

	private FuncMap funcMap;
	private Palette palette;
	private Palette gridColor;
	private Palette isolineColor;
	private Pattern comments;
	private OptionDialog dialog;
	private final double defaultXmin = -10;
	private final double defaultXmax = 10;
	private final double defaultYmin = -10;
	private final double defaultYmax = 10;

	/**
	 * Default constructor
	 */
	public Main() {
		super(800, 600, "Ultimate utility for drawing isolines of the function");

		try {
			addSubMenu("File", KeyEvent.VK_F);
			addMenuItem("File/Open", "Open drawing", KeyEvent.VK_O,
					"Folder.gif", "onOpen");
			addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X,
					"Exit.gif", "onExit");

			addSubMenu("Options", KeyEvent.VK_O);
			addMenuItem("Options/Change settings",
					"Change grid and definition area settings", KeyEvent.VK_C,
					"Wrench.gif", "onChangeSettings");
			addMenuItem("Options/Simple map",
					"Change drawing mode to simple map", KeyEvent.VK_M,
					"SimpleMap.png", "onSimpleMap");
			addMenuItem("Options/Interpolation map",
					"Change drawing mode to interpolation map", KeyEvent.VK_I,
					"InterpolationMap.png", "onInterpolationMap");
			addMenuItem(
					"Options/Dithering map",
					"Change drawing mode to Floyd-Steinberg dithering interpolation map",
					KeyEvent.VK_D, "DitheringMap.png", "onDitheringMap");
			addMenuItem("Options/Toggle isolines", "Toggle isolines",
					KeyEvent.VK_L, "Isolines.png", "onToggleIsolines");
			addMenuItem("Options/Toggle mouse isoline", "Toggle mouse isoline",
					KeyEvent.VK_L, "MouseIsoline.png", "onToggleMouseIsoline");
			addMenuItem("Options/Toggle grid", "Toggle grid", KeyEvent.VK_L,
					"Grid.png", "onToggleGrid");

			addSubMenu("Help", KeyEvent.VK_H);
			addMenuItem("Help/About...",
					"Shows program version and copyright information",
					KeyEvent.VK_A, "About.gif", "onAbout");

			addToolBarButton("File/Open");
			addToolBarButton("File/Exit");
			addToolBarSeparator();
			addToolBarButton("Options/Change settings");
			addToolBarButton("Options/Simple map");
			addToolBarButton("Options/Interpolation map");
			addToolBarButton("Options/Dithering map");
			addToolBarButton("Options/Toggle isolines");
			addToolBarButton("Options/Toggle mouse isoline");
			addToolBarButton("Options/Toggle grid");
			addToolBarSeparator();
			addToolBarButton("Help/About...");

			setMinimumSize(new Dimension(View.defaultLegendWidth
					+ View.defaultTickWidth + 1, 200));

			comments = Pattern.compile("(\\s*//[^\n]*\n)*");
			gridColor = new Palette(1);
			gridColor.setColor(0, Color.GREEN);
			isolineColor = new Palette(1);

			view = null;
			openFile("default.is");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Exception",
					JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass()
							.getResource("resources/Error.gif")));
			System.exit(1);
		}
	}

	/**
	 * Initializes program from given file
	 * 
	 * @param filename
	 *            name of the file in data directory
	 * @throws IOException
	 *             raised if errors occurred opening file or parameters are not
	 *             formatted in a right way
	 */
	private void openFile(String filename) throws IOException {
		File file = new File(FileUtils.getDataDirectory(), filename);
		openFile(file);
	}

	/**
	 * Helper function to read ints from scanner with possible comments
	 * 
	 * @param scanner
	 *            source of input
	 * @param error
	 *            error string to return with exception
	 * @return next integer
	 * @throws IOException
	 *             raised if there is is no integer in the input
	 */
	private int getInt(Scanner scanner, String error) throws IOException {
		scanner.skip(comments);
		if (scanner.hasNextInt())
			return scanner.nextInt();
		else
			throw new IOException(error);
	}

	/**
	 * Initializes program from given file
	 * 
	 * @param file
	 *            file object
	 * @throws IOException
	 *             raised if errors occurred while reading file or parameters
	 *             are not formatted in a right way
	 */
	private void openFile(File file) throws IOException {
		Scanner scanner = new Scanner(file);
		int xgrid, ygrid, levels;

		xgrid = getInt(scanner, "X grid value not found");
		ygrid = getInt(scanner, "Y grid value not found");
		levels = getInt(scanner, "Amount of levels not found");

		if (funcMap == null)
			funcMap = new FuncMap(defaultXmin, defaultXmax, defaultYmin,
					defaultYmax, xgrid, ygrid, levels, new SinCosFunction());
		else
			funcMap = new FuncMap(funcMap.getXmin(), funcMap.getXmax(),
					funcMap.getYmin(), funcMap.getYmax(), xgrid, ygrid, levels,
					new SinCosFunction());

		palette = new Palette(levels + 1);

		for (int i = 0; i <= levels; i++) {
			int r = getInt(scanner, "Red color component for level " + i
					+ " not found");
			int g = getInt(scanner, "Green color component for level " + i
					+ " not found");
			int b = getInt(scanner, "Blue color component for level " + i
					+ " not found");
			palette.setColor(i, r, g, b);
		}

		int r = getInt(scanner, "Red color component for isoline not found");
		int g = getInt(scanner, "Green color component for isoline not found");
		int b = getInt(scanner, "Blue color component for isoline not found");
		isolineColor.setColor(0, r, g, b);

		if (view != null) {
			remove(view);
			isolineColor.deleteObserver(view);
			gridColor.deleteObserver(view);
		}
		if (dialog != null)
			dialog.dispose();
		view = new View(funcMap, palette, isolineColor, gridColor);
		add(view, BorderLayout.CENTER);
		pack();
	}

	/**
	 * File/Open - opens a .is file
	 */
	public void onOpen() {
		File file = this.getSaveFileName(extension, fileDescription);
		if (file == null)
			return;
		try {
			openFile(file);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Exception",
					JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass()
							.getResource("resources/Error.gif")));
		}
	}

	/**
	 * File/Exit - closes application
	 */
	public void onExit() {
		System.exit(0);
	}

	/**
	 * Options/Change settings - changes function map bounds and grid
	 */
	public void onChangeSettings() {
		if (dialog != null)
			dialog.dispose();
		dialog = new OptionDialog(this, funcMap, palette, gridColor,
				isolineColor);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	/**
	 * Options/Simple map - changes drawing mode to simple
	 */
	public void onSimpleMap() {
		view.setSimpleMap();
	}

	/**
	 * Options/Interpolation map - changes drawing mode to interpolation
	 */
	public void onInterpolationMap() {
		view.setInterpolationMap();
	}

	/**
	 * Options/Dithering map - changes drawing mode to dithering
	 */
	public void onDitheringMap() {
		view.setDitheringMap();
	}

	/**
	 * Options/Toggle isolines - toggles isoline rendering.
	 */
	public void onToggleIsolines() {
		view.switchIsolines();
	}

	/**
	 * Options/Toggle mouse isoline - toggles isoline rendering at mouse click.
	 */
	public void onToggleMouseIsoline() {
		view.switchMouseIsoline();
	}

	/**
	 * Options/Toggle grid - toggles grid rendering.
	 */
	public void onToggleGrid() {
		view.switchGrid();
	}

	/**
	 * Help/About... - shows some info
	 */
	public void onAbout() {
		JOptionPane
				.showMessageDialog(
						this,
						"IS, version 0.1\nCopyright � 2014 Pavel Cherenkov, FF, group 11312",
						"About IS", JOptionPane.INFORMATION_MESSAGE);
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
