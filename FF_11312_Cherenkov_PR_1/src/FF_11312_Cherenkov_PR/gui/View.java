package FF_11312_Cherenkov_PR.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import FF_11312_Cherenkov_PR.model.Camera;
import FF_11312_Cherenkov_PR.model.Parameters;
import FF_11312_Cherenkov_PR.model.Scene;
import FF_11312_Cherenkov_PR.rendering.PolygonRenderer;

/**
 * This class is responsible for showing 3D scene and mouse control
 * 
 * @author Pavel Cherenkov
 * 
 */
public class View extends JPanel implements ComponentListener, Observer {
	private Scene scene;
	private Camera camera;
	private BigImage image;
	private BigImageClip imageClip;
	private int margin;
	private PolygonRenderer renderer;

	private Point initVec;

	public static final int defaultWidth = 800;
	public static final int defaultHeight = 600;
	public static final double screenAngle = Math.PI;

	/**
	 * Default constructor
	 * 
	 * @param params
	 *            rendering parameters
	 */
	public View(Parameters params) {

		scene = new Scene();
		camera = new Camera(params);
		renderer = new PolygonRenderer();
		margin = 10;

		image = new BigImage(defaultWidth, defaultHeight);
		imageClip = new BigImageClip(image);
		imageClip.setPreferredSize(new Dimension(defaultWidth, defaultHeight));

		setLayout(new BorderLayout());

		add(imageClip, BorderLayout.CENTER);
		this.addComponentListener(this);
		scene.addObserver(this);
		camera.addObserver(this);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				initVec = e.getPoint();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = e.getPoint();

				scene.rotate(Math.PI / 2., -(p.x - initVec.x) * screenAngle
						/ image.getWidth(), -Math.PI / 2 + (p.y - initVec.y)
						* screenAngle / image.getHeight());
				initVec = p;
			}
		});
		draw();
	}

	/**
	 * Return 3D scene
	 * 
	 * @return 3D scene
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * Render scene
	 */
	public void draw() {
		renderer.render(scene, camera, image, margin);
		repaint();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		image.setHeight(imageClip.getHeight());
		image.setWidth(imageClip.getWidth());
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

	@Override
	public void update(Observable o, Object arg) {
		draw();

	}
}
