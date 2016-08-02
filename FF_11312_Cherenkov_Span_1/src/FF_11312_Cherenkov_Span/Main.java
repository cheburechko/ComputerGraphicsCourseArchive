package FF_11312_Cherenkov_Span;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import ru.nsu.cg.MainFrame;
import FF_11312_Cherenkov_Span.model.Canvas;
import FF_11312_Cherenkov_Span.serializers.CanvasSerializer;

/**
 * Main window class
 * 
 * @author Pavel Cherenkov
 */
public class Main extends MainFrame {
	private CanvasSerializer serializer;
	final private String extension = "spn";
	final private String fileDescription = "Span file";
	private Canvas canvas;
	private View view;

	/**
	 * Default constructor
	 */
	public Main() {
		super(800, 600, "Ultimate utility for drawing polylines and fills");

		try {
			addSubMenu("File", KeyEvent.VK_F);
			addMenuItem("File/Open", "Open drawing", KeyEvent.VK_O,
					"Folder.gif", "onOpen");
			addMenuItem("File/New", "Start new drawing", KeyEvent.VK_N,
					"New document.gif", "onNew");

			addMenuItem("File/Save", "Save current drawing", KeyEvent.VK_S,
					"Save.gif", "onSave");
			addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X,
					"Exit.gif", "onExit");

			addSubMenu("Polyline", KeyEvent.VK_E);
			addMenuItem("Polyline/Enable polyline", "Enable polyline",
					KeyEvent.VK_SPACE, "Modify.gif", "onEnablePolyline");
			addMenuItem("Polyline/Switch width",
					"Switch polyline width between 1 and 3", KeyEvent.VK_SPACE,
					"width.png", "onSwitchWidth");

			addSubMenu("Fill", KeyEvent.VK_E);
			addMenuItem("Fill/Enable filling", "Enable filling",
					KeyEvent.VK_SPACE, "Retort.gif", "onEnableFilling");
			addMenuItem("Fill/Choose red color", "Choose red color",
					KeyEvent.VK_SPACE, "Female symbol.gif", "onColorRed");
			addMenuItem("Fill/Choose blue color", "Choose blue color",
					KeyEvent.VK_SPACE, "Male symbol.gif", "onColorBlue");
			addMenuItem("Fill/Choose connectivity 8", "Choose connectivity 8",
					KeyEvent.VK_SPACE, "connectivity8.png",
					"onConnectivityEight");
			addMenuItem("Fill/Choose connectivity 4", "Choose connectivity 4",
					KeyEvent.VK_SPACE, "connectivity4.png",
					"onConnectivityFour");

			addSubMenu("Help", KeyEvent.VK_H);
			addMenuItem("Help/About...",
					"Shows program version and copyright information",
					KeyEvent.VK_A, "About.gif", "onAbout");

			addToolBarButton("File/New");
			addToolBarButton("File/Open");
			addToolBarButton("File/Save");
			addToolBarButton("File/Exit");
			addToolBarSeparator();
			addToolBarButton("Polyline/Enable polyline");
			addToolBarButton("Polyline/Switch width");
			addToolBarSeparator();
			addToolBarButton("Fill/Enable filling");
			addToolBarButton("Fill/Choose red color");
			addToolBarButton("Fill/Choose blue color");
			addToolBarButton("Fill/Choose connectivity 8");
			addToolBarButton("Fill/Choose connectivity 4");
			addToolBarSeparator();
			addToolBarButton("Help/About...");

			canvas = new Canvas();
			view = new View(canvas);
			JScrollPane pane = new JScrollPane(view);

			pane.getViewport().setPreferredSize(new Dimension(1280, 720));
			pane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

			serializer = new CanvasSerializer();
			add(pane);
			pack();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * File/New - creates clean canvas
	 */
	public void onNew() {
		canvas = new Canvas();
		view.changeCanvas(canvas);
	}

	/**
	 * File/Open - opens a .spn file
	 */
	public void onOpen() {
		view.finishDrawing();
		File file = this.getSaveFileName(extension, fileDescription);
		if (file == null)
			return;
		try {
			Scanner scanner = new Scanner(file);
			canvas = (Canvas) serializer.read(scanner);
			view.changeCanvas(canvas);
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(), "Exception",
					JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass()
							.getResource("resources/Error.gif")));
		}
	}

	/**
	 * File/Save - saves current drawing in a file
	 */
	public void onSave() {
		view.finishDrawing();
		File file = this.getSaveFileName(extension, fileDescription);
		if (file == null)
			return;
		try {
			if (file.exists())
				file.delete();

			file.createNewFile();

			FileWriter writer = new FileWriter(file);
			serializer.write(canvas, writer);
			writer.close();
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
	 * Polyline/Enable polyline - enables polyline mode
	 */
	public void onEnablePolyline() {
		view.switchFilling(false);
	}

	/**
	 * Polyline/Switch width - choose between width 3 and 1
	 */
	public void onSwitchWidth() {
		view.switchWidth();
	}

	/**
	 * Fill/Enable filling - enables filling mode
	 */
	public void onEnableFilling() {
		view.switchFilling(true);
	}

	/**
	 * Fill/Choose red color
	 */
	public void onColorRed() {
		view.switchColor(0);
	}

	/**
	 * Fill/Choose blue color
	 */
	public void onColorBlue() {
		view.switchColor(1);
	}

	/**
	 * Fill/Choose connectivity 4
	 */
	public void onConnectivityFour() {
		view.switchConnectivity(4);
	}

	/**
	 * Fill/Choose connectivity 8
	 */
	public void onConnectivityEight() {
		view.switchConnectivity(8);
	}

	/**
	 * Help/About... - shows some info
	 */
	public void onAbout() {
		JOptionPane
				.showMessageDialog(
						this,
						"Span, version 0.1\nCopyright � 2014 Pavel Cherenkov, FF, group 11312",
						"About Span", JOptionPane.INFORMATION_MESSAGE);
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
