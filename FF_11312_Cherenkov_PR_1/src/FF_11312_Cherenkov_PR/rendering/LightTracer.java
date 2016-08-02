package FF_11312_Cherenkov_PR.rendering;

import java.util.ArrayList;

import FF_11312_Cherenkov_PR.matrix.Point;
import FF_11312_Cherenkov_PR.matrix.transformation.Transformation;
import FF_11312_Cherenkov_PR.model.Camera;
import FF_11312_Cherenkov_PR.model.LightSource;
import FF_11312_Cherenkov_PR.model.Material;
import FF_11312_Cherenkov_PR.model.Parameters;
import FF_11312_Cherenkov_PR.model.Scene;
import FF_11312_Cherenkov_PR.model.Surface;
import FF_11312_Cherenkov_PR.model.Vertex;

/**
 * Class that computes vertices' lightning
 * 
 * @author Pavel Cherenkov
 * 
 */
public class LightTracer {

	/**
	 * Default constructor
	 */
	public LightTracer() {
	}

	private final double epsilon = 1E-20;

	/**
	 * Computes vertex lights
	 * 
	 * @param scene
	 *            3D scene
	 * @param camera
	 *            camera
	 */
	public void computeLights(Scene scene, Camera camera) {
		if (scene.getTransformation() == null)
			return;

		Parameters params = camera.getParams();
		ArrayList<Surface> surfaces = scene.getSurfaces();
		Transformation pt = scene.getTransformation();
		Transformation nt = scene.getNormalTransformation();
		float ambient[] = params.getAmbient().getRGBColorComponents(null);
		float lightColor[][] = new float[3][3];
		Point lightPos[] = new Point[3];

		for (int k = 0; k < 3; k++) {
			LightSource light = params.getLight(k);
			light.getColor().getRGBColorComponents(lightColor[k]);
			Transformation rot = scene.getRotation();
			lightPos[k] = rot.mult(light.getPosition());
		}

		for (Surface s : surfaces) {
			for (int i = 0; i < s.getN(); i++)
				for (int j = 0; j < s.getM(); j++) {
					Vertex v = s.get(i, j).applyAndCopy(pt, nt);
					Material mat;

					for (int matNum = 0; matNum < 2; matNum++) {
						if (matNum == 1) {
							mat = params.getBackMat();
							v.getNormal().multInPlace(-1);
						} else {
							mat = params.getFrontMat();
						}

						float diffusion[] = mat.getDiffusion()
								.getRGBColorComponents(null);
						float specular[] = mat.getSpecular()
								.getRGBColorComponents(null);

						for (int c = 0; c < 3; c++) {
							double I = ambient[c] * diffusion[c];
							for (int k = 0; k < 3; k++) {
								LightSource light = params.getLight(k);
								if (!light.isOn())
									continue;

								Point lightDir = lightPos[k].add(v.getPoint()
										.mult(-1));
								double dist = lightDir.length();
								lightDir.multInPlace(1. / dist);

								Point hypo = lightDir.add(Camera.direction
										.mult(-1));
								hypo.multInPlace(1. / hypo.length());

								double proj = v.getNormal().scalarProduct(
										lightDir);
								if (proj < 0)
									proj = 0;

								double diffComp = diffusion[c] * proj;

								proj = v.getNormal().scalarProduct(hypo);
								if (proj < 0)
									proj = 0;

								double specComp = specular[c]
										* Math.pow(proj, mat.getkPhong());

								I += lightColor[k][c] * light.getIntensity()
										/ 4. / Math.PI
										/ (dist * dist + epsilon)
										* (diffComp + specComp);
							}
							s.get(i, j).setColor(matNum, c, I * 255);
						}
					}
				}
		}
	}
}
