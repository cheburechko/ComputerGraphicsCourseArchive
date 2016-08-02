package FF_11312_Cherenkov_Filt;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import FF_11312_Cherenkov_Filt.filter.WatercolorFilter;
import FF_11312_Cherenkov_Filt.filter.matrix.BlurFilter;
import FF_11312_Cherenkov_Filt.filter.matrix.CustomMatrixFilter;
import FF_11312_Cherenkov_Filt.filter.matrix.EmbossingFilter;
import FF_11312_Cherenkov_Filt.filter.matrix.OutlineFilter;
import FF_11312_Cherenkov_Filt.filter.matrix.SharpenFilter;
import FF_11312_Cherenkov_Filt.filter.pixel.GammaCorrectionFilter;
import FF_11312_Cherenkov_Filt.filter.pixel.IdenticalFilter;
import FF_11312_Cherenkov_Filt.filter.pixel.MonochromeFilter;
import FF_11312_Cherenkov_Filt.filter.pixel.NegativeFilter;

/**
 * View class which is responsible for drawing source image and image with
 * filters applied
 * 
 * @author Pavel Cherenkov
 * 
 */
public class View extends JPanel {
	private BufferedImage sourceImage;
	private BufferedImage newImage;

	private static final int defaultWidth = 600;
	private static final int defaultHeight = 600;
	private static final int margin = 5;

	private ImageClip sourceClip;
	private ImageClip newClip;

	private MonochromeFilter monochrome;
	private OutlineFilter outline;
	private BlurFilter blur;
	private SharpenFilter sharpen;
	private IdenticalFilter identical;
	private NegativeFilter negative;
	private EmbossingFilter embossing;
	private WatercolorFilter watercolor;
	private GammaCorrectionFilter gamma;
	private CustomMatrixFilter custom;

	/**
	 * Creates a clone of a BufferedImage
	 * 
	 * @param img
	 *            source image
	 * @return clone of the source image
	 */
	public static BufferedImage cloneImage(BufferedImage img) {
		BufferedImage newImage = new BufferedImage(img.getWidth(),
				img.getHeight(), BufferedImage.TYPE_INT_RGB);
		newImage.getGraphics().drawImage(img, 0, 0, null);
		return newImage;
	}

	/**
	 * Default constructor
	 * 
	 * @param img
	 *            source image
	 */
	public View(BufferedImage img) {

		sourceImage = cloneImage(img);
		sourceClip = new ImageClip(sourceImage);
		sourceClip.setSize(defaultWidth, defaultHeight);
		sourceClip.setPreferredSize(sourceClip.getSize());

		newImage = cloneImage(img);
		newClip = new ImageClip(newImage);
		newClip.setSize(defaultWidth, defaultHeight);
		newClip.setPreferredSize(newClip.getSize());

		monochrome = new MonochromeFilter();
		outline = new OutlineFilter();
		blur = new BlurFilter();
		sharpen = new SharpenFilter();
		identical = new IdenticalFilter();
		negative = new NegativeFilter();
		embossing = new EmbossingFilter();
		watercolor = new WatercolorFilter();
		gamma = new GammaCorrectionFilter();
		custom = new CustomMatrixFilter();

		setLayout(new GridLayout(1, 0, margin, 0));
		setBorder(new EmptyBorder(margin, margin, margin, margin));
		add(sourceClip);
		add(newClip);
	}

	/**
	 * Copies source image to destination image
	 */
	public void srcToDst() {
		sourceImage.copyData(newImage.getRaster());
		repaint();
	}

	/**
	 * Copies destination image to source image
	 */
	public void dstToSrc() {
		newImage.copyData(sourceImage.getRaster());
		repaint();
	}

	/**
	 * Applies monochrome filter
	 */
	public void applyMonochromeFilter() {
		monochrome.filter(sourceImage, newImage);
		repaint();
	}

	/**
	 * Applies outline filter
	 * 
	 * @param offset
	 *            offset parameter for outline filter
	 */
	public void applyOutlineFilter(int offset) {
		outline.setOffset(offset);
		outline.filter(sourceImage, newImage);
		repaint();
	}

	/**
	 * Applies blur filter
	 */
	public void applyBlurFilter() {
		blur.filter(sourceImage, newImage);
		repaint();
	}

	/**
	 * Applies sharpening filter
	 */
	public void applySharpenFilter() {
		sharpen.filter(sourceImage, newImage);
		repaint();
	}

	/**
	 * Applies identical filter
	 */
	public void applyIdenticalFilter() {
		identical.filter(sourceImage, newImage);
		repaint();
	}

	/**
	 * Applies negative filter
	 */
	public void applyNegativeFilter() {
		negative.filter(sourceImage, newImage);
		repaint();
	}

	/**
	 * Applies embossing filter
	 */
	public void applyEmbossingFilter() {
		embossing.filter(sourceImage, newImage);
		repaint();
	}

	/**
	 * Applies watercolor filter
	 */
	public void applyWatercolorFilter() {
		watercolor.filter(sourceImage, newImage);
		repaint();
	}

	/**
	 * Applies gamma correction filter
	 * 
	 * @param param
	 *            correction value
	 */
	public void applyGammaCorrectionFilter(double param) {
		gamma.setPower(param);
		gamma.filter(sourceImage, newImage);
		repaint();
	}

	/**
	 * Get destination image
	 * 
	 * @return destination image
	 */
	public BufferedImage getDestinationImage() {
		return newImage;
	}

	/**
	 * Get custom matrix filter
	 * 
	 * @return custom filter
	 */
	public CustomMatrixFilter getCustomMatrixFilter() {
		return custom;
	}

	/**
	 * Applies custom filter
	 */
	public void applyCustomFilter() {
		custom.filter(sourceImage, newImage);
		repaint();
	}
}
