package FF_11312_Cherenkov_LE;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import ru.nsu.cg.MainFrame;

/**
 * Main window class
 * 
 * @author Pavel Cherenkov
 */
public class Main extends MainFrame {
	private CanvasSerializer serializer;
	final private String extension = "le";
	final private String fileDescription = "LE file";
	private Canvas canvas;
	private View view;

	/**
	 * Default constructor
	 */
	public Main() {
		super(800, 600, "Ultimate utility for drawing polylines");

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

			addSubMenu("Edit", KeyEvent.VK_E);
			addMenuItem("Edit/Finish polyline", "Finish polyline",
					KeyEvent.VK_SPACE, "Modify.gif", "onFinishPolyline");
			addMenuItem(
					"Edit/Erase polyline",
					"Erase current polyline. If a new polyline is not started, delete the last one.",
					KeyEvent.VK_DELETE, "Erase.gif", "onErase");

			addSubMenu("Help", KeyEvent.VK_H);
			addMenuItem("Help/About...",
					"Shows program version and copyright information",
					KeyEvent.VK_A, "About.gif", "onAbout");

			addToolBarButton("File/New");
			addToolBarButton("File/Open");
			addToolBarButton("File/Save");
			addToolBarButton("File/Exit");
			addToolBarSeparator();
			addToolBarButton("Edit/Finish polyline");
			addToolBarButton("Edit/Erase polyline");
			addToolBarSeparator();
			addToolBarButton("Help/About...");

			canvas = new Canvas();
			view = new View(canvas);
			serializer = new CanvasSerializer();
			add(view);
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
	 * File/Open - opens a .le file
	 */
	public void onOpen() {
		view.finishDrawing();
		File file = this.getSaveFileName(extension, fileDescription);
		if (file == null)
			return;
		try {
			Scanner scanner = new Scanner(file);
			canvas = serializer.read(scanner);
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
	 * Edit/Finish polyline - finishes current polyline, starts drawing the new
	 * one
	 */
	public void onFinishPolyline() {
		view.finishDrawing();
	}

	/**
	 * Edit/Erase polyline - erases current polyline or the last one if haven't
	 * started drawing yet
	 */
	public void onErase() {
		view.finishDrawing();
		canvas.eraseLastPolyline();
	}

	/**
	 * Help/About... - shows some info
	 */
	public void onAbout() {
		JOptionPane
				.showMessageDialog(
						this,
						"LE, version 0.1\nCopyright � 2014 Pavel Cherenkov, FF, group 11312",
						"About LE", JOptionPane.INFORMATION_MESSAGE);
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
